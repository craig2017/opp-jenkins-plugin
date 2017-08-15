package com.opp.domain.ux;


/**
 * Created by ctobe on 1/7/16.
 */
public class SlaResultDetails {
    private String name;
    private Integer sla;
    private Integer actual;
    private Integer diff;
    private boolean slaPass;
    private Double diffPct;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSla() {
        return sla;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
    }

    public Integer getDiff() {
        return diff;
    }

    public void setDiff(Integer diff) {
        this.diff = diff;
    }

    public boolean getSlaPass() {
        return slaPass;
    }

    public void setSlaPass(boolean slaPass) {
        this.slaPass = slaPass;
    }

    public Double getDiffPct() {
        return diffPct;
    }

    public void setDiffPct(Double diffPct) {
        this.diffPct = diffPct;
    }
}
