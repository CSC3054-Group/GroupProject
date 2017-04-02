package com.groupwork.bean;

/**
 * Created by admin on 2017/4/2.
 */

public class ResType {
    private int resTypeId;
    private String resType;

    public ResType(int resTypeId, String resType) {
        this.resTypeId = resTypeId;
        this.resType = resType;
    }

    @Override
    public String toString() {
        return "ResType{" +
                "resTypeId=" + resTypeId +
                ", resType='" + resType + '\'' +
                '}';
    }

    public int getResTypeId() {
        return resTypeId;
    }

    public void setResTypeId(int resTypeId) {
        this.resTypeId = resTypeId;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }
}
