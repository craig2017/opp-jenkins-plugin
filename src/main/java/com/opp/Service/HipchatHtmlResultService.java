package com.opp.service;

import com.opp.domain.ux.RrResultResponse;
import com.opp.domain.ux.SlaResultDetails;

import java.util.List;

/**
 * Created by ctobe on 1/25/16.
 */
public class HipchatHtmlResultService extends HtmlResultService {

    public final String SEP = "____________________________________________________________<br><br>";


    @Override
    public String getResultsCss() {
        return "";
    }

    @Override
    public String getSlaResultsHtml(RrResultResponse rrResultResponse) {
        String slaDetailsTableHeader =
                "<div>\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"6\">" + "SLA Failed Results" + " </th>\n" +
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


        String slaDetailsTableFooter =
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>\n";

        return new StringBuilder()
                .append(getSlaResultSummaryTable(rrResultResponse))
                .append(SEP)
                .append(slaDetailsTableHeader)
                .append(slaDetailsTableBody)
                .append(slaDetailsTableFooter)
                .toString();


    }


    /**
     * Set the pass, fail, warning text
     * @param slaResultDetails
     * @return
     */
    private String getSlaPassFailMessage(SlaResultDetails slaResultDetails){
        String message;
        if(slaResultDetails.getSlaPass()){
            message = "Passed";
        } else {
            if(slaResultDetails.getDiffPct() > 10 || slaResultDetails.getDiffPct() == null){
                message = "Failed";
            } else {
                message = "Warning (+"+slaResultDetails.getDiffPct().toString()+"%) over SLA";
            }
        }
        return message;
    }

    @Override
    String getSlaResultDetailsBody(List<SlaResultDetails> slaResultDetailsList) {
        StringBuilder slaDetailsTableBody = new StringBuilder();
        boolean slasPassed = true;
        for (SlaResultDetails slaResultDetails : slaResultDetailsList) {
            if (slaResultDetails.getDiffPct() > 0) {
                slasPassed = false;
                slaDetailsTableBody.append("<tr>");
                slaDetailsTableBody.append("<td>" + slaResultDetails.getName() + "</td>");
                slaDetailsTableBody.append("<td>" + String.valueOf(slaResultDetails.getSla()) + "</td>");
                slaDetailsTableBody.append("<td>" + String.valueOf(slaResultDetails.getActual()) + "</td>");
                slaDetailsTableBody.append("<td>" + String.valueOf(slaResultDetails.getDiff()) + "</td>");
                slaDetailsTableBody.append("<td>" + String.valueOf(slaResultDetails.getDiffPct()) + "</td>");
                slaDetailsTableBody.append("<td>" + getSlaPassFailMessage(slaResultDetails) + "</td>"); // set pass, warn, fail.  warn if over by less than 10%
                slaDetailsTableBody.append("</tr>\n");
            }
        }

        if(slasPassed){
            slaDetailsTableBody.append("<tr>");
            slaDetailsTableBody.append("<td>All SLAs Passed</td>");
            slaDetailsTableBody.append("</tr>\n");
        }

        return slaDetailsTableBody.toString();
    }



    public String getWptResultSummaryHtml(String wptTestId, String jobUrl, String jobLabel) {
        String resultsUrl = String.format("<a target='_blank' href='http://wpt.roving.com/results.php?test=%s'>http://wpt.roving.com/results.php?test=%s</a>", wptTestId, wptTestId);
        String jobResUrlString = "https://perfjenkins.roving.com/" + jobUrl;
        String jobResUrl = "<a href='" + jobResUrlString + "'>" + jobResUrlString + "</a>";
        String rrResUrlString = "http://opp.roving.com/ux/wptTrend?name=" + jobLabel + "&cached=false&run=median&utBaseline=false";
        String rrResUrl = "<a href='" + rrResUrlString + "'>" + rrResUrlString + "</a>";


        return getResultsCss() +
                "<b>WPT ID: </b> " + wptTestId + "<br>\n" +
                "<b>WPT Results: </b> " + resultsUrl +"<br>\n" +
                "<b>Jenkins Results: </b> " + jobResUrl +"<br>\n" +
                "<b>opp Results: </b> " + rrResUrl +"<br>\n";

    }


}
