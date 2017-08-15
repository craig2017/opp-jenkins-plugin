package com.opp.ux;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.opp.domain.HttpResp;
import com.opp.service.HipchatHtmlResultService;
import com.opp.service.HtmlResultService;
import com.opp.service.HttpService;
import com.opp.service.WptService;
import com.opp.domain.ux.RrResultResponse;
import com.opp.domain.ux.SlaResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Created by ctobe on 1/4/16.
 */
public class WptTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void decodeSlas() throws Exception {
        String json = "{\"total_slas\":9,\"total_pass\":5,\"total_failed\":4,\"total_pass_pct\":55.6,\"results\":{\"successfulFVRuns\":{\"sla\":6,\"actual\":6,\"diff\":0,\"diff_pct\":9900,\"sla_pass\":false},\"median.firstView.TTFB\":{\"sla\":300,\"actual\":138,\"diff\":-162,\"diff_pct\":4500,\"sla_pass\":false},\"median.firstView.SpeedIndex\":{\"sla\":3000,\"actual\":3100,\"diff\":100,\"diff_pct\":10230,\"sla_pass\":false},\"median.firstView.visualComplete\":{\"sla\":4000,\"actual\":0,\"diff\":-4000,\"diff_pct\":0,\"sla_pass\":true},\"median.firstView.userTime.galileo.loaded\":{\"sla\":4000,\"actual\":null,\"diff\":-4000,\"diff_pct\":0,\"sla_pass\":true},\"median.firstView.userTime.ctct_fully_loaded\":{\"sla\":7000,\"actual\":null,\"diff\":-7000,\"diff_pct\":0,\"sla_pass\":true},\"median.firstView.userTime.ctct_visually_complete\":{\"sla\":3000,\"actual\":null,\"diff\":-3000,\"diff_pct\":0,\"sla_pass\":true},\"median.firstView.breakdown.js.requests\":{\"sla\":5,\"actual\":15,\"diff\":10,\"diff_pct\":29900,\"sla_pass\":false},\"median.firstView.breakdown.css.requests\":{\"sla\":5,\"actual\":0,\"diff\":-5,\"diff_pct\":0,\"sla_pass\":true}}}";
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        SlaResult slaResult = gson.fromJson(json, SlaResult.class);
        assertTrue(slaResult.getTotalSlas() == 9);

    }

    @Test
    public void decodeRrResponse() throws Exception {
        String json = "{\"test\":{\"successfulFVRuns\":6,\"successfulRVRuns\":6,\"min\":\"SAME SCHEMA AS MEDIAN\",\"max\":\"SAME SCHEMA AS MEDIAN\",\"median\":{\"firstView\":{\"loadTime\":2207,\"TTFB\":138,\"bytesOut\":69149,\"bytesOutDoc\":64087,\"bytesIn\":151288,\"bytesInDoc\":150491,\"connections\":1,\"requestsDoc\":30,\"responses_200\":25,\"responses_404\":0,\"responses_other\":2,\"result\":99999,\"render\":630,\"fullyLoaded\":2317,\"cached\":0,\"docTime\":2207,\"domTime\":0,\"score_cache\":40,\"score_cdn\":40,\"score_gzip\":100,\"score_cookies\":-1,\"score_keep-alive\":100,\"score_minify\":-1,\"score_combine\":100,\"score_compress\":-1,\"score_etags\":-1,\"gzip_total\":124521,\"gzip_savings\":0,\"minify_total\":0,\"minify_savings\":0,\"image_total\":0,\"image_savings\":0,\"optimization_checked\":1,\"aft\":0,\"domElements\":323,\"pageSpeedVersion\":\"1.9\",\"title\":\"\",\"titleTime\":0,\"loadEventStart\":2213,\"loadEventEnd\":2220,\"domContentLoadedEventStart\":441,\"domContentLoadedEventEnd\":487,\"lastVisualChange\":3099,\"browser_name\":\"Firefox\",\"browser_version\":\"28.0.0.5186\",\"server_count\":1,\"server_rtt\":2,\"base_page_cdn\":\"\",\"fixed_viewport\":0,\"score_progressive_jpeg\":-1,\"firstPaint\":0,\"docCPUms\":2043.613,\"fullyLoadedCPUms\":3213.621,\"docCPUpct\":92,\"fullyLoadedCPUpct\":19,\"isResponsive\":-1,\"date\":1439610893,\"SpeedIndex\":3100,\"visualComplete\":0,\"effectiveBps\":69430,\"effectiveBpsDoc\":72736,\"run\":6,\"userTime.galileo.editor-started\":87,\"userTime.galileo.editor-loading\":170,\"userTime.galileo.editor-starting\":95,\"domains\":{\"moc.tcatnoctnatsnoc.1s.iu\":{\"bytes\":14730,\"requests\":2,\"connections\":1},\"moc.tcatnoctnatsnoc.1s.iutsid\":{\"bytes\":3130,\"requests\":7,\"connections\":2},\"ten.cdrtmo.tt.tcatnoctnatsnoc\":{\"bytes\":8047,\"requests\":7,\"connections\":2}},\"breakdown\":{\"html\":{\"bytes\":114785,\"requests\":5},\"js\":{\"bytes\":10741,\"requests\":15},\"css\":{\"bytes\":0,\"requests\":0},\"image\":{\"bytes\":8872,\"requests\":3},\"flash\":{\"bytes\":0,\"requests\":0},\"font\":{\"bytes\":0,\"requests\":0},\"other\":{\"bytes\":8762,\"requests\":9}}},\"repeatView\":{\"loadTime\":2226,\"TTFB\":111,\"bytesOut\":83609,\"bytesOutDoc\":80843,\"bytesIn\":167476,\"bytesInDoc\":166844,\"connections\":3,\"requestsDoc\":30,\"responses_200\":26,\"responses_404\":0,\"responses_other\":2,\"result\":99999,\"render\":596,\"fullyLoaded\":2287,\"cached\":1,\"docTime\":2226,\"domTime\":0,\"score_cache\":40,\"score_cdn\":40,\"score_gzip\":100,\"score_cookies\":-1,\"score_keep-alive\":100,\"score_minify\":-1,\"score_combine\":100,\"score_compress\":-1,\"score_etags\":-1,\"gzip_total\":143186,\"gzip_savings\":0,\"minify_total\":0,\"minify_savings\":0,\"image_total\":0,\"image_savings\":0,\"optimization_checked\":1,\"aft\":0,\"domElements\":323,\"pageSpeedVersion\":\"1.9\",\"title\":\"\",\"titleTime\":0,\"loadEventStart\":2197,\"loadEventEnd\":2198,\"domContentLoadedEventStart\":383,\"domContentLoadedEventEnd\":424,\"lastVisualChange\":3078,\"browser_name\":\"Firefox\",\"browser_version\":\"28.0.0.5186\",\"server_count\":1,\"server_rtt\":1,\"base_page_cdn\":\"\",\"fixed_viewport\":0,\"score_progressive_jpeg\":-1,\"firstPaint\":0,\"docCPUms\":2152.814,\"fullyLoadedCPUms\":3447.622,\"docCPUpct\":96,\"fullyLoadedCPUpct\":20,\"isResponsive\":-1,\"date\":1439610943,\"SpeedIndex\":3100,\"visualComplete\":0,\"effectiveBps\":76965,\"effectiveBpsDoc\":78886,\"run\":6,\"userTime.galileo.editor-started\":87,\"userTime.galileo.editor-loading\":170,\"userTime.galileo.editor-starting\":95,\"domains\":{\"moc.tcatnoctnatsnoc.1s.iu\":{\"bytes\":14728,\"requests\":2,\"connections\":1},\"moc.tcatnoctnatsnoc.1s.iutsid\":{\"bytes\":19313,\"requests\":7,\"connections\":2}},\"breakdown\":{\"html\":{\"bytes\":114781,\"requests\":5},\"js\":{\"bytes\":27172,\"requests\":16},\"css\":{\"bytes\":0,\"requests\":0},\"image\":{\"bytes\":5692,\"requests\":3},\"flash\":{\"bytes\":0,\"requests\":0},\"font\":{\"bytes\":0,\"requests\":0},\"other\":{\"bytes\":8499,\"requests\":8}}}},\"average\":{\"firstView\":{\"loadTime\":2424,\"TTFB\":107.333336,\"bytesOut\":73925.836,\"bytesOutDoc\":64773.5,\"bytesIn\":148638.17,\"bytesInDoc\":147234.33,\"connections\":1.1666666,\"requestsDoc\":30,\"responses_200\":25.166666,\"responses_404\":0,\"responses_other\":2,\"result\":99999,\"render\":582.1667,\"fullyLoaded\":3867.5,\"cached\":0,\"docTime\":2424,\"domTime\":0,\"score_cache\":40,\"score_cdn\":40,\"score_gzip\":100,\"score_cookies\":-1,\"score_keep-alive\":100,\"score_minify\":-1,\"score_combine\":100,\"score_compress\":-1,\"score_etags\":-1,\"gzip_total\":127197,\"gzip_savings\":0,\"minify_total\":0,\"minify_savings\":0,\"image_total\":0,\"image_savings\":0,\"optimization_checked\":1,\"aft\":0,\"domElements\":323,\"pageSpeedVersion\":1.9,\"title\":0,\"titleTime\":0,\"loadEventStart\":2420.6667,\"loadEventEnd\":2425.8333,\"domContentLoadedEventStart\":455,\"domContentLoadedEventEnd\":491.5,\"lastVisualChange\":3242.6667,\"browser_name\":0,\"browser_version\":28,\"server_count\":1,\"server_rtt\":1.1666666,\"base_page_cdn\":0,\"fixed_viewport\":0,\"score_progressive_jpeg\":-1,\"firstPaint\":0,\"docCPUms\":2139.8137,\"fullyLoadedCPUms\":3421.622,\"docCPUpct\":88.833336,\"fullyLoadedCPUpct\":18.333334,\"isResponsive\":-1,\"date\":1439610620,\"SpeedIndex\":3233.3333,\"visualComplete\":0,\"effectiveBps\":55116,\"effectiveBpsDoc\":64836.168,\"avgRun\":5,\"userTime.galileo.editor-started\":87,\"userTime.galileo.editor-loading\":170,\"userTime.galileo.editor-starting\":95},\"repeatView\":{\"URL\":0,\"loadTime\":2233.6667,\"TTFB\":118.333336,\"bytesOut\":84482.664,\"bytesOutDoc\":79675.836,\"bytesIn\":159151,\"bytesInDoc\":158264.67,\"connections\":2.1666667,\"requestsDoc\":28.833334,\"responses_200\":24.166666,\"responses_404\":0,\"responses_other\":2,\"result\":99999,\"render\":611.5,\"fullyLoaded\":2318.3333,\"cached\":1,\"docTime\":2233.6667,\"domTime\":0,\"score_cache\":17.5,\"score_cdn\":17.5,\"score_gzip\":100,\"score_cookies\":-1,\"score_keep-alive\":100,\"score_minify\":-1,\"score_combine\":100,\"score_compress\":-1,\"score_etags\":-1,\"gzip_total\":129474,\"gzip_savings\":0,\"minify_total\":0,\"minify_savings\":0,\"image_total\":0,\"image_savings\":0,\"optimization_checked\":1,\"aft\":0,\"domElements\":323,\"pageSpeedVersion\":1.9,\"title\":0,\"titleTime\":0,\"loadEventStart\":2227.6667,\"loadEventEnd\":2233,\"domContentLoadedEventStart\":400,\"domContentLoadedEventEnd\":437.5,\"lastVisualChange\":3099.3333,\"browser_name\":0,\"browser_version\":28,\"server_count\":1,\"server_rtt\":1,\"base_page_cdn\":0,\"fixed_viewport\":0,\"score_progressive_jpeg\":-1,\"firstPaint\":0,\"docCPUms\":2165.814,\"fullyLoadedCPUms\":3473.6223,\"docCPUpct\":96.666664,\"fullyLoadedCPUpct\":20,\"isResponsive\":-1,\"date\":1439610620,\"SpeedIndex\":3100,\"visualComplete\":0,\"effectiveBps\":72371,\"effectiveBpsDoc\":74826.664,\"avgRun\":6,\"userTime.galileo.editor-started\":87,\"userTime.galileo.editor-loading\":170,\"userTime.galileo.editor-starting\":95}},\"standardDeviation\":{\"firstView\":{\"loadTime\":349,\"TTFB\":13,\"bytesOut\":9555,\"bytesOutDoc\":738,\"bytesIn\":2164,\"bytesInDoc\":2570,\"connections\":0,\"requests\":0,\"requestsDoc\":0,\"responses_200\":0,\"responses_404\":0,\"responses_other\":0,\"result\":0,\"render\":49,\"fullyLoaded\":3055,\"cached\":0,\"docTime\":349,\"domTime\":0,\"score_cache\":0,\"score_cdn\":0,\"score_gzip\":0,\"score_cookies\":0,\"score_keep-alive\":0,\"score_minify\":0,\"score_combine\":0,\"score_compress\":0,\"score_etags\":0,\"gzip_total\":1586,\"gzip_savings\":0,\"minify_total\":0,\"minify_savings\":0,\"image_total\":0,\"image_savings\":0,\"optimization_checked\":0,\"aft\":0,\"domElements\":0,\"pageSpeedVersion\":0,\"title\":0,\"titleTime\":0,\"loadEventStart\":349,\"loadEventEnd\":349,\"domContentLoadedEventStart\":72,\"domContentLoadedEventEnd\":73,\"lastVisualChange\":349,\"browser_name\":0,\"browser_version\":0,\"server_count\":0,\"server_rtt\":0,\"base_page_cdn\":0,\"fixed_viewport\":0,\"score_progressive_jpeg\":0,\"firstPaint\":0,\"docCPUms\":125,\"fullyLoadedCPUms\":255,\"docCPUpct\":6,\"fullyLoadedCPUpct\":1,\"isResponsive\":0,\"date\":174,\"SpeedIndex\":344,\"visualComplete\":0,\"effectiveBps\":19987,\"effectiveBpsDoc\":8605,\"avgRun\":null,\"userTime.galileo.editor-started\":87,\"userTime.galileo.editor-loading\":170,\"userTime.galileo.editor-starting\":95},\"repeatView\":{\"URL\":0,\"loadTime\":34,\"TTFB\":5,\"bytesOut\":1768,\"bytesOutDoc\":1266,\"bytesIn\":6294,\"bytesInDoc\":6413,\"connections\":0,\"requests\":0,\"requestsDoc\":0,\"responses_200\":1,\"responses_404\":0,\"responses_other\":0,\"result\":0,\"render\":32,\"fullyLoaded\":40,\"cached\":0,\"docTime\":34,\"domTime\":0,\"score_cache\":18,\"score_cdn\":18,\"score_gzip\":0,\"score_cookies\":0,\"score_keep-alive\":0,\"score_minify\":0,\"score_combine\":0,\"score_compress\":0,\"score_etags\":0,\"gzip_total\":8222,\"gzip_savings\":0,\"minify_total\":0,\"minify_savings\":0,\"image_total\":0,\"image_savings\":0,\"optimization_checked\":0,\"aft\":0,\"domElements\":0,\"pageSpeedVersion\":0,\"title\":0,\"titleTime\":0,\"loadEventStart\":41,\"loadEventEnd\":41,\"domContentLoadedEventStart\":8,\"domContentLoadedEventEnd\":7,\"lastVisualChange\":29,\"browser_name\":0,\"browser_version\":0,\"server_count\":0,\"server_rtt\":0,\"base_page_cdn\":0,\"fixed_viewport\":0,\"score_progressive_jpeg\":0,\"firstPaint\":0,\"docCPUms\":14,\"fullyLoadedCPUms\":110,\"docCPUpct\":1,\"fullyLoadedCPUpct\":0,\"isResponsive\":0,\"date\":175,\"SpeedIndex\":0,\"visualComplete\":0,\"effectiveBps\":3327,\"effectiveBpsDoc\":3024,\"avgRun\":null,\"userTime.galileo.editor-started\":87,\"userTime.galileo.editor-loading\":170,\"userTime.galileo.editor-starting\":95}},\"statusCode\":200,\"statusText\":\"Test Complete\"},\"sla_result\":{\"total_slas\":9,\"total_pass\":5,\"total_failed\":4,\"total_pass_pct\":55.6,\"results\":{\"successfulFVRuns\":{\"sla\":6,\"actual\":6,\"diff\":0,\"diff_pct\":9900,\"sla_pass\":false},\"median.firstView.TTFB\":{\"sla\":300,\"actual\":138,\"diff\":-162,\"diff_pct\":4500,\"sla_pass\":false},\"median.firstView.SpeedIndex\":{\"sla\":3000,\"actual\":3100,\"diff\":100,\"diff_pct\":10230,\"sla_pass\":false},\"median.firstView.visualComplete\":{\"sla\":4000,\"actual\":0,\"diff\":-4000,\"diff_pct\":0,\"sla_pass\":true},\"median.firstView.userTime.galileo.loaded\":{\"sla\":4000,\"actual\":null,\"diff\":-4000,\"diff_pct\":0,\"sla_pass\":true},\"median.firstView.userTime.ctct_fully_loaded\":{\"sla\":7000,\"actual\":null,\"diff\":-7000,\"diff_pct\":0,\"sla_pass\":true},\"median.firstView.userTime.ctct_visually_complete\":{\"sla\":3000,\"actual\":null,\"diff\":-3000,\"diff_pct\":0,\"sla_pass\":true},\"median.firstView.breakdown.js.requests\":{\"sla\":5,\"actual\":15,\"diff\":10,\"diff_pct\":29900,\"sla_pass\":false},\"median.firstView.breakdown.css.requests\":{\"sla\":5,\"actual\":0,\"diff\":-5,\"diff_pct\":0,\"sla_pass\":true}}},\"slas\":{\"successfulFVRuns\":6,\"median\":{\"firstView\":{\"TTFB\":300,\"SpeedIndex\":3000,\"visualComplete\":4000,\"userTime.galileo.loaded\":4000,\"userTime.ctct_fully_loaded\":7000,\"userTime.ctct_visually_complete\":3000,\"breakdown\":{\"js\":{\"requests\":5},\"css\":{\"requests\":5}}}}}}";
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        RrResultResponse rrResultResponse = gson.fromJson(json, RrResultResponse.class);
        assertTrue(rrResultResponse.getSlaResult().getTotalSlas() == 9);
        assertTrue(((Double)(rrResultResponse.getTest().get("successfulFVRuns"))).intValue() == 6);

    }

  /*  @Test
    public void mytest(){
        String oppRespJson = "{\n" +
                "    \"sla_result\": {\n" +
                "        \"total_slas\": 8,\n" +
                "        \"total_pass\": 5,\n" +
                "        \"total_failed\": 3,\n" +
                "        \"total_pass_pct\": 62.5,\n" +
                "        \"sla_details\": {\n" +
                "            \"successfulFVRuns\": {\n" +
                "                \"sla\": 6,\n" +
                "                \"actual\": 6,\n" +
                "                \"diff\": 0,\n" +
                "                \"sla_pass\": true,\n" +
                "                \"diff_pct\": 0\n" +
                "            },\n" +
                "            \"median.firstView.TTFB\": {\n" +
                "                \"sla\": 500,\n" +
                "                \"actual\": 125,\n" +
                "                \"diff\": -375,\n" +
                "                \"sla_pass\": true,\n" +
                "                \"diff_pct\": -300\n" +
                "            },\n" +
                "            \"median.firstView.SpeedIndex\": {\n" +
                "                \"sla\": 3000,\n" +
                "                \"actual\": 2812,\n" +
                "                \"diff\": -188,\n" +
                "                \"sla_pass\": true,\n" +
                "                \"diff_pct\": -6.69\n" +
                "            },\n" +
                "            \"median.firstView.visualComplete\": {\n" +
                "                \"sla\": 3000,\n" +
                "                \"actual\": 5300,\n" +
                "                \"diff\": 2300,\n" +
                "                \"sla_pass\": false,\n" +
                "                \"diff_pct\": 43.4\n" +
                "            },\n" +
                "            \"median.firstView.userTime.galileo.loaded\": {\n" +
                "                \"sla\": 4000,\n" +
                "                \"actual\": null,\n" +
                "                \"diff\": -4000,\n" +
                "                \"sla_pass\": true,\n" +
                "                \"diff_pct\": 0\n" +
                "            },\n" +
                "            \"median.firstView.userTime.ctct_visually_complete\": {\n" +
                "                \"sla\": 3000,\n" +
                "                \"actual\": null,\n" +
                "                \"diff\": -3000,\n" +
                "                \"sla_pass\": true,\n" +
                "                \"diff_pct\": 0\n" +
                "            },\n" +
                "            \"median.firstView.breakdown.js.requests\": {\n" +
                "                \"sla\": 10,\n" +
                "                \"actual\": 67,\n" +
                "                \"diff\": 57,\n" +
                "                \"sla_pass\": false,\n" +
                "                \"diff_pct\": 85.07\n" +
                "            },\n" +
                "            \"median.firstView.breakdown.css.requests\": {\n" +
                "                \"sla\": 5,\n" +
                "                \"actual\": 14,\n" +
                "                \"diff\": 9,\n" +
                "                \"sla_pass\": false,\n" +
                "                \"diff_pct\": 64.29\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"slas\": {\n" +
                "        \"successfulFVRuns\": 6,\n" +
                "        \"median\": {\n" +
                "            \"firstView\": {\n" +
                "                \"TTFB\": 500,\n" +
                "                \"SpeedIndex\": 3000,\n" +
                "                \"visualComplete\": 3000,\n" +
                "                \"userTime.galileo.loaded\": 4000,\n" +
                "                \"userTime.ctct_visually_complete\": 3000,\n" +
                "                \"breakdown\": {\n" +
                "                    \"js\": {\n" +
                "                        \"requests\": 10\n" +
                "                    },\n" +
                "                    \"css\": {\n" +
                "                        \"requests\": 5\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"test\": {\n" +
                "        \"_id\": \"dfe10b5d0d04f42752756e63a153ea8b\",\n" +
                "        \"_rev\": \"1-988f01b5364cbc4e73c7ae9f0fc88f52\",\n" +
                "        \"id\": \"160129_F1_4\",\n" +
                "        \"url\": \"https://ui.l1.constantcontact.com/rnavmap/evaluate.rnav?activepage=site.logout.eval\",\n" +
                "        \"summary\": \"http://wpt.roving.com/results.php?test=160129_F1_4\",\n" +
                "        \"testUrl\": \"https://ui.l1.constantcontact.com/rnavmap/evaluate.rnav?activepage=site.logout.eval\",\n" +
                "        \"location\": \"Bedford_W7_Chrome-FFX:Firefox\",\n" +
                "        \"from\": \"Bedford - Chrome, Firefox - <b>Firefox</b> - <b>Cable</b>\",\n" +
                "        \"connectivity\": \"Cable\",\n" +
                "        \"bwDown\": 5000,\n" +
                "        \"bwUp\": 1000,\n" +
                "        \"latency\": 28,\n" +
                "        \"plr\": \"0\",\n" +
                "        \"label\": \"l1.galileo.load-editor-toolkit.cc-us-east.firefox.cable.announcement-layout-2\",\n" +
                "        \"completed\": 1454084864,\n" +
                "        \"testerDNS\": \"10.20.72.5,10.20.72.6\",\n" +
                "        \"fvonly\": true,\n" +
                "        \"successfulFVRuns\": 6,\n" +
                "        \"average\": {\n" +
                "            \"firstView\": {\n" +
                "                \"URL\": 0,\n" +
                "                \"loadTime\": 3379.3333333333,\n" +
                "                \"TTFB\": 148.5,\n" +
                "                \"bytesOut\": 217169.5,\n" +
                "                \"bytesOutDoc\": 102733.16666667,\n" +
                "                \"bytesIn\": 1291767.8333333,\n" +
                "                \"bytesInDoc\": 695909.66666667,\n" +
                "                \"connections\": 24,\n" +
                "                \"requestsDoc\": 63.666666666667,\n" +
                "                \"responses_200\": 130.33333333333,\n" +
                "                \"responses_404\": 1,\n" +
                "                \"responses_other\": 0,\n" +
                "                \"result\": 99999,\n" +
                "                \"render\": 893.5,\n" +
                "                \"fullyLoaded\": 6503.8333333333,\n" +
                "                \"cached\": 0,\n" +
                "                \"docTime\": 3379.3333333333,\n" +
                "                \"domTime\": 0,\n" +
                "                \"score_cache\": 88,\n" +
                "                \"score_cdn\": 72,\n" +
                "                \"score_gzip\": 95,\n" +
                "                \"score_cookies\": -1,\n" +
                "                \"score_keep-alive\": 100,\n" +
                "                \"score_minify\": -1,\n" +
                "                \"score_combine\": 100,\n" +
                "                \"score_compress\": -1,\n" +
                "                \"score_etags\": -1,\n" +
                "                \"gzip_total\": 1100661.3333333,\n" +
                "                \"gzip_savings\": 45979,\n" +
                "                \"minify_total\": 0,\n" +
                "                \"minify_savings\": 0,\n" +
                "                \"image_total\": 0,\n" +
                "                \"image_savings\": 0,\n" +
                "                \"optimization_checked\": 1,\n" +
                "                \"aft\": 0,\n" +
                "                \"domElements\": 503.66666666667,\n" +
                "                \"pageSpeedVersion\": 1.9,\n" +
                "                \"title\": 0,\n" +
                "                \"titleTime\": 1513.5,\n" +
                "                \"loadEventStart\": 3347.6666666667,\n" +
                "                \"loadEventEnd\": 3349.8333333333,\n" +
                "                \"domContentLoadedEventStart\": 568.66666666667,\n" +
                "                \"domContentLoadedEventEnd\": 569.66666666667,\n" +
                "                \"lastVisualChange\": 5397.6666666667,\n" +
                "                \"browser_name\": 0,\n" +
                "                \"browser_version\": 28,\n" +
                "                \"server_count\": 1,\n" +
                "                \"server_rtt\": 9.5,\n" +
                "                \"base_page_cdn\": 0,\n" +
                "                \"adult_site\": 0,\n" +
                "                \"fixed_viewport\": 0,\n" +
                "                \"score_progressive_jpeg\": -1,\n" +
                "                \"firstPaint\": 0,\n" +
                "                \"docCPUms\": 2688.4171666667,\n" +
                "                \"fullyLoadedCPUms\": 6409.041,\n" +
                "                \"docCPUpct\": 78.333333333333,\n" +
                "                \"fullyLoadedCPUpct\": 29.666666666667,\n" +
                "                \"isResponsive\": -1,\n" +
                "                \"date\": 1454084890.3333,\n" +
                "                \"userTime.galileo.editor-started\": 1376.1666666667,\n" +
                "                \"userTime.galileo.editor-loading\": 1376.1666666667,\n" +
                "                \"userTime.galileo.app-config-retrieving\": 1962.1666666667,\n" +
                "                \"userTime.galileo.app-config-retrieved\": 2063,\n" +
                "                \"userTime.galileo.engine-retrieving\": 2064.1666666667,\n" +
                "                \"userTime.galileo.engine-retrieved\": 2704.5,\n" +
                "                \"userTime.galileo.customer-retrieving\": 2725.8333333333,\n" +
                "                \"userTime.galileo.registry-populating\": 2980.8333333333,\n" +
                "                \"userTime.galileo.registry-populated\": 3542.3333333333,\n" +
                "                \"userTime.galileo.palette-rendered\": 3595,\n" +
                "                \"userTime.galileo.template-document-retrieving\": 3599.8333333333,\n" +
                "                \"userTime.galileo.document-opened\": 3845.5,\n" +
                "                \"userTime.galileo.document-editor-initialized\": 4296.1666666667,\n" +
                "                \"userTime.galileo.document-rendered\": 4351,\n" +
                "                \"userTime.galileo.editor-starting\": 1059.8333333333,\n" +
                "                \"userTime.galileo.page-manager-initialized\": 2980.6666666667,\n" +
                "                \"userTime.galileo.palette-rendering\": 3573.1666666667,\n" +
                "                \"userTime.galileo.template-document-retrieved\": 3845.5,\n" +
                "                \"userTime.galileo.document-rendering\": 4296.1666666667,\n" +
                "                \"userTime.galileo.customer-retrieved\": 2988.6666666667,\n" +
                "                \"userTime.galileo.document-editor-initializing\": 3846.1666666667,\n" +
                "                \"userTime.galileo.page-manager-initializing\": 2723.3333333333,\n" +
                "                \"userTime.galileo.editor-ready\": 4351.3333333333,\n" +
                "                \"userTime.galileo.document-opening\": 3599.8333333333,\n" +
                "                \"userTime\": 4351.3333333333,\n" +
                "                \"SpeedIndex\": 2991.1666666667,\n" +
                "                \"visualComplete\": 5416.6666666667,\n" +
                "                \"effectiveBps\": 203499.83333333,\n" +
                "                \"effectiveBpsDoc\": 219882.33333333,\n" +
                "                \"avgRun\": 4\n" +
                "            }\n" +
                "        },\n" +
                "        \"standardDeviation\": {\n" +
                "            \"firstView\": {\n" +
                "                \"URL\": 0,\n" +
                "                \"loadTime\": 1394,\n" +
                "                \"TTFB\": 39,\n" +
                "                \"bytesOut\": 3683,\n" +
                "                \"bytesOutDoc\": 47679,\n" +
                "                \"bytesIn\": 5977,\n" +
                "                \"bytesInDoc\": 265184,\n" +
                "                \"connections\": 1,\n" +
                "                \"requests\": 0,\n" +
                "                \"requestsDoc\": 33,\n" +
                "                \"responses_200\": 0,\n" +
                "                \"responses_404\": 0,\n" +
                "                \"responses_other\": 0,\n" +
                "                \"result\": 0,\n" +
                "                \"render\": 230,\n" +
                "                \"fullyLoaded\": 246,\n" +
                "                \"cached\": 0,\n" +
                "                \"docTime\": 1394,\n" +
                "                \"domTime\": 0,\n" +
                "                \"score_cache\": 0,\n" +
                "                \"score_cdn\": 0,\n" +
                "                \"score_gzip\": 0,\n" +
                "                \"score_cookies\": 0,\n" +
                "                \"score_keep-alive\": 0,\n" +
                "                \"score_minify\": 0,\n" +
                "                \"score_combine\": 0,\n" +
                "                \"score_compress\": 0,\n" +
                "                \"score_etags\": 0,\n" +
                "                \"gzip_total\": 438,\n" +
                "                \"gzip_savings\": 0,\n" +
                "                \"minify_total\": 0,\n" +
                "                \"minify_savings\": 0,\n" +
                "                \"image_total\": 0,\n" +
                "                \"image_savings\": 0,\n" +
                "                \"optimization_checked\": 0,\n" +
                "                \"aft\": 0,\n" +
                "                \"domElements\": 708,\n" +
                "                \"pageSpeedVersion\": 0,\n" +
                "                \"title\": 0,\n" +
                "                \"titleTime\": 278,\n" +
                "                \"loadEventStart\": 1388,\n" +
                "                \"loadEventEnd\": 1389,\n" +
                "                \"domContentLoadedEventStart\": 250,\n" +
                "                \"domContentLoadedEventEnd\": 250,\n" +
                "                \"lastVisualChange\": 296,\n" +
                "                \"browser_name\": 0,\n" +
                "                \"browser_version\": 0,\n" +
                "                \"server_count\": 0,\n" +
                "                \"server_rtt\": 0,\n" +
                "                \"base_page_cdn\": 0,\n" +
                "                \"adult_site\": 0,\n" +
                "                \"fixed_viewport\": 0,\n" +
                "                \"score_progressive_jpeg\": 0,\n" +
                "                \"firstPaint\": 0,\n" +
                "                \"docCPUms\": 1199,\n" +
                "                \"fullyLoadedCPUms\": 197,\n" +
                "                \"docCPUpct\": 5,\n" +
                "                \"fullyLoadedCPUpct\": 0,\n" +
                "                \"isResponsive\": 0,\n" +
                "                \"date\": 431,\n" +
                "                \"userTime.galileo.editor-started\": 261,\n" +
                "                \"userTime.galileo.editor-loading\": 261,\n" +
                "                \"userTime.galileo.app-config-retrieving\": 265,\n" +
                "                \"userTime.galileo.app-config-retrieved\": 260,\n" +
                "                \"userTime.galileo.engine-retrieving\": 260,\n" +
                "                \"userTime.galileo.engine-retrieved\": 277,\n" +
                "                \"userTime.galileo.customer-retrieving\": 280,\n" +
                "                \"userTime.galileo.registry-populating\": 260,\n" +
                "                \"userTime.galileo.registry-populated\": 262,\n" +
                "                \"userTime.galileo.palette-rendered\": 262,\n" +
                "                \"userTime.galileo.template-document-retrieving\": 262,\n" +
                "                \"userTime.galileo.document-opened\": 274,\n" +
                "                \"userTime.galileo.document-editor-initialized\": 269,\n" +
                "                \"userTime.galileo.document-rendered\": 271,\n" +
                "                \"userTime.galileo.editor-starting\": 282,\n" +
                "                \"userTime.galileo.page-manager-initialized\": 260,\n" +
                "                \"userTime.galileo.palette-rendering\": 262,\n" +
                "                \"userTime.galileo.template-document-retrieved\": 274,\n" +
                "                \"userTime.galileo.document-rendering\": 269,\n" +
                "                \"userTime.galileo.customer-retrieved\": 260,\n" +
                "                \"userTime.galileo.document-editor-initializing\": 274,\n" +
                "                \"userTime.galileo.page-manager-initializing\": 279,\n" +
                "                \"userTime.galileo.editor-ready\": 271,\n" +
                "                \"userTime.galileo.document-opening\": 262,\n" +
                "                \"userTime\": 271,\n" +
                "                \"SpeedIndex\": 242,\n" +
                "                \"visualComplete\": 307,\n" +
                "                \"effectiveBps\": 7023,\n" +
                "                \"effectiveBpsDoc\": 20835,\n" +
                "                \"avgRun\": null\n" +
                "            }\n" +
                "        },\n" +
                "        \"median\": {\n" +
                "            \"firstView\": {\n" +
                "                \"URL\": \"https://ui.l1.constantcontact.com/rnavmap/distui/em-ui/wizard/create?campaignType=11&amp;step=templatePicker\",\n" +
                "                \"loadTime\": 2614,\n" +
                "                \"TTFB\": 125,\n" +
                "                \"bytesOut\": 217114,\n" +
                "                \"bytesOutDoc\": 83867,\n" +
                "                \"bytesIn\": 1289427,\n" +
                "                \"bytesInDoc\": 574792,\n" +
                "                \"connections\": 24,\n" +
                "                \"requestsDoc\": 49,\n" +
                "                \"responses_200\": 130,\n" +
                "                \"responses_404\": 1,\n" +
                "                \"responses_other\": 0,\n" +
                "                \"result\": 99999,\n" +
                "                \"render\": 726,\n" +
                "                \"fullyLoaded\": 6399,\n" +
                "                \"cached\": 0,\n" +
                "                \"docTime\": 2614,\n" +
                "                \"domTime\": 0,\n" +
                "                \"score_cache\": 88,\n" +
                "                \"score_cdn\": 72,\n" +
                "                \"score_gzip\": 95,\n" +
                "                \"score_cookies\": -1,\n" +
                "                \"score_keep-alive\": 100,\n" +
                "                \"score_minify\": -1,\n" +
                "                \"score_combine\": 100,\n" +
                "                \"score_compress\": -1,\n" +
                "                \"score_etags\": -1,\n" +
                "                \"gzip_total\": 1100469,\n" +
                "                \"gzip_savings\": 45979,\n" +
                "                \"minify_total\": 0,\n" +
                "                \"minify_savings\": 0,\n" +
                "                \"image_total\": 0,\n" +
                "                \"image_savings\": 0,\n" +
                "                \"optimization_checked\": 1,\n" +
                "                \"aft\": 0,\n" +
                "                \"domElements\": 185,\n" +
                "                \"pageSpeedVersion\": \"1.9\",\n" +
                "                \"title\": \"Customize Your Email\",\n" +
                "                \"titleTime\": 1290,\n" +
                "                \"loadEventStart\": 2581,\n" +
                "                \"loadEventEnd\": 2583,\n" +
                "                \"domContentLoadedEventStart\": 427,\n" +
                "                \"domContentLoadedEventEnd\": 428,\n" +
                "                \"lastVisualChange\": 5271,\n" +
                "                \"browser_name\": \"Firefox\",\n" +
                "                \"browser_version\": \"28.0.0.5186\",\n" +
                "                \"server_count\": 1,\n" +
                "                \"server_rtt\": 9,\n" +
                "                \"base_page_cdn\": \"\",\n" +
                "                \"adult_site\": 0,\n" +
                "                \"fixed_viewport\": 0,\n" +
                "                \"score_progressive_jpeg\": -1,\n" +
                "                \"firstPaint\": 0,\n" +
                "                \"docCPUms\": 2152.814,\n" +
                "                \"fullyLoadedCPUms\": 6739.243,\n" +
                "                \"docCPUpct\": 82,\n" +
                "                \"fullyLoadedCPUpct\": 31,\n" +
                "                \"isResponsive\": -1,\n" +
                "                \"date\": 1454084512,\n" +
                "                \"userTime.galileo.editor-started\": 1211,\n" +
                "                \"userTime.galileo.editor-loading\": 1211,\n" +
                "                \"userTime.galileo.app-config-retrieving\": 1801,\n" +
                "                \"userTime.galileo.app-config-retrieved\": 1922,\n" +
                "                \"userTime.galileo.engine-retrieving\": 1924,\n" +
                "                \"userTime.galileo.engine-retrieved\": 2600,\n" +
                "                \"userTime.galileo.customer-retrieving\": 2621,\n" +
                "                \"userTime.galileo.registry-populating\": 2882,\n" +
                "                \"userTime.galileo.registry-populated\": 3448,\n" +
                "                \"userTime.galileo.palette-rendered\": 3501,\n" +
                "                \"userTime.galileo.template-document-retrieving\": 3506,\n" +
                "                \"userTime.galileo.document-opened\": 3780,\n" +
                "                \"userTime.galileo.document-editor-initialized\": 4273,\n" +
                "                \"userTime.galileo.document-rendered\": 4326,\n" +
                "                \"userTime.galileo.editor-starting\": 841,\n" +
                "                \"userTime.galileo.page-manager-initialized\": 2882,\n" +
                "                \"userTime.galileo.palette-rendering\": 3480,\n" +
                "                \"userTime.galileo.template-document-retrieved\": 3780,\n" +
                "                \"userTime.galileo.document-rendering\": 4273,\n" +
                "                \"userTime.galileo.customer-retrieved\": 2890,\n" +
                "                \"userTime.galileo.document-editor-initializing\": 3780,\n" +
                "                \"userTime.galileo.page-manager-initializing\": 2618,\n" +
                "                \"userTime.galileo.editor-ready\": 4327,\n" +
                "                \"userTime.galileo.document-opening\": 3506,\n" +
                "                \"userTime\": 4327,\n" +
                "                \"SpeedIndex\": 2812,\n" +
                "                \"visualComplete\": 5300,\n" +
                "                \"effectiveBps\": 205519,\n" +
                "                \"effectiveBpsDoc\": 230932,\n" +
                "                \"run\": 2,\n" +
                "                \"pages\": {\n" +
                "                    \"details\": \"http://wpt.roving.com/details.php?test=160129_F1_4&run=2&cached=0\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/performance_optimization.php?test=160129_F1_4&run=2&cached=0\",\n" +
                "                    \"breakdown\": \"http://wpt.roving.com/breakdown.php?test=160129_F1_4&run=2&cached=0\",\n" +
                "                    \"domains\": \"http://wpt.roving.com/domains.php?test=160129_F1_4&run=2&cached=0\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/screen_shot.php?test=160129_F1_4&run=2&cached=0\"\n" +
                "                },\n" +
                "                \"thumbnails\": {\n" +
                "                    \"waterfall\": \"http://wpt.roving.com/result/160129_F1_4/2_waterfall_thumb.png\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/result/160129_F1_4/2_optimization_thumb.png\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/result/160129_F1_4/2_screen_thumb.png\"\n" +
                "                },\n" +
                "                \"images\": {\n" +
                "                    \"waterfall\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_waterfall.png\",\n" +
                "                    \"connectionView\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_connection.png\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_optimization.png\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_screen.jpg\"\n" +
                "                },\n" +
                "                \"rawData\": {\n" +
                "                    \"headers\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_report.txt\",\n" +
                "                    \"pageData\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_IEWPG.txt\",\n" +
                "                    \"requestsData\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_IEWTR.txt\",\n" +
                "                    \"utilization\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_progress.csv\"\n" +
                "                },\n" +
                "                \"domains\": {\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu-me\": {\n" +
                "                        \"bytes\": 11320,\n" +
                "                        \"requests\": 6,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.nas\": {\n" +
                "                        \"bytes\": 1426,\n" +
                "                        \"requests\": 2,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.ndctctc.citats\": {\n" +
                "                        \"bytes\": 766228,\n" +
                "                        \"requests\": 65,\n" +
                "                        \"connections\": 6\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu-ngiapmac\": {\n" +
                "                        \"bytes\": 13099,\n" +
                "                        \"requests\": 3,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu\": {\n" +
                "                        \"bytes\": 129764,\n" +
                "                        \"requests\": 29,\n" +
                "                        \"connections\": 6\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iutsid\": {\n" +
                "                        \"bytes\": 7388,\n" +
                "                        \"requests\": 12,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.mroftalp-htua\": {\n" +
                "                        \"bytes\": 20246,\n" +
                "                        \"requests\": 7,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"ten.cdrtmo.tt.tcatnoctnatsnoc\": {\n" +
                "                        \"bytes\": 1189,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.qimuilaet.1-tsae-su-duolcatad\": {\n" +
                "                        \"bytes\": 914,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"ten.atad-rn.mab\": {\n" +
                "                        \"bytes\": 690,\n" +
                "                        \"requests\": 5,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.dcmys.ng\": {\n" +
                "                        \"bytes\": 1773,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iulm\": {\n" +
                "                        \"bytes\": 102688,\n" +
                "                        \"requests\": 7,\n" +
                "                        \"connections\": 5\n" +
                "                    },\n" +
                "                    \"ten.tnorfduolc.4nzmoc8hi0emd\": {\n" +
                "                        \"bytes\": 67065,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.gmi\": {\n" +
                "                        \"bytes\": 10157,\n" +
                "                        \"requests\": 2,\n" +
                "                        \"connections\": 2\n" +
                "                    }\n" +
                "                },\n" +
                "                \"breakdown\": {\n" +
                "                    \"html\": {\n" +
                "                        \"color\": [\n" +
                "                            130,\n" +
                "                            181,\n" +
                "                            252\n" +
                "                        ],\n" +
                "                        \"bytes\": 49762,\n" +
                "                        \"requests\": 8\n" +
                "                    },\n" +
                "                    \"js\": {\n" +
                "                        \"color\": [\n" +
                "                            254,\n" +
                "                            197,\n" +
                "                            132\n" +
                "                        ],\n" +
                "                        \"bytes\": 931410,\n" +
                "                        \"requests\": 67\n" +
                "                    },\n" +
                "                    \"css\": {\n" +
                "                        \"color\": [\n" +
                "                            178,\n" +
                "                            234,\n" +
                "                            148\n" +
                "                        ],\n" +
                "                        \"bytes\": 93043,\n" +
                "                        \"requests\": 14\n" +
                "                    },\n" +
                "                    \"image\": {\n" +
                "                        \"color\": [\n" +
                "                            196,\n" +
                "                            154,\n" +
                "                            232\n" +
                "                        ],\n" +
                "                        \"bytes\": 43142,\n" +
                "                        \"requests\": 27\n" +
                "                    },\n" +
                "                    \"flash\": {\n" +
                "                        \"color\": [\n" +
                "                            45,\n" +
                "                            183,\n" +
                "                            193\n" +
                "                        ],\n" +
                "                        \"bytes\": 0,\n" +
                "                        \"requests\": 0\n" +
                "                    },\n" +
                "                    \"font\": {\n" +
                "                        \"color\": [\n" +
                "                            255,\n" +
                "                            82,\n" +
                "                            62\n" +
                "                        ],\n" +
                "                        \"bytes\": 0,\n" +
                "                        \"requests\": 0\n" +
                "                    },\n" +
                "                    \"other\": {\n" +
                "                        \"color\": [\n" +
                "                            196,\n" +
                "                            196,\n" +
                "                            196\n" +
                "                        ],\n" +
                "                        \"bytes\": 16590,\n" +
                "                        \"requests\": 26\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"max\": {\n" +
                "            \"firstView\": {\n" +
                "                \"URL\": \"https://ui.l1.constantcontact.com/rnavmap/distui/em-ui/wizard/create?campaignType=11&amp;step=templatePicker\",\n" +
                "                \"loadTime\": 3310,\n" +
                "                \"TTFB\": 219,\n" +
                "                \"bytesOut\": 218460,\n" +
                "                \"bytesOutDoc\": 77249,\n" +
                "                \"bytesIn\": 1297919,\n" +
                "                \"bytesInDoc\": 572555,\n" +
                "                \"connections\": 21,\n" +
                "                \"requestsDoc\": 47,\n" +
                "                \"responses_200\": 130,\n" +
                "                \"responses_404\": 1,\n" +
                "                \"responses_other\": 0,\n" +
                "                \"result\": 99999,\n" +
                "                \"render\": 1403,\n" +
                "                \"fullyLoaded\": 6980,\n" +
                "                \"cached\": 0,\n" +
                "                \"docTime\": 3310,\n" +
                "                \"domTime\": 0,\n" +
                "                \"score_cache\": 88,\n" +
                "                \"score_cdn\": 72,\n" +
                "                \"score_gzip\": 95,\n" +
                "                \"score_cookies\": -1,\n" +
                "                \"score_keep-alive\": 100,\n" +
                "                \"score_minify\": -1,\n" +
                "                \"score_combine\": 100,\n" +
                "                \"score_compress\": -1,\n" +
                "                \"score_etags\": -1,\n" +
                "                \"gzip_total\": 1100477,\n" +
                "                \"gzip_savings\": 45979,\n" +
                "                \"minify_total\": 0,\n" +
                "                \"minify_savings\": 0,\n" +
                "                \"image_total\": 0,\n" +
                "                \"image_savings\": 0,\n" +
                "                \"optimization_checked\": 1,\n" +
                "                \"aft\": 0,\n" +
                "                \"domElements\": 185,\n" +
                "                \"pageSpeedVersion\": \"1.9\",\n" +
                "                \"title\": \"Customize Your Email\",\n" +
                "                \"titleTime\": 2118,\n" +
                "                \"loadEventStart\": 3273,\n" +
                "                \"loadEventEnd\": 3274,\n" +
                "                \"domContentLoadedEventStart\": 1125,\n" +
                "                \"domContentLoadedEventEnd\": 1126,\n" +
                "                \"lastVisualChange\": 6052,\n" +
                "                \"browser_name\": \"Firefox\",\n" +
                "                \"browser_version\": \"28.0.0.5186\",\n" +
                "                \"server_count\": 1,\n" +
                "                \"server_rtt\": 10,\n" +
                "                \"base_page_cdn\": \"\",\n" +
                "                \"adult_site\": 0,\n" +
                "                \"fixed_viewport\": 0,\n" +
                "                \"score_progressive_jpeg\": -1,\n" +
                "                \"firstPaint\": 0,\n" +
                "                \"docCPUms\": 2215.214,\n" +
                "                \"fullyLoadedCPUms\": 6286.84,\n" +
                "                \"docCPUpct\": 66,\n" +
                "                \"fullyLoadedCPUpct\": 29,\n" +
                "                \"isResponsive\": -1,\n" +
                "                \"date\": 1454085016,\n" +
                "                \"userTime.galileo.editor-started\": 1951,\n" +
                "                \"userTime.galileo.editor-loading\": 1951,\n" +
                "                \"userTime.galileo.app-config-retrieving\": 2548,\n" +
                "                \"userTime.galileo.app-config-retrieved\": 2635,\n" +
                "                \"userTime.galileo.engine-retrieving\": 2636,\n" +
                "                \"userTime.galileo.engine-retrieved\": 3316,\n" +
                "                \"userTime.galileo.customer-retrieving\": 3343,\n" +
                "                \"userTime.galileo.registry-populating\": 3539,\n" +
                "                \"userTime.galileo.registry-populated\": 4104,\n" +
                "                \"userTime.galileo.palette-rendered\": 4157,\n" +
                "                \"userTime.galileo.template-document-retrieving\": 4162,\n" +
                "                \"userTime.galileo.document-opened\": 4354,\n" +
                "                \"userTime.galileo.document-editor-initialized\": 4781,\n" +
                "                \"userTime.galileo.document-rendered\": 4832,\n" +
                "                \"userTime.galileo.editor-starting\": 1671,\n" +
                "                \"userTime.galileo.page-manager-initialized\": 3538,\n" +
                "                \"userTime.galileo.palette-rendering\": 4134,\n" +
                "                \"userTime.galileo.template-document-retrieved\": 4354,\n" +
                "                \"userTime.galileo.document-rendering\": 4781,\n" +
                "                \"userTime.galileo.customer-retrieved\": 3546,\n" +
                "                \"userTime.galileo.document-editor-initializing\": 4354,\n" +
                "                \"userTime.galileo.page-manager-initializing\": 3340,\n" +
                "                \"userTime.galileo.editor-ready\": 4833,\n" +
                "                \"userTime.galileo.document-opening\": 4162,\n" +
                "                \"userTime\": 4833,\n" +
                "                \"SpeedIndex\": 3524,\n" +
                "                \"visualComplete\": 6100,\n" +
                "                \"effectiveBps\": 191971,\n" +
                "                \"effectiveBpsDoc\": 185232,\n" +
                "                \"run\": 4,\n" +
                "                \"pages\": {\n" +
                "                    \"details\": \"http://wpt.roving.com/details.php?test=160129_F1_4&run=4&cached=0\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/performance_optimization.php?test=160129_F1_4&run=4&cached=0\",\n" +
                "                    \"breakdown\": \"http://wpt.roving.com/breakdown.php?test=160129_F1_4&run=4&cached=0\",\n" +
                "                    \"domains\": \"http://wpt.roving.com/domains.php?test=160129_F1_4&run=4&cached=0\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/screen_shot.php?test=160129_F1_4&run=4&cached=0\"\n" +
                "                },\n" +
                "                \"thumbnails\": {\n" +
                "                    \"waterfall\": \"http://wpt.roving.com/result/160129_F1_4/4_waterfall_thumb.png\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/result/160129_F1_4/4_optimization_thumb.png\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/result/160129_F1_4/4_screen_thumb.png\"\n" +
                "                },\n" +
                "                \"images\": {\n" +
                "                    \"waterfall\": \"http://wpt.roving.com/results/16/01/29/F1/4/4_waterfall.png\",\n" +
                "                    \"connectionView\": \"http://wpt.roving.com/results/16/01/29/F1/4/4_connection.png\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/results/16/01/29/F1/4/4_optimization.png\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/results/16/01/29/F1/4/4_screen.jpg\"\n" +
                "                },\n" +
                "                \"rawData\": {\n" +
                "                    \"headers\": \"http://wpt.roving.com/results/16/01/29/F1/4/4_report.txt\",\n" +
                "                    \"pageData\": \"http://wpt.roving.com/results/16/01/29/F1/4/4_IEWPG.txt\",\n" +
                "                    \"requestsData\": \"http://wpt.roving.com/results/16/01/29/F1/4/4_IEWTR.txt\",\n" +
                "                    \"utilization\": \"http://wpt.roving.com/results/16/01/29/F1/4/4_progress.csv\"\n" +
                "                },\n" +
                "                \"domains\": {\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu-me\": {\n" +
                "                        \"bytes\": 11322,\n" +
                "                        \"requests\": 6,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.nas\": {\n" +
                "                        \"bytes\": 1426,\n" +
                "                        \"requests\": 2,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.ndctctc.citats\": {\n" +
                "                        \"bytes\": 766228,\n" +
                "                        \"requests\": 65,\n" +
                "                        \"connections\": 6\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu-ngiapmac\": {\n" +
                "                        \"bytes\": 13099,\n" +
                "                        \"requests\": 3,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu\": {\n" +
                "                        \"bytes\": 129771,\n" +
                "                        \"requests\": 29,\n" +
                "                        \"connections\": 6\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iutsid\": {\n" +
                "                        \"bytes\": 7386,\n" +
                "                        \"requests\": 12,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.mroftalp-htua\": {\n" +
                "                        \"bytes\": 20245,\n" +
                "                        \"requests\": 7,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"ten.cdrtmo.tt.tcatnoctnatsnoc\": {\n" +
                "                        \"bytes\": 1187,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.qimuilaet.1-tsae-su-duolcatad\": {\n" +
                "                        \"bytes\": 914,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"ten.atad-rn.mab\": {\n" +
                "                        \"bytes\": 690,\n" +
                "                        \"requests\": 5,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.dcmys.ng\": {\n" +
                "                        \"bytes\": 1773,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iulm\": {\n" +
                "                        \"bytes\": 102689,\n" +
                "                        \"requests\": 7,\n" +
                "                        \"connections\": 5\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.gmi\": {\n" +
                "                        \"bytes\": 10157,\n" +
                "                        \"requests\": 2,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"ten.tnorfduolc.4nzmoc8hi0emd\": {\n" +
                "                        \"bytes\": 67065,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    }\n" +
                "                },\n" +
                "                \"breakdown\": {\n" +
                "                    \"html\": {\n" +
                "                        \"color\": [\n" +
                "                            130,\n" +
                "                            181,\n" +
                "                            252\n" +
                "                        ],\n" +
                "                        \"bytes\": 49769,\n" +
                "                        \"requests\": 8\n" +
                "                    },\n" +
                "                    \"js\": {\n" +
                "                        \"color\": [\n" +
                "                            254,\n" +
                "                            197,\n" +
                "                            132\n" +
                "                        ],\n" +
                "                        \"bytes\": 931410,\n" +
                "                        \"requests\": 67\n" +
                "                    },\n" +
                "                    \"css\": {\n" +
                "                        \"color\": [\n" +
                "                            178,\n" +
                "                            234,\n" +
                "                            148\n" +
                "                        ],\n" +
                "                        \"bytes\": 93043,\n" +
                "                        \"requests\": 14\n" +
                "                    },\n" +
                "                    \"image\": {\n" +
                "                        \"color\": [\n" +
                "                            196,\n" +
                "                            154,\n" +
                "                            232\n" +
                "                        ],\n" +
                "                        \"bytes\": 43142,\n" +
                "                        \"requests\": 27\n" +
                "                    },\n" +
                "                    \"flash\": {\n" +
                "                        \"color\": [\n" +
                "                            45,\n" +
                "                            183,\n" +
                "                            193\n" +
                "                        ],\n" +
                "                        \"bytes\": 0,\n" +
                "                        \"requests\": 0\n" +
                "                    },\n" +
                "                    \"font\": {\n" +
                "                        \"color\": [\n" +
                "                            255,\n" +
                "                            82,\n" +
                "                            62\n" +
                "                        ],\n" +
                "                        \"bytes\": 0,\n" +
                "                        \"requests\": 0\n" +
                "                    },\n" +
                "                    \"other\": {\n" +
                "                        \"color\": [\n" +
                "                            196,\n" +
                "                            196,\n" +
                "                            196\n" +
                "                        ],\n" +
                "                        \"bytes\": 16588,\n" +
                "                        \"requests\": 26\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"min\": {\n" +
                "            \"firstView\": {\n" +
                "                \"URL\": \"https://ui.l1.constantcontact.com/rnavmap/distui/em-ui/wizard/create?campaignType=11&amp;step=templatePicker\",\n" +
                "                \"loadTime\": 2614,\n" +
                "                \"TTFB\": 125,\n" +
                "                \"bytesOut\": 217114,\n" +
                "                \"bytesOutDoc\": 83867,\n" +
                "                \"bytesIn\": 1289427,\n" +
                "                \"bytesInDoc\": 574792,\n" +
                "                \"connections\": 24,\n" +
                "                \"requestsDoc\": 49,\n" +
                "                \"responses_200\": 130,\n" +
                "                \"responses_404\": 1,\n" +
                "                \"responses_other\": 0,\n" +
                "                \"result\": 99999,\n" +
                "                \"render\": 726,\n" +
                "                \"fullyLoaded\": 6399,\n" +
                "                \"cached\": 0,\n" +
                "                \"docTime\": 2614,\n" +
                "                \"domTime\": 0,\n" +
                "                \"score_cache\": 88,\n" +
                "                \"score_cdn\": 72,\n" +
                "                \"score_gzip\": 95,\n" +
                "                \"score_cookies\": -1,\n" +
                "                \"score_keep-alive\": 100,\n" +
                "                \"score_minify\": -1,\n" +
                "                \"score_combine\": 100,\n" +
                "                \"score_compress\": -1,\n" +
                "                \"score_etags\": -1,\n" +
                "                \"gzip_total\": 1100469,\n" +
                "                \"gzip_savings\": 45979,\n" +
                "                \"minify_total\": 0,\n" +
                "                \"minify_savings\": 0,\n" +
                "                \"image_total\": 0,\n" +
                "                \"image_savings\": 0,\n" +
                "                \"optimization_checked\": 1,\n" +
                "                \"aft\": 0,\n" +
                "                \"domElements\": 185,\n" +
                "                \"pageSpeedVersion\": \"1.9\",\n" +
                "                \"title\": \"Customize Your Email\",\n" +
                "                \"titleTime\": 1290,\n" +
                "                \"loadEventStart\": 2581,\n" +
                "                \"loadEventEnd\": 2583,\n" +
                "                \"domContentLoadedEventStart\": 427,\n" +
                "                \"domContentLoadedEventEnd\": 428,\n" +
                "                \"lastVisualChange\": 5271,\n" +
                "                \"browser_name\": \"Firefox\",\n" +
                "                \"browser_version\": \"28.0.0.5186\",\n" +
                "                \"server_count\": 1,\n" +
                "                \"server_rtt\": 9,\n" +
                "                \"base_page_cdn\": \"\",\n" +
                "                \"adult_site\": 0,\n" +
                "                \"fixed_viewport\": 0,\n" +
                "                \"score_progressive_jpeg\": -1,\n" +
                "                \"firstPaint\": 0,\n" +
                "                \"docCPUms\": 2152.814,\n" +
                "                \"fullyLoadedCPUms\": 6739.243,\n" +
                "                \"docCPUpct\": 82,\n" +
                "                \"fullyLoadedCPUpct\": 31,\n" +
                "                \"isResponsive\": -1,\n" +
                "                \"date\": 1454084512,\n" +
                "                \"userTime.galileo.editor-started\": 1211,\n" +
                "                \"userTime.galileo.editor-loading\": 1211,\n" +
                "                \"userTime.galileo.app-config-retrieving\": 1801,\n" +
                "                \"userTime.galileo.app-config-retrieved\": 1922,\n" +
                "                \"userTime.galileo.engine-retrieving\": 1924,\n" +
                "                \"userTime.galileo.engine-retrieved\": 2600,\n" +
                "                \"userTime.galileo.customer-retrieving\": 2621,\n" +
                "                \"userTime.galileo.registry-populating\": 2882,\n" +
                "                \"userTime.galileo.registry-populated\": 3448,\n" +
                "                \"userTime.galileo.palette-rendered\": 3501,\n" +
                "                \"userTime.galileo.template-document-retrieving\": 3506,\n" +
                "                \"userTime.galileo.document-opened\": 3780,\n" +
                "                \"userTime.galileo.document-editor-initialized\": 4273,\n" +
                "                \"userTime.galileo.document-rendered\": 4326,\n" +
                "                \"userTime.galileo.editor-starting\": 841,\n" +
                "                \"userTime.galileo.page-manager-initialized\": 2882,\n" +
                "                \"userTime.galileo.palette-rendering\": 3480,\n" +
                "                \"userTime.galileo.template-document-retrieved\": 3780,\n" +
                "                \"userTime.galileo.document-rendering\": 4273,\n" +
                "                \"userTime.galileo.customer-retrieved\": 2890,\n" +
                "                \"userTime.galileo.document-editor-initializing\": 3780,\n" +
                "                \"userTime.galileo.page-manager-initializing\": 2618,\n" +
                "                \"userTime.galileo.editor-ready\": 4327,\n" +
                "                \"userTime.galileo.document-opening\": 3506,\n" +
                "                \"userTime\": 4327,\n" +
                "                \"SpeedIndex\": 2812,\n" +
                "                \"visualComplete\": 5300,\n" +
                "                \"effectiveBps\": 205519,\n" +
                "                \"effectiveBpsDoc\": 230932,\n" +
                "                \"run\": 2,\n" +
                "                \"pages\": {\n" +
                "                    \"details\": \"http://wpt.roving.com/details.php?test=160129_F1_4&run=2&cached=0\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/performance_optimization.php?test=160129_F1_4&run=2&cached=0\",\n" +
                "                    \"breakdown\": \"http://wpt.roving.com/breakdown.php?test=160129_F1_4&run=2&cached=0\",\n" +
                "                    \"domains\": \"http://wpt.roving.com/domains.php?test=160129_F1_4&run=2&cached=0\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/screen_shot.php?test=160129_F1_4&run=2&cached=0\"\n" +
                "                },\n" +
                "                \"thumbnails\": {\n" +
                "                    \"waterfall\": \"http://wpt.roving.com/result/160129_F1_4/2_waterfall_thumb.png\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/result/160129_F1_4/2_optimization_thumb.png\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/result/160129_F1_4/2_screen_thumb.png\"\n" +
                "                },\n" +
                "                \"images\": {\n" +
                "                    \"waterfall\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_waterfall.png\",\n" +
                "                    \"connectionView\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_connection.png\",\n" +
                "                    \"checklist\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_optimization.png\",\n" +
                "                    \"screenShot\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_screen.jpg\"\n" +
                "                },\n" +
                "                \"rawData\": {\n" +
                "                    \"headers\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_report.txt\",\n" +
                "                    \"pageData\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_IEWPG.txt\",\n" +
                "                    \"requestsData\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_IEWTR.txt\",\n" +
                "                    \"utilization\": \"http://wpt.roving.com/results/16/01/29/F1/4/2_progress.csv\"\n" +
                "                },\n" +
                "                \"domains\": {\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu-me\": {\n" +
                "                        \"bytes\": 11320,\n" +
                "                        \"requests\": 6,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.nas\": {\n" +
                "                        \"bytes\": 1426,\n" +
                "                        \"requests\": 2,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.ndctctc.citats\": {\n" +
                "                        \"bytes\": 766228,\n" +
                "                        \"requests\": 65,\n" +
                "                        \"connections\": 6\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu-ngiapmac\": {\n" +
                "                        \"bytes\": 13099,\n" +
                "                        \"requests\": 3,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iu\": {\n" +
                "                        \"bytes\": 129764,\n" +
                "                        \"requests\": 29,\n" +
                "                        \"connections\": 6\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iutsid\": {\n" +
                "                        \"bytes\": 7388,\n" +
                "                        \"requests\": 12,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.mroftalp-htua\": {\n" +
                "                        \"bytes\": 20246,\n" +
                "                        \"requests\": 7,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"ten.cdrtmo.tt.tcatnoctnatsnoc\": {\n" +
                "                        \"bytes\": 1189,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.qimuilaet.1-tsae-su-duolcatad\": {\n" +
                "                        \"bytes\": 914,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"ten.atad-rn.mab\": {\n" +
                "                        \"bytes\": 690,\n" +
                "                        \"requests\": 5,\n" +
                "                        \"connections\": 2\n" +
                "                    },\n" +
                "                    \"moc.dcmys.ng\": {\n" +
                "                        \"bytes\": 1773,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.iulm\": {\n" +
                "                        \"bytes\": 102688,\n" +
                "                        \"requests\": 7,\n" +
                "                        \"connections\": 5\n" +
                "                    },\n" +
                "                    \"ten.tnorfduolc.4nzmoc8hi0emd\": {\n" +
                "                        \"bytes\": 67065,\n" +
                "                        \"requests\": 1,\n" +
                "                        \"connections\": 1\n" +
                "                    },\n" +
                "                    \"moc.tcatnoctnatsnoc.1l.gmi\": {\n" +
                "                        \"bytes\": 10157,\n" +
                "                        \"requests\": 2,\n" +
                "                        \"connections\": 2\n" +
                "                    }\n" +
                "                },\n" +
                "                \"breakdown\": {\n" +
                "                    \"html\": {\n" +
                "                        \"color\": [\n" +
                "                            130,\n" +
                "                            181,\n" +
                "                            252\n" +
                "                        ],\n" +
                "                        \"bytes\": 49762,\n" +
                "                        \"requests\": 8\n" +
                "                    },\n" +
                "                    \"js\": {\n" +
                "                        \"color\": [\n" +
                "                            254,\n" +
                "                            197,\n" +
                "                            132\n" +
                "                        ],\n" +
                "                        \"bytes\": 931410,\n" +
                "                        \"requests\": 67\n" +
                "                    },\n" +
                "                    \"css\": {\n" +
                "                        \"color\": [\n" +
                "                            178,\n" +
                "                            234,\n" +
                "                            148\n" +
                "                        ],\n" +
                "                        \"bytes\": 93043,\n" +
                "                        \"requests\": 14\n" +
                "                    },\n" +
                "                    \"image\": {\n" +
                "                        \"color\": [\n" +
                "                            196,\n" +
                "                            154,\n" +
                "                            232\n" +
                "                        ],\n" +
                "                        \"bytes\": 43142,\n" +
                "                        \"requests\": 27\n" +
                "                    },\n" +
                "                    \"flash\": {\n" +
                "                        \"color\": [\n" +
                "                            45,\n" +
                "                            183,\n" +
                "                            193\n" +
                "                        ],\n" +
                "                        \"bytes\": 0,\n" +
                "                        \"requests\": 0\n" +
                "                    },\n" +
                "                    \"font\": {\n" +
                "                        \"color\": [\n" +
                "                            255,\n" +
                "                            82,\n" +
                "                            62\n" +
                "                        ],\n" +
                "                        \"bytes\": 0,\n" +
                "                        \"requests\": 0\n" +
                "                    },\n" +
                "                    \"other\": {\n" +
                "                        \"color\": [\n" +
                "                            196,\n" +
                "                            196,\n" +
                "                            196\n" +
                "                        ],\n" +
                "                        \"bytes\": 16590,\n" +
                "                        \"requests\": 26\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"rawDataId\": null,\n" +
                "        \"statusCode\": 200,\n" +
                "        \"statusText\": \"Test Complete\"\n" +
                "    }\n" +
                "}";




    } */

    @Test
    @Ignore
    public void buildHtmlTableFromSlas() throws Exception {
        String json = new HttpService().sendPost("http://opp.roving.com/uxsvc/v2/rrux/importWpt", new HashMap<String, String>() {{
            put("wptId", "160129_F1_4");
            put("saveToGraphite", "false");
            put("slas", "{    \"successfulFVRuns\": 6,    \"median\": {       \"firstView\": {          \"TTFB\": 500, \"SpeedIndex\": 3000, \"visualComplete\": 3000,    \"breakdown\": {             \"js\": { \"requests\": 10 },             \"css\": { \"requests\": 5 }          }       }    } }");
        }}).getResp();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        RrResultResponse rrResultResponse = gson.fromJson(json, RrResultResponse.class);
        assertTrue(rrResultResponse.getSlaResult().getTotalSlas() == 8);
        assertTrue(((Double) (rrResultResponse.getTest().get("successfulFVRuns"))).intValue() == 0);

       // build slas here


        String response = "{\n" +
                "\"statusCode\": 200,\n" +
                "\"statusText\": \"Ok\",\n" +
                "\"data\": {\n" +
                "\"testId\": \"160121_XV_17\",\n" +
                "\"ownerKey\": \"8c01305f6579bebf0b2eb9e04cb8c4541a73583e\",\n" +
                "\"jsonUrl\": \"http://wpt.roving.com/jsonResult.php?test=160121_XV_17\",\n" +
                "\"xmlUrl\": \"http://wpt.roving.com/xmlResult/160121_XV_17/\",\n" +
                "\"userUrl\": \"http://wpt.roving.com/result/160121_XV_17/\",\n" +
                "\"summaryCSV\": \"http://wpt.roving.com/result/160121_XV_17/page_data.csv\",\n" +
                "\"detailCSV\": \"http://wpt.roving.com/result/160121_XV_17/requests.csv\"\n" +
                "}\n" +
                "}";

        HtmlResultService htmlResultService = new HtmlResultService();
        HipchatHtmlResultService hipchatHtmlResultService = new HipchatHtmlResultService();
        String results;
       // String wptHtmlRunDetails = htmlResultService.getWptResultSummaryHtml("160104_5X_X", "http:\\foo", new JsonParser().parse(response).getAsJsonObject());
        //String wptHtmlRunDetails = hipchatHtmlResultService.getWptResultSummaryHtml("160104_5X_X", "http:\\foo", new JsonParser().parse(response).getAsJsonObject());

        // System.out.println(wptHtmlRunDetails);

        //wptHtmlRunDetails += htmlResultService.getSlaResultsHtml(rrResultResponse);
        results = hipchatHtmlResultService.getSlaResultsHtml(rrResultResponse);

        System.out.println(results);


    }
/*
    @Test
    public void buildHtmlTableFromSlasHipChat() throws Exception {
        String json = new HttpService().sendPost("http://opp.roving.com/uxsvc/v2/rrux/importWpt", new HashMap<String, String>() {{
            put("wptId", "160104_5X_X");
            put("saveToGraphite", "false");
            put("slas", "{ \"median\": {       \"firstView\": {       \"loadTime\": 300, \"TTFB\": 300,\n" +
                    "         \"SpeedIndex\": 3000 } } }");
        }}).getResp();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        RrResultResponse rrResultResponse = gson.fromJson(json, RrResultResponse.class);
        assertTrue(rrResultResponse.getSlaResult().getTotalSlas() == 3);
        assertTrue(((Double) (rrResultResponse.getTest().get("successfulFVRuns"))).intValue() == 0);

        // build slas here


        String response = "{\n" +
                "\"statusCode\": 200,\n" +
                "\"statusText\": \"Ok\",\n" +
                "\"data\": {\n" +
                "\"testId\": \"160121_XV_17\",\n" +
                "\"ownerKey\": \"8c01305f6579bebf0b2eb9e04cb8c4541a73583e\",\n" +
                "\"jsonUrl\": \"http://wpt.roving.com/jsonResult.php?test=160121_XV_17\",\n" +
                "\"xmlUrl\": \"http://wpt.roving.com/xmlResult/160121_XV_17/\",\n" +
                "\"userUrl\": \"http://wpt.roving.com/result/160121_XV_17/\",\n" +
                "\"summaryCSV\": \"http://wpt.roving.com/result/160121_XV_17/page_data.csv\",\n" +
                "\"detailCSV\": \"http://wpt.roving.com/result/160121_XV_17/requests.csv\"\n" +
                "}\n" +
                "}";

        HipchatHtmlResultService hipchatHtmlResultService = new HipchatHtmlResultService();
        String runDetails = hipchatHtmlResultService.getWptResultSummaryHtml("160104_5X_X", "http:\\foo", new JsonParser().parse(response).getAsJsonObject());
        // System.out.println(wptHtmlRunDetails);

        runDetails += hipchatHtmlResultService.getSlaResultsHtml(rrResultResponse);
        System.out.println(runDetails);

    }
*/

    @Test
    @Ignore
    public void handleBadTestResult() {
        // set post params
        HashMap<String, String> params = new HashMap<>();
        params.put("wptId", "160303_K2_H"); // this test returns null data
        params.put("slas", "{\"median\":{\"firstView\":{\"loadTime\":3000,\"TTFB\":200}}}");
        params.put("sendToGraphite", "false");
        HttpService httpService = new HttpService();
        HttpResp testDoneResp =  httpService.sendPost("http://opp.roving.com/uxsvc/v2/rrux/importWpt", params);
        RrResultResponse rrResultResponse = new RrResultResponse();
        if(testDoneResp.isSuccess()) {
            System.out.println("********* Raw response from opp *********");
            System.out.println(testDoneResp.getResp());
            try {
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
                rrResultResponse = gson.fromJson(testDoneResp.getResp(), RrResultResponse.class);

            } catch (Exception ex2){
                System.out.println("Failed to parse response from opp: <br /><br />" + testDoneResp.getResp());
            }
        } else {
            System.out.println("Failed to save results to opp. Error message: " + testDoneResp.getErrorMessage());
        }
        Assert.that(!rrResultResponse.getSlaResult().getSlaDetails().get(0).getSlaPass(), "SLAs passed");


    }

    @Test
    public void labelValidation() throws Exception {
        WptService wptService = new WptService();
        // invalid environment
        assertTrue(!wptService.labelValidation("l2.contacts-ui.page2.uu-east.ie10.native.my-test-description-rules").equals(""));
        // uses underscores
        assertTrue(!wptService.labelValidation("l1.contacts-ui.page_2.uu-east.ie10.native.my-test-description-rules").equals(""));
        // has capital letter
        assertTrue(!wptService.labelValidation("l1.contacts-ui.PagG2.uu-east.ie10.native.my-test-description-rules").equals(""));
        // browser has a dash
        assertTrue(!wptService.labelValidation("l1.contacts-ui.page2.uu-east.ie-10.native.my-test-description-rules").equals(""));
        // not enough fields
        assertTrue(!wptService.labelValidation("l1.contacts-ui.page2.uu-east.ie-10").equals(""));
        // invalid application name
        assertTrue(!wptService.labelValidation("l1.invalid-app-name.page2.uu-east.ie10.native.my-test-description-rules").equals(""));
        // should pass
        assertTrue(wptService.labelValidation("l1.contacts-ui.page2.uu-east.ie10.native.my-test-description-rules").equals(""));
        // should pass no description
        assertTrue(wptService.labelValidation("l1.contacts-ui.page2.uu-east.ie10.native").equals(""));
    }
}