package com.groupwork.bean;

/**
 * Created by admin on 2017/4/2.
 */

public class Restaurants {
    private int resId;
    private String resName;
    private int resTypeId;
    private String resLocation;
    private int resNumber;

    public Restaurants(int resId, String resName, int resTypeId, String resLocation, int resNumber) {

        this.resId = resId;
        this.resName = resName;
        this.resTypeId = resTypeId;
        this.resLocation = resLocation;
        this.resNumber = resNumber;
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

    public int getResTypeId() {
        return resTypeId;
    }

    public void setResTypeId(int resTypeId) {
        this.resTypeId = resTypeId;
    }

    public String getResLocation() {
        return resLocation;
    }

    public void setResLocation(String resLocation) {
        this.resLocation = resLocation;
    }

    public int getResNumber() {
        return resNumber;
    }

    public void setResNumber(int resNumber) {
        this.resNumber = resNumber;
    }

    @Override
    public String toString() {
        return "Restaurants{" +
                "resId=" + resId +
                ", resName='" + resName + '\'' +
                ", resTypeId=" + resTypeId +
                ", resLocation='" + resLocation + '\'' +
                ", resNumber=" + resNumber +
                '}';
    }
}
