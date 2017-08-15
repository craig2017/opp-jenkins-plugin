package com.opp.domain.ux;

import java.util.Map;

/**
 * Created by ctobe on 1/4/16.
 */
public class RrResultResponse {

    private SlaResult slaResult;
    private Map<String, Object> slas;
    private Map<String, Object> test;

    public SlaResult getSlaResult() {
        return slaResult;
    }

    public void setSlaResult(SlaResult slaResult) {
        this.slaResult = slaResult;
    }

    public Map<String, Object> getSlas() {
        return slas;
    }

    public void setSlas(Map<String, Object> slas) {
        this.slas = slas;
    }

    public Map<String, Object> getTest() {
        return test;
    }

    public void setTest(Map<String, Object> test) {
        this.test = test;
    }
}
