package com.capstone.notekeeper.Models;

public class NotesDetails {
    private String author,type,description,title;

    public NotesDetails(String author, String type, String description, String title) {
        this.author = author;
        this.type = type;
        this.description = description;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
