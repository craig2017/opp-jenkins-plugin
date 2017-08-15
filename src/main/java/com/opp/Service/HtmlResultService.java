package com.opp.service;

import com.google.gson.JsonObject;
import com.opp.domain.ux.RrResultResponse;
import com.opp.domain.ux.SlaResult;
import com.opp.domain.ux.SlaResultDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by ctobe on 1/25/16.
 */
public class HtmlResultService {
    /**
     * Gets the CSS for the jenkins results
     *
     * @return
     */


    public String getResultsCss() {
        return "<style>\n" +
                "    .rrux-table table { border-collapse: collapse; text-align: left;  }\n" +
                "    .rrux-table { margin: 20px 0; font: normal 12px/150% Arial, Helvetica, sans-serif; background: #fff; overflow: hidden; border: 2px solid #8C8C8C; }\n" +
                "    .rrux-table table td, .rrux-table table th { padding: 7px 10px; }\n" +
                "    .rrux-table table thead th {background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #8C8C8C), color-stop(1, #7D7D7D) );background:-moz-linear-gradient( center top, #8C8C8C 5%, #7D7D7D 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#8C8C8C', endColorstr='#7D7D7D');background-color:#8C8C8C; color:#FFFFFF; font-size: 15px; font-weight: bold; border-left: 1px solid #A3A3A3; }\n" +
                "    .rrux-table table thead th:first-child { border: none; }\n" +
                "    .rrux-table table tbody td { color: #7D7D7D; border-left: 1px solid #DBDBDB;font-size: 14px;font-weight: normal; }\n" +
                "    .rrux-table table tbody td:first-child { border-left: none; }\n" +
                "    .rrux-table table tbody tr:last-child td { border-bottom: none; }\n" +
                "    .rrux-table .passText { color: #006622; }\n" +
                "    .rrux-table .failText { color: #990000; }\n" +
                "    .rrux-table .warningText { color: #e68a00; }\n" +
                "    .rrux-table .passBackground { background-color: #ccffdd; }\n" +
                "    .rrux-table .failBackground { background-color: #ff8080;  }\n" +
                "    .rrux-table .failBackground td { color:#ffffff }\n" +
                "    .rrux-table .warningBackground { background-color: #ffe0b3; }\n" +
                "    .sla-summary-table { width:400px; }\n" +
                "    .sla-summary-table table { width:400px; }\n" +
                "    .sla-details-table { width:80%; }\n" +
                "    .sla-details-table table { width:100%; }\n" +
                "    .wpt-summary-table th { min-width: 110px; }\n" +
                "</style>";
    }

    /**
     * Gets the SLA result html
     *
     * @param rrResultResponse
     * @return
     */
    public String getSlaResultsHtml(RrResultResponse rrResultResponse) {

        String slaDetailsTableHeader = "<div class=\"rrux-table sla-details-table\">\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"6\">SLA Detailed Results</th>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <th>SLA Name</th>\n" +
                "                <th>SLA (ms)</th>\n" +
                "                <th>Actual (ms)</th>\n" +
                "                <th>Diff</th>\n" +
                "                <th>Diff %</th>\n" +
                "                <th>Status</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n";

        // iterate over this body
        String slaDetailsTableBody = getSlaResultDetailsBody(rrResultResponse.getSlaResult().getSlaDetails());


        String slaDetailsTableFooter = "        </tbody>\n" +
                "    </table>\n" +
                "</div>";

        return new StringBuilder()
                .append(getSlaResultSummaryTable(rrResultResponse))
                .append(slaDetailsTableHeader).append(slaDetailsTableBody).append(slaDetailsTableFooter).toString();


    }


    /**
     * Gets the SLA result summary table
     *
     * @param rrResultResponse
     * @return
     */
    public String getSlaResultSummaryTable(RrResultResponse rrResultResponse) {
        SlaResult slaResult = rrResultResponse.getSlaResult();
        String totalPassPctTextClass;
        if (slaResult.getTotalPassPct() < 100) {
            totalPassPctTextClass = (slaResult.getTotalPassPct() < 80) ? "failText" : "warningText";
        } else {
            totalPassPctTextClass = "passText";
        }

        return "<div class=\"rrux-table sla-summary-table\">\n" +
                "<table>\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                "            <th colspan=\"4\">SLA Summary Results</th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Total</th>\n" +
                "            <th>Passed</th>\n" +
                "            <th>Failed</th>\n" +
                "            <th>Pass %</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td>" + String.valueOf(slaResult.getTotalSlas()) + "</td>\n" +
                "            <td class=\"passText\">" + String.valueOf(slaResult.getTotalPass()) + "</td>\n" +
                "            <td class=\"failText\">" + String.valueOf(slaResult.getTotalFailed()) + "</td>\n" +
                "            <td class=\"" + totalPassPctTextClass + "\">" + String.valueOf(slaResult.getTotalPassPct()) + "%</td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</div>";
    }



    /**
     * Gets the table rows for the SLA detailed results
     *
     * @param slaResultDetailsList
     * @return
     */
    String getSlaResultDetailsBody(List<SlaResultDetails> slaResultDetailsList) {
        StringBuilder slaDetailsTableBody = new StringBuilder();

        for (SlaResultDetails slaResultDetails : slaResultDetailsList) {

            String className;
            // determine row highlight color
            if (slaResultDetails.getSlaPass()) {
                className = "passBackground";
            } else {
                if (slaResultDetails.getDiffPct() > 10) {
                    className = "failBackground"; // worse than 10%
                } else {
                    className = "warningBackground"; // within 10% show warning
                }
            }

            slaDetailsTableBody.append("<tr class=\"" + className + "\">");
            slaDetailsTableBody.append("<td>" + slaResultDetails.getName() + "</td>");
            slaDetailsTableBody.append("<td>" + String.valueOf(slaResultDetails.getSla()) + "</td>");
            slaDetailsTableBody.append("<td>" + String.valueOf(slaResultDetails.getActual()) + "</td>");
            slaDetailsTableBody.append("<td>" + String.valueOf(slaResultDetails.getDiff()) + "</td>");
            slaDetailsTableBody.append("<td>" + String.valueOf(slaResultDetails.getDiffPct()) + "</td>");
            slaDetailsTableBody.append("<td>" + (slaResultDetails.getSlaPass() ? "Passed" : "Failed") + "</td>");
            slaDetailsTableBody.append("</tr>");

        }


        return slaDetailsTableBody.toString();
    }


    /**
     * Gets the WPT summary HTML table
     *
     * @param wptTestId
     * @param wptApiUrl
     * @param wptResponseJson
     * @return
     */
    public String getWptResultSummaryHtml(String wptTestId, String wptApiUrl, String jobLabel, JsonObject wptResponseJson) {

        String resultsUrl = String.format("<a target='_blank' href='http://wpt.roving.com/results.php?test=%s'>http://wpt.roving.com/results.php?test=%s</a>", wptTestId, wptTestId);
        String rrResUrlString = "http://opp.roving.com/ux/wptTrend?name=" + jobLabel + "&cached=false&run=median&utBaseline=false";
        String rrResUrl = "<a href='" + rrResUrlString + "'>" + rrResUrlString + "</a>";

        JsonObject wptRespData = wptResponseJson.getAsJsonObject("data");

        return getResultsCss() +
                "<div class=\"rrux-table wpt-summary-table\">\n" +
                "<table>\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                "            <th colspan=\"2\">WPT Run Details</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <th>WPT ID</th>\n" +
                "            <td>" + wptTestId + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Results Url</th>\n" +
                "            <td>" + resultsUrl + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Status</th>\n" +
                "            <td>" +  wptResponseJson.get("statusCode").getAsString() + " - " + wptResponseJson.get("statusText").getAsString() + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Json Url</th>\n" +
                "            <td>" + wptRespData.get("jsonUrl").getAsString() + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Xml Url</th>\n" +
                "            <td>" + wptRespData.get("xmlUrl").getAsString() + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Summary CSV</th>\n" +
                "            <td>" + wptRespData.get("summaryCSV").getAsString() + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Details CSV</th>\n" +
                "            <td>" + wptRespData.get("detailCSV").getAsString() + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>WPT API Call</th>\n" +
                "            <td>" + wptApiUrl + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>opp Results Url</th>\n" +
                "            <td>" + rrResUrl + "</td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</div>";
    }

}
