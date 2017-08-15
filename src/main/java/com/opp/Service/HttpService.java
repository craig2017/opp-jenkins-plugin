package com.opp.service;

import com.opp.domain.HttpResp;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ctobe on 1/7/16.
 */
@Service
public class HttpService {
    /**
     * Send a GET request
     * @param url
     * @return HttpResp
     * @throws IOException
     */
    public HttpResp sendGet(String url) {
        HttpResp httpResp = new HttpResp();
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            httpResp = handleHttpResponse(response, httpResp);
        } catch (Exception ex){
            httpResp.setSuccess(false);
            httpResp.setErrorMessage("Caught and exception: " + ex.getMessage());
        }
        return httpResp;
    }

    /**
     * Send a POST request
     * @param url
     * @param postParams
     * @return HttpResp
     */
    public HttpResp sendPost(String url, HashMap<String, String> postParams){
        HttpResp httpResp = new HttpResp();
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            RequestBuilder requestBuilder = RequestBuilder.post().setUri(new URI(url));
            for (Map.Entry<String, String> entry : postParams.entrySet()) {
                requestBuilder.addParameter(entry.getKey(), entry.getValue());
            }
            CloseableHttpResponse response = httpclient.execute(requestBuilder.build());
            httpResp = handleHttpResponse(response, httpResp);
        } catch (Exception ex){
            httpResp.setSuccess(false);
            httpResp.setErrorMessage("Caught and exception: " + ex.getMessage());
        }
        return httpResp;
    }

    /**
     * Handle the CloseableHttpResponse and set the custom response object HttpResp
     * @param response
     * @param httpResp
     * @return HttpResp
     */
    private HttpResp handleHttpResponse(CloseableHttpResponse response, HttpResp httpResp) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            httpResp.setResp(entity != null ? EntityUtils.toString(entity) : "");
        } else {
            httpResp.setSuccess(false);
            httpResp.setErrorMessage("Unexpected response status: " + status);
        }
        return httpResp;
    }
}
