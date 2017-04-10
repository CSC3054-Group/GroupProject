package com.groupwork.bean;

/**
 * Created by admin on 2017/4/2.
 */

public class NearbyItem {
    private int resId;
    private String resLoaction;
    private String res_img;
    private String resName;
    private float rating;
    private String resType;
    private String duration_text;
    private double duration;

    public NearbyItem(int resId, String resLoaction, String res_img, String resName, String resType, float rating, String duration_text, double duration) {
        this.resId = resId;
        this.resLoaction = resLoaction;
        this.res_img = res_img;
        this.resName = resName;
        this.resType = resType;
        this.rating = rating;
        this.duration_text = duration_text;
        this.duration = duration;
    }

    public String getDuration_text() {
        return duration_text;
    }

    public void setDuration_text(String duration_text) {
        this.duration_text = duration_text;
    }

    @Override
    public String toString() {
        return "NearbyItem{" +
                "resId=" + resId +
                ", resLoaction='" + resLoaction + '\'' +
                ", res_img='" + res_img + '\'' +
                ", resName='" + resName + '\'' +
                ", rating=" + rating +
                ", resType='" + resType + '\'' +
                ", duration_text='" + duration_text + '\'' +
                ", duration=" + duration +
                '}';
    }

    public String getResLoaction() {
        return resLoaction;
    }

    public void setResLoaction(String resLoaction) {
        this.resLoaction = resLoaction;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getRes_img() {
        return res_img;
    }

    public void setRes_img(String res_img) {
        this.res_img = res_img;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }



}
