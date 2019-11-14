package com.capstone.notekeeper.Models;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private static final int NO_ANSWERS = 0;
    private static final int NO_IMAGE = -1;

    private String mQuestionId;
    private String mQuestionString;
    private long mTimeStamp;
    private List<Answer> mAnswers = new ArrayList<>();
    private int mNumberOfAnswers = NO_ANSWERS;
    private String mUserId;
    private String mUserImageUrl;
    private String mUserName;

    // Creates a no-argument Question object
    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(Question.class)
    }

    public Question(String mQuestionId, String mQuestionString, long mTimeStamp, int mNumberOfAnswers, String mUserId, String mUserImageUrl, String mUserName) {
        this.mQuestionId = mQuestionId;
        this.mQuestionString = mQuestionString;
        this.mTimeStamp = mTimeStamp;
        this.mNumberOfAnswers = mNumberOfAnswers;
        this.mUserId = mUserId;
        this.mUserImageUrl = mUserImageUrl;
        this.mUserName = mUserName;
    }

    public String getmQuestionId() {
        return mQuestionId;
    }

    public void setmQuestionId(String mQuestionId) {
        this.mQuestionId = mQuestionId;
    }

    public String getmQuestionString() {
        return mQuestionString;
    }

    public void setmQuestionString(String mQuestionString) {
        this.mQuestionString = mQuestionString;
    }

    public long getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(long mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public List<Answer> getmAnswers() {
        return mAnswers;
    }

    public void setmAnswers(List<Answer> mAnswers) {
        this.mAnswers = mAnswers;
    }

    public int getmNumberOfAnswers() {
        return mNumberOfAnswers;
    }

    public void setmNumberOfAnswers(int mNumberOfAnswers) {
        this.mNumberOfAnswers = mNumberOfAnswers;
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

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(String questionId) {
        mQuestionId = questionId;
    }

    public String getQuestionString() {
        return mQuestionString;
    }

    public void setQuestionString(String questionString) {
        mQuestionString = questionString;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    public List<Answer> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(List<Answer> answers) {
        mAnswers = answers;
    }

    public int getNumberOfAnswers() {
        return mNumberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        mNumberOfAnswers = numberOfAnswers;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
