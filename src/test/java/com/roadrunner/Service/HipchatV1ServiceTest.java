package com.opp.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by msavoie on 1/7/16.
 */
public class HipchatV1ServiceTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
/*
    @Test
    public void testSendMessage() throws Exception {
        HipchatV1Service hipChatService = new HipchatV1Service();
        HashMap<String, String> resultsTest = hipChatService.sendMessage("Moe Test", "test", "purple");
        assertTrue(resultsTest.get("Moe Test").equals("Success"));
    }
*/
    @Test
    public void testSendHTMLMessage() throws Exception {
        String hipchatRoomName = "Moe Test";
        String hipchatTestNameHtml = "<b>WPT Test </b>" + "label" + "&nbsp;";
        String hipchatDetails = "<b>WPT ID:</b>&nbsp;160104_5X_X<br>\n" +
                "<b>Results URL:</b>&nbsp;<a target='_blank' href='http://wpt.roving.com/results.php?test=160104_5X_X'>http://wpt.roving.com/results.php?test=160104_5X_X</a><br>\n";
        String slaHipchatHtml = "<div class=\"rrux-table sla-summary-table\">\n" +
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
                "            <td>3</td>\n" +
                "            <td class=\"passText\">2</td>\n" +
                "            <td class=\"failText\">1</td>\n" +
                "            <td class=\"failText\">66.67%</td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</div>________________________________________________________<br><br><div>\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"6\">SLA Failed Results </th>\n" +
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
                "        <tbody>\n" +
                "<tr><td>median.firstView.loadTime</td><td>100</td><td>171</td><td>71</td><td>41.52</td><td>Failed</td></tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>";


        //hipchatRoomName,"Test: " + label + "\n" + slaHipchatHtml
        String label = "MoeTest";
        String hipchatSep = "========================================================<br>";
        HipchatV1Service hipChatService = new HipchatV1Service();
        hipChatService.sendMessage(hipchatRoomName, hipchatTestNameHtml + "<b>COMPLETED</b><br>" + hipchatDetails + hipchatSep + slaHipchatHtml, "green");



        //hipChatService.sendMessage(hipchatRoomName, slaHipchatHtml, "green");
       // HashMap<String, String> resultsTest = hipChatService.sendMessage(hipchatRoomName, hipchatTestNameHtml + "<b>COMPLETED</b><br>" + hipchatDetails + slaHipchatHtml, "black");
        //        assertTrue(resultsTest.get("Moe Test").equals("Success"));
    }
}