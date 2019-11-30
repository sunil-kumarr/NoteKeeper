package com.capstone.notekeeper.Models;

import java.io.Serializable;

public class NoteBookModel implements Serializable {
    private String bookUploadedBy,mUserID,bookDescription,bookTitle,bookLink;
    private long timsetamp,rating;

    public NoteBookModel() {
    }

    public NoteBookModel(String pBookUploadedBy,String pUserID, String pBookDescription, String pBookTitle, String pBookLink, long pTimsetamp, long pRating) {
        bookUploadedBy = pBookUploadedBy;
        mUserID = pUserID;
        bookDescription = pBookDescription;
        bookTitle = pBookTitle;
        bookLink = pBookLink;
        timsetamp = pTimsetamp;
        rating = pRating;
    }

    public String getBookUploadedBy() {
        return bookUploadedBy;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String pUserID) {
        mUserID = pUserID;
    }

    public void setBookUploadedBy(String pBookUploadedBy) {
        bookUploadedBy = pBookUploadedBy;
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

    public long getRating() {
        return rating;
    }

    public void setRating(long pRating) {
        rating = pRating;
    }
}
