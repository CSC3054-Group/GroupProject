package com.groupwork.bean;

/**
 * Created by admin on 2017/4/2.
 */

public class SearchItem {
    private int resId;
    private String resName;
    private String resLocation;

    public SearchItem(int resId, String resName, String resLocation) {

        this.resId = resId;
        this.resName = resName;
        this.resLocation = resLocation;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResLocation() {
        return resLocation;
    }

    public void setResLocation(String resLocation) {
        this.resLocation = resLocation;
    }

    /*
    @Override
    public String toString() {
        return "SearchItem{" +
                "resId=" + resId +
                ", resName='" + resName + '\'' +
                ", resLocation='" + resLocation + '\'' +
                '}';
    }
    */
}
