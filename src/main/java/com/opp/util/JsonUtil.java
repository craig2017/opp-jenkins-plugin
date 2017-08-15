package com.opp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

/**
 * Created by ctobe on 1/7/16.
 */
public class JsonUtil {
    /**
     * Pretty print json
     * @param json
     * @param convertToHtml
     * @return String - pretty printed json
     */
    public static String prettyPrint(String json, boolean convertToHtml){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(new JsonParser().parse(json));
        if(convertToHtml) {
            prettyJson = prettyJson.replace(" ", "&nbsp;").replace("\n", "<br />");
        }
        return prettyJson;
    }
}
