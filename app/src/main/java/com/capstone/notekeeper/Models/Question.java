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
    private int mUserImageResId = NO_IMAGE;

    // Creates a no-argument Question object
    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(Question.class)
    }

    // Creates a new Question object
    public Question(String questionString, int numberOfAnswers) {
        setQuestionString(questionString);
        setNumberOfAnswers(numberOfAnswers);
    }

    // Creates a new Question object
    public Question(String key, String questionString, long timeStamp, int numberOfAnswers) {
        setQuestionId(key);;
        setQuestionString(questionString);
        setTimeStamp(timeStamp);
        setNumberOfAnswers(numberOfAnswers);
    }

    // Creates a new Question object
    public Question(String key, String questionString, long timeStamp, int numberOfAnswers,
                    String userId) {
        setQuestionId(key);;
        setQuestionString(questionString);
        setTimeStamp(timeStamp);
        setNumberOfAnswers(numberOfAnswers);
        setUserId(userId);
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

    public int getUserImageResId() {
        return mUserImageResId;
    }

    public void setUserImageResId(int userImageResId) {
        mUserImageResId = userImageResId;
    }
}
