package com.groupwork.bean;

/**
 * Created by admin on 2017/4/2.
 */

public class NearbyItem implements Comparable<NearbyItem>{
    private int resId;
    private String resLoaction;
    private String res_img;
    private String resName;
    private int rating;
    private String resType;
    private float duration;

    public NearbyItem(int resId, String resLoaction, String res_img, String resName, int rating, String resType, float duration) {
        this.resId = resId;
        this.resLoaction = resLoaction;
        this.res_img = res_img;
        this.resName = resName;
        this.rating = rating;
        this.resType = resType;
        this.duration = duration;
    }

    public String getResLoaction() {
        return resLoaction;
    }

    public void setResLoaction(String resLoaction) {
        this.resLoaction = resLoaction;
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
                ", duration=" + duration +
                '}';
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(NearbyItem o) {

        return (int) (this.duration-o.getDuration());
    }
}
