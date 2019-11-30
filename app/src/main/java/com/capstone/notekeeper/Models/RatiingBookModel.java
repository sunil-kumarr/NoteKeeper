package com.capstone.notekeeper.Models;

public class RatiingBookModel {
    private String userID,reviewComment;
    private long timestamp,rated;

    public RatiingBookModel() {
    }

    public RatiingBookModel(String pUserID, String pReviewComment, long pTimestamp, long pRated) {
        userID = pUserID;
        reviewComment = pReviewComment;
        timestamp = pTimestamp;
        rated = pRated;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String pUserID) {
        userID = pUserID;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String pReviewComment) {
        reviewComment = pReviewComment;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long pTimestamp) {
        timestamp = pTimestamp;
    }

    public long getRated() {
        return rated;
    }

    public void setRated(long pRated) {
        rated = pRated;
    }
}
