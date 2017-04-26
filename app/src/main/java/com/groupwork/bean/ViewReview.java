package com.groupwork.bean;

/**
 * Created by admin on 2017/4/26.
 */

public class ViewReview {
    private float revStarRating;
    private String revText;
    private String Forename;
    private String Surname;

    public ViewReview(String revText, float revStarRating, String forename, String surname) {
        this.revText = revText;
        this.revStarRating = revStarRating;
        Forename = forename;
        Surname = surname;
    }

    public float getRevStarRating() {
        return revStarRating;
    }

    public void setRevStarRating(float revStarRating) {
        this.revStarRating = revStarRating;
    }

    public String getRevText() {
        return revText;
    }

    public void setRevText(String revText) {
        this.revText = revText;
    }

    public String getForename() {
        return Forename;
    }

    public void setForename(String forename) {
        Forename = forename;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }
}
