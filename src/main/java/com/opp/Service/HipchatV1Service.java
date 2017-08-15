package com.opp.service;

import com.opp.domain.HttpResp;

import java.util.HashMap;

/**
 * Created by msavoie on 1/6/16.
 */
public class HipchatV1Service {
    private final String server = "api.hipchat.com";
    private final String token = "token-here";
    private final String sendAs = "perfjenkins";
    private final HttpService httpService;


    public HipchatV1Service() {
        this.httpService = new HttpService();
    }

    /**
     * Sends a message to hipchat
     * @param roomIds
     * @param message
     * @param color
     * @return
     */
    public HashMap<String, String> sendMessage(String roomIds, String message, String color) {
        HashMap<String, String> publishResults = new HashMap<>();
        if (roomIds.trim().equals("")) {
            return publishResults;
        }
        String[] roomIdsArray = roomIds.split("\\s*,\\s*");
        for (String roomId : roomIdsArray) {
            String url = "https://" + server + "/v1/rooms/message";
            HashMap<String, String> postParams = new HashMap<>();
            postParams.put("auth_token", token);
            postParams.put("from", sendAs);
            postParams.put("room_id", roomId);
            postParams.put("message", message);
            postParams.put("color", color);
            postParams.put("notify", "1");
            HttpResp httpResp = httpService.sendPost(url, postParams);
            String result = (httpResp.isSuccess()) ? "Success" : "Failed: " + httpResp.getErrorMessage();
            publishResults.put(roomId, result);
        }
        return publishResults;
    }


    public String getServer() {
        return server;
    }

    public String getToken() {
        return token;
    }

    public String getSendAs() {
        return sendAs;
    }
}
