package com.capstone.notekeeper.Models;

public class NotesDetails {
    private String author,type,description,title,fileLink;

    public NotesDetails(String author, String type, String description, String title,String fileLink) {
        this.author = author;
        this.type = type;
        this.description = description;
        this.title = title;
        this.fileLink = fileLink;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getFileLink() {
        return fileLink;
    }
}
