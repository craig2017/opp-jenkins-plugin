package com.opp.domain.ux;

import java.util.List;

/**
 * Created by ctobe on 1/4/16.
 */
public class SlaResult {
    private int totalSlas;
    private int totalPass;
    private int totalFailed;
    private float totalPassPct;
    private List<SlaResultDetails> slaDetails;

    public int getTotalSlas() {
        return totalSlas;
    }

    public void setTotalSlas(int totalSlas) {
        this.totalSlas = totalSlas;
    }

    public int getTotalPass() {
        return totalPass;
    }

    public void setTotalPass(int totalPass) {
        this.totalPass = totalPass;
    }

    public int getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(int totalFailed) {
        this.totalFailed = totalFailed;
    }

    public float getTotalPassPct() {
        return totalPassPct;
    }

    public void setTotalPassPct(float totalPassPct) {
        this.totalPassPct = totalPassPct;
    }

    public List<SlaResultDetails> getSlaDetails() {
        return slaDetails;
    }

    public void setSlaDetails(List<SlaResultDetails> slaDetails) {
        this.slaDetails = slaDetails;
    }
}
