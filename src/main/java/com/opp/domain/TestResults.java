package com.opp.domain;

/**
 * Created by ctobe on 12/29/15.
 */
public class TestResults {
    private boolean buildSuccessful;
    private String summaryText;
    private String errorMessage;

    public TestResults() {
        this.buildSuccessful = true;
        this.summaryText = "";
        this.errorMessage = "";
    }

    public boolean isBuildSuccessful() {
        return buildSuccessful;
    }

    public void setBuildSuccessful(boolean buildSuccessful) {
        this.buildSuccessful = buildSuccessful;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
