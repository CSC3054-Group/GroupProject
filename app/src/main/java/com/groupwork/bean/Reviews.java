package com.groupwork.bean;

/**
 * Created by admin on 2017/4/2.
 */

public class Reviews {
    private int revId;
    private int revStarRating;
    private String revText;
    private int UserId;
    private int restId;

    public Reviews(int revId, int revStarRating, String revText, int userId, int restId) {
        this.revId = revId;
        this.revStarRating = revStarRating;
        this.revText = revText;
        UserId = userId;
        this.restId = restId;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "revId=" + revId +
                ", revStarRating=" + revStarRating +
                ", revText='" + revText + '\'' +
                ", UserId=" + UserId +
                ", restId=" + restId +
                '}';
    }

    public int getRevId() {
        return revId;
    }

    public void setRevId(int revId) {
        this.revId = revId;
    }

    public int getRevStarRating() {
        return revStarRating;
    }

    public void setRevStarRating(int revStarRating) {
        this.revStarRating = revStarRating;
    }

    public String getRevText() {
        return revText;
    }

    public void setRevText(String revText) {
        this.revText = revText;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }
}
