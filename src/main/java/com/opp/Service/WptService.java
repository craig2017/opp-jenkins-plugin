package com.opp.service;

import com.google.gson.*;
import com.opp.domain.Application;
import com.opp.domain.HttpResp;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by ctobe on 1/7/16.
 */
@Service
public class WptService {

    private final HttpService httpService;
    private final JenkinsBuildService jenkinsBuildService;

    private CacheManager cacheManager;

    public WptService() throws Exception {
        System.setProperty(CacheManager.ENABLE_SHUTDOWN_HOOK_PROPERTY, "true"); // not sure if i need this or not but seems like a good idea
        cacheManager = CacheManager.getInstance();
        this.httpService = new HttpService();
        this.jenkinsBuildService = new JenkinsBuildService();
    }

    /**
     * Validates a UX test label to confirm to the following:
     * {env}.{appName}.{pageOrTestName}.{location}.{browser}.{connectionType}.{description-optional}
     *
     * @param label
     * @return
     */
    public String labelValidation(String label) {

        List<String> errors = new ArrayList<>();
        List<String> listLabels = Arrays.asList(label.split("\\."));

        if (label.toLowerCase() != label) {
            errors.add("Must be lower case");
        }

        int size = listLabels.size();

        // check we have at least 6
        if (size < 6) {
            errors.add("Invalid syntax.  Should be: {env}.{appName}.{pageOrTestName}.{location}.{browser}.{connectionType}.{description-optional}");
        }

        String env = listLabels.get(0);
        //String app="", page="", location="",connection="", description="";
        final String app = (size >= 2) ? listLabels.get(1) : "";
        //if(size >= 3) page = listLabels.get(2);
        //if(size >= 4) location = listLabels.get(3);
        final String browser = (size >= 5) ? listLabels.get(4) : "";
        //if(size >= 6) connection = listLabels.get(5);
        //if(size >= 7) description = listLabels.get(6);


        // validate application name exists
        // get all applications
        Application[] applications = getApplications();
        if(applications != null) {
            Optional<Application> application = Arrays.stream(applications).filter(a -> a.getAppKey().equals(app)).findFirst();
            // validate name exists
            if (!application.isPresent()) {
                errors.add("Invalid application name");
            }
        }


        // verify environments
        List<String> allowedEnvironments = Arrays.asList("d1", "f1", "f2", "l1", "s1", "s1load", "p2");
        if (!allowedEnvironments.contains(env)) {
            errors.add("Invalid environment");
        }

        // verify no underscores
        if (label.contains("_")) {
            errors.add("Use '-' not '_' for naming");
        }

        // verify no underscores
        if (browser.contains("-")) {
            errors.add("Browser shouldn't have a dash");
        }


        String message = "";
        for (String error : errors) {
            message += "<li>" + error + "</li>";
        }
        if (message != "") {
            message = "<div style='color:red'>Invalid Test Label: <br /> <ul>" + message + "</ul></div>";
        }
        return message;
    }


    /**
     * Builds the API call from all the parameters in jenkins to send to WPT to start the test
     *
     * @return
     * @throws URISyntaxException
     */
    public String buildWptApiCall() throws URISyntaxException {
        String[] params = {
                "url",
                "label",
                "location",
                "runs",
                "fvonly",
                "domelement",
                "connections",
                "web10",
                "script",
                "block",
                "login",
                "password",
                "authType",
                "video",
                "f",
                "notify",
                "pingback",
                "bwDown",
                "bwUp",
                "latency",
                "plr",
                "tcpdump",
                "clearcerts",
                "mobile",
                "mv",
                "cmdline"
        };

        // build WPT API call
        URIBuilder wptUrlBuilder = new URIBuilder(jenkinsBuildService.getParam("wpturl"));
        for (String param : params) {
            String tmpValue = jenkinsBuildService.getParam(param);
            if (!tmpValue.trim().isEmpty()) {
                wptUrlBuilder.addParameter(param, tmpValue);
            }
        }

        return wptUrlBuilder.toString();
    }


    /**
     * Keep polling WPT till the test run is complete
     *
     * @param testId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean waitForCompletion(String testId, groovy.lang.Script script) throws IOException, InterruptedException {

        String url = "http://mywpt-server/testStatus.php?f=json&test=" + testId;
        boolean testDone = false;
        boolean success = false;
        int loopProtect = 0;
        int testStatus;

        //Wait until test is done or 150 minutes
        while (!testDone && loopProtect < 300) {
            loopProtect++;
            HttpResp res = httpService.sendGet(url);
            //{"statusCode":100,"statusText":"Test Started 36 seconds ago","data":{"statusCode":100,"statusText":"Test Started 36 seconds ago","testId":"140325_Y9_4","runs":1,"fvonly":0,"remote":false,"testsExpected":2,"location":"Bedford_W7_02_Chrome-FFX","startTime":"03\/25\/14 19:34:11","elapsed":36}}

            if (res.isSuccess()) {
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(res.getResp()).getAsJsonObject();
                testStatus = jsonObject.get("statusCode").getAsInt();
                if (testStatus >= 200) {
                    testDone = true;
                    success = true;
                }
                script.println(res.getResp());
            } else {
                script.println("ERROR: Error in making web request");
                testDone = true;
                success = false;
            }

            if (!testDone) {
                // build.setDescription(JsonOutput.prettyPrint(resp).replace(' ', '&nbsp;').replace('\n', '<br />'))
                Thread.sleep(30000);
            }

        }

        return success;


    }

    /**
     * Get applications from cache or rest
     * @return Application[]
     */
    private Application[] getApplications(){
        // cache keys
        final String cacheName = "applications";
        final String cacheElementName = "apps";

        Cache cache = cacheManager.getCache(cacheName);
        // check cache
        if(cache.get(cacheElementName) == null){
            // fetch values and store in cache
            Application[] applications;
            HttpService httpService = new HttpService();
            HttpResp httpResp = httpService.sendGet("https://opp.roving.com/loadsvc/v1/applications");
            if(httpResp.isSuccess()) {
                // map to object
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
                applications = gson.fromJson(httpResp.getResp(), Application[].class);
                // save to cache
                cache.put(new Element(cacheElementName, applications));
            }
        }

        // grab element from cache
        Element elApps = cache.get(cacheElementName);

        return (elApps == null ? null : (Application[])elApps.getObjectValue());

    }
}
