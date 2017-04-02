package com.groupwork.bean;

/**
 * Created by admin on 2017/4/2.
 */

public class ResDetails {
    private int resDld;
    private int restId;
    private String resDtext;
    private String resDTimes;
    private int resDhyg;

    public ResDetails(int resDld, int restId, String resDtext, String resDTimes, int resDhyg) {
        this.resDld = resDld;
        this.restId = restId;
        this.resDtext = resDtext;
        this.resDTimes = resDTimes;
        this.resDhyg = resDhyg;
    }

    @Override
    public String toString() {
        return "ResDetails{" +
                "resDld=" + resDld +
                ", restId=" + restId +
                ", resDtext='" + resDtext + '\'' +
                ", resDTimes='" + resDTimes + '\'' +
                ", resDhyg=" + resDhyg +
                '}';
    }

    public int getResDld() {
        return resDld;
    }

    public void setResDld(int resDld) {
        this.resDld = resDld;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public String getResDtext() {
        return resDtext;
    }

    public void setResDtext(String resDtext) {
        this.resDtext = resDtext;
    }

    public String getResDTimes() {
        return resDTimes;
    }

    public void setResDTimes(String resDTimes) {
        this.resDTimes = resDTimes;
    }

    public int getResDhyg() {
        return resDhyg;
    }

    public void setResDhyg(int resDhyg) {
        this.resDhyg = resDhyg;
    }
}
