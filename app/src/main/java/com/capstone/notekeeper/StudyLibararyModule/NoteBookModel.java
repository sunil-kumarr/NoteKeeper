package com.capstone.notekeeper.StudyLibararyModule;

import java.io.Serializable;
import java.util.ArrayList;

public class NoteBookModel implements Serializable {
    private String mUserID,bookDescription,bookTitle,
            bookLink,authorImage,authorName,notebookID,bookCourse;
    private long timsetamp,totalRatings;
    float rating;
    public NoteBookModel() {
    }

    public NoteBookModel(String pUserID, String pBookDescription, String pBookTitle, String pBookLink, String pAuthorImage, String pAuthorName, String pNotebookID, String pBookCourse, long pTimsetamp, long pTotalRatings, float pRating) {
        mUserID = pUserID;
        bookDescription = pBookDescription;
        bookTitle = pBookTitle;
        bookLink = pBookLink;
        authorImage = pAuthorImage;
        authorName = pAuthorName;
        notebookID = pNotebookID;
        bookCourse = pBookCourse;
        timsetamp = pTimsetamp;
        totalRatings = pTotalRatings;
        rating = pRating;
    }

    public float getRating() {
        return rating;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long pTotalRatings) {
        totalRatings = pTotalRatings;
    }

    public void setRating(float pRating) {
        rating = pRating;
    }

    public String getNotebookID() {
        return notebookID;
    }

    public String getBookCourse() {
        return bookCourse;
    }

    public void setBookCourse(String pBookCourse) {
        bookCourse = pBookCourse;
    }

    public void setNotebookID(String pNotebookID) {
        notebookID = pNotebookID;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String pAuthorImage) {
        authorImage = pAuthorImage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String pAuthorName) {
        authorName = pAuthorName;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String pUserID) {
        mUserID = pUserID;
    }



    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String pBookDescription) {
        bookDescription = pBookDescription;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String pBookTitle) {
        bookTitle = pBookTitle;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String pBookLink) {
        bookLink = pBookLink;
    }

    public long getTimsetamp() {
        return timsetamp;
    }

    public void setTimsetamp(long pTimsetamp) {
        timsetamp = pTimsetamp;
    }

}
