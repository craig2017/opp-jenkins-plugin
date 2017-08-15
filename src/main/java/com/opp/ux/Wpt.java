package com.opp.ux;

import com.google.gson.*;
import com.opp.service.*;
import com.opp.domain.HttpResp;
import com.opp.domain.TestResults;
import com.opp.domain.ux.RrResultResponse;
import com.opp.util.JsonUtil;
import hudson.Launcher;
import hudson.model.StreamBuildListener;
import net.sf.json.JSON;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by ctobe on 12/24/15.
 */
public class Wpt {

    private final groovy.lang.Script script;
    private final StreamBuildListener out;
    private final Launcher launcher;
    private TestResults testResults;

    private final WptService wptService;
    private final HttpService httpService;
    private final JenkinsBuildService jenkinsBuildService;
    private final HtmlResultService htmlResultService;
    private final HipchatV1Service hipchatV1Service;
    private final HipchatHtmlResultService hipchatHtmlResultService;

    /**
     * Class constructor
     * @param script
     * @throws Exception
     */
    public Wpt(final groovy.lang.Script script) throws Exception {
        this.testResults = new TestResults();
        this.script = script;
        if (script == null) {
            this.out = new StreamBuildListener(System.out, Charset.defaultCharset());
            this.launcher = null;
        } else {
            this.out = (StreamBuildListener) script.getBinding().getVariable("listener");
            this.launcher = (Launcher) script.getBinding().getVariable("launcher");
        }
        this.wptService = new WptService();
        this.httpService = new HttpService();
        this.jenkinsBuildService = new JenkinsBuildService();
        this.hipchatV1Service = new HipchatV1Service();
        this.htmlResultService = new HtmlResultService();
        this.hipchatHtmlResultService = new HipchatHtmlResultService();
    }


    /**
     * Starts the test run and performs the following tasks:
     * - Gets parameters from jenkins job
     * - Validates the WPT label
     * - Makes WPT api call to start test run
     * - Waits for test to complete
     * - Notifies opp when test is done
     * - Adds results to jenkins
     * @return TestResults
     * @throws Exception
     */
    public TestResults runTest() throws Exception {
        String label = jenkinsBuildService.getParam("label");
        String jobUrl = jenkinsBuildService.getBuild().getUrl();
        String hipchatRoomName = jenkinsBuildService.getParam("hipchatRoomName");
        String hipchatTestNameHtml = "<b>WPT Test </b>" + label + "&nbsp;";
        script.println("Got test label: " + label + "Got room: " + hipchatRoomName);

        // validate test label
        String labelValidationMsg = wptService.labelValidation(label);
        if (labelValidationMsg != "") {
            jenkinsBuildService.getBuild().setDescription(labelValidationMsg);
            appendTestResultsErrorMessage(labelValidationMsg);
            testResults.setBuildSuccessful(false);
            hipchatV1Service.sendMessage(hipchatRoomName, hipchatTestNameHtml + "<b>FAILED: </b>" + labelValidationMsg, "red");
            return testResults; // exit with failure
        }


        // send wpt api call
        String wptApiUrl = wptService.buildWptApiCall();
        HttpResp res = httpService.sendGet(wptApiUrl);

        // validate results from API call
        String wptResponse = "";
        String wptTestId = "";
        JsonObject wptRespJsonObject = new JsonObject();
        if (res.isSuccess()) {
            try {
                // no error, pretty print and get WPT test id
                wptRespJsonObject = new JsonParser().parse(res.getResp()).getAsJsonObject();
                wptResponse += "<br />" + JsonUtil.prettyPrint(res.getResp(), true);
                if (wptRespJsonObject.get("statusText").getAsString().equals("Ok")) {
                    wptTestId = wptRespJsonObject.get("data").getAsJsonObject().get("testId").getAsString();
                } else {
                    script.println("=== Error: 'statusText' from WPT JSON response is not set to 'Ok'");
                }
            } catch (Exception ex) {
                script.println(res.getErrorMessage() + " \n " + "Failed to parse json. " + ex.getMessage());
            }
        } else {
            // got an error, save what i got back
            wptResponse += "<br />" + res.getErrorMessage();
        }



        // report errors or successes to jenkins
        StringBuilder summary = new StringBuilder();
        if (wptTestId.equals("")) {
            // ==== report errors ====
            summary.append("<b>ERROR DETAILS:</b> " + wptResponse);
            summary.append("<br /><br /><b>API Call:</b> " + wptApiUrl);
            jenkinsBuildService.getBuild().setDescription(summary.toString());

           // testResults.setSummaryText(summary.toString());
            testResults.setBuildSuccessful(false);
            appendTestResultsErrorMessage("See Run Description for cause of Failure");
            hipchatV1Service.sendMessage(hipchatRoomName, hipchatTestNameHtml + ": <b>FAILED:</b><br>" + summary.toString(), "red");
            return testResults;
        }

        script.println("<br />=====WPT Response=====<br />" + wptResponse);

        // ==== everything is good ====
        String wptHtmlRunDetails = htmlResultService.getWptResultSummaryHtml(wptTestId, wptApiUrl, label, wptRespJsonObject);
        String hipchatDetails = hipchatHtmlResultService.getWptResultSummaryHtml(wptTestId, jobUrl, label);
        summary.append(wptHtmlRunDetails);
        String hipchatDetailsInPr = removeRRresFromResults(hipchatDetails);  // Remove opp Results from IN PROGRESS run
        hipchatV1Service.sendMessage(hipchatRoomName, hipchatTestNameHtml + "<b>IN PROGRESS</b><br>" + hipchatDetailsInPr, "green");
        jenkinsBuildService.getBuild().setDescription(summary.toString());
        // testResults.setSummaryText(summary.toString());

        script.println("Waiting for test to complete...");
        // wait for test to complete
        boolean success = wptService.waitForCompletion(wptTestId, script);
        if (!success) {
            script.println("********* Error waiting for test to complete. *********");
            testResults.setBuildSuccessful(false);
            appendTestResultsErrorMessage("Error waiting for test to complete");
            hipchatV1Service.sendMessage(hipchatRoomName, hipchatTestNameHtml + "<b>FAILED: </b> " + "Error waiting for test to complete", "red");
        }

        boolean hasSlas = false;
        if(testResults.isBuildSuccessful()){
            // notify opp test is done
            script.println("********* Test Completed: making callback to opp *********");

            // get results from opp
            RrResultResponse rrResultResponse = sendTestDoneToRR(wptTestId);

            hasSlas = !jenkinsBuildService.getParam("slas").trim().equals("");
            if(hasSlas) {
                String slaResultHtml = htmlResultService.getSlaResultsHtml(rrResultResponse);
                String slaHipchatHtml = hipchatHtmlResultService.getSlaResultsHtml(rrResultResponse);
                if (rrResultResponse.getSlaResult().getTotalFailed() > 0) {
                    testResults.setBuildSuccessful(false);
                }
                // now output pretty html for sla results
                summary.append(slaResultHtml);
                jenkinsBuildService.getBuild().setDescription(summary.toString());
                hipchatV1Service.sendMessage(hipchatRoomName, hipchatTestNameHtml + "<b>COMPLETED</b><br>" + hipchatDetails + hipchatHtmlResultService.SEP + slaHipchatHtml, (testResults.isBuildSuccessful()) ? "green" : "red");
            }
            script.println("<br /><b>----------- Raw Results --------------</b><br /><br />");
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            script.println(JsonUtil.prettyPrint(gson.toJson(rrResultResponse, RrResultResponse.class), true));
        }

        return testResults;
    }


    private void appendTestResultsErrorMessage(String errorMessage){
        testResults.setErrorMessage(testResults.getErrorMessage() + "\n<br />" + errorMessage);
    }

    private String removeRRresFromResults(String htmlResults) {
        int rrResIndx = htmlResults.indexOf("opp Results");
        String replaceRRres = htmlResults.substring(rrResIndx);
        return htmlResults.replace(replaceRRres,"");
    }

    /**
     * Notifies opp UX that a test is done and to process the results
     * @param wptId
     * @return
     */
    private RrResultResponse sendTestDoneToRR(String wptId) {
        // send request for work to be done
        String storeInGraphite = (jenkinsBuildService.getParam("saveToGraphite").equals("TRUE")) ? "true" : "";
        String slas = jenkinsBuildService.getParam("slas");

        // set post params
        HashMap<String, String> params = new HashMap<>();
        params.put("wptId", wptId);
        params.put("slas", slas);
        params.put("sendToGraphite", storeInGraphite);
        HttpResp testDoneResp =  httpService.sendPost("http://opp.roving.com/uxsvc/v2/rrux/importWpt", params);
        RrResultResponse rrResultResponse = new RrResultResponse();
        if(testDoneResp.isSuccess()) {
            script.println("********* Raw response from opp *********");
            script.println(testDoneResp.getResp());
            try {
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
                return gson.fromJson(testDoneResp.getResp(), RrResultResponse.class);

            } catch (Exception ex2){
                testResults.setBuildSuccessful(false);
                appendTestResultsErrorMessage("Failed to parse response from opp: <br /><br />" + testDoneResp.getResp());
            }
        } else {
            testResults.setBuildSuccessful(false);
            appendTestResultsErrorMessage("Failed to save results to opp. Error message: " + testDoneResp.getErrorMessage());
        }
        return rrResultResponse;
    }
}
