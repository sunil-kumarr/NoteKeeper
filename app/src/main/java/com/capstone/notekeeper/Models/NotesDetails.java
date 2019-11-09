package com.capstone.notekeeper.Models;

public class NotesDetails {
    private String materialName,author,materialType,courseIcon;
    private Integer rating;

    public NotesDetails(String materialName, String author, String materialType, String courseIcon, Integer rating) {
        this.materialName = materialName;
        this.author = author;
        this.materialType = materialType;
        this.courseIcon = courseIcon;
        this.rating = rating;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getAuthor() {
        return author;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getCourseIcon() {
        return courseIcon;
    }

    public Integer getRating() {
        return rating;
    }
}
