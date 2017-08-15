package com.opp.domain;


/**
 * Created by ctobe on 12/28/15.
 */
public class HttpResp {

    private boolean success;
    private String errorMessage;
    private String resp;

    public HttpResp() {
        this.success = true;
        this.errorMessage = "";
        this.resp = "";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }
}
