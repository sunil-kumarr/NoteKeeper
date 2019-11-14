package com.capstone.notekeeper.Models;

public class Answer {
    private String mAnswerId;
    private String mAnswerString;
    private String mQuestionId;
    private long mTimeStamp;
    private String mUserId;
    private String mUserImageUrl;
    private String mUserName;

    public Answer() {
    }

    public Answer(String mAnswerId, String mAnswerString, long mTimeStamp, String mUserId, String mUserImageUrl, String mUserName) {
        this.mAnswerId = mAnswerId;
        this.mAnswerString = mAnswerString;
        this.mTimeStamp = mTimeStamp;
        this.mUserId = mUserId;
        this.mUserImageUrl = mUserImageUrl;
        this.mUserName = mUserName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmAnswerId() {
        return mAnswerId;
    }

    public void setmAnswerId(String mAnswerId) {
        this.mAnswerId = mAnswerId;
    }

    public String getmAnswerString() {
        return mAnswerString;
    }

    public void setmAnswerString(String mAnswerString) {
        this.mAnswerString = mAnswerString;
    }

    public String getmQuestionId() {
        return mQuestionId;
    }

    public void setmQuestionId(String mQuestionId) {
        this.mQuestionId = mQuestionId;
    }

    public long getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(long mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmUserImageUrl() {
        return mUserImageUrl;
    }

    public void setmUserImageUrl(String mUserImageUrl) {
        this.mUserImageUrl = mUserImageUrl;
    }
}
