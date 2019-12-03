package com.capstone.notekeeper.StudyLibararyModule;

public class RatiingBookModel {
    private String userID,userImage,userName,reviewComment;
    private long timestamp;
    private float givenRating;

    public RatiingBookModel() {
    }

    public RatiingBookModel(String pUserID, String pUserImage, String pUserName, String pReviewComment, long pTimestamp, float pGivenRating) {
        userID = pUserID;
        userImage = pUserImage;
        userName = pUserName;
        reviewComment = pReviewComment;
        timestamp = pTimestamp;
        givenRating = pGivenRating;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String pUserID) {
        userID = pUserID;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String pUserImage) {
        userImage = pUserImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String pUserName) {
        userName = pUserName;
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

    public float getGivenRating() {
        return givenRating;
    }

    public void setGivenRating(float pGivenRating) {
        givenRating = pGivenRating;
    }
}
