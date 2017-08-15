package com.opp.domain;

/**
 * Created by ctobe on 4/27/16.
 */
public class Application {
    private int id;
    private String appKey;
    private String newrelic;
    private String appdynamics;
    private String webpagetest;
    private String splunk;
    private String dynatrace;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getNewrelic() {
        return newrelic;
    }

    public void setNewrelic(String newrelic) {
        this.newrelic = newrelic;
    }

    public String getAppdynamics() {
        return appdynamics;
    }

    public void setAppdynamics(String appdynamics) {
        this.appdynamics = appdynamics;
    }

    public String getWebpagetest() {
        return webpagetest;
    }

    public void setWebpagetest(String webpagetest) {
        this.webpagetest = webpagetest;
    }

    public String getSplunk() {
        return splunk;
    }

    public void setSplunk(String splunk) {
        this.splunk = splunk;
    }

    public String getDynatrace() {
        return dynatrace;
    }

    public void setDynatrace(String dynatrace) {
        this.dynatrace = dynatrace;
    }
}
