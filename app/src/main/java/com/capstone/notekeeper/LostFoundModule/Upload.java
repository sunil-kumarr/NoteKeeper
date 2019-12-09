package com.capstone.notekeeper.LostFoundModule;


import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mNumber;
    private String mImageUrl;
    private String mKey;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String number, String name, String imageUrl) {

        if (number.trim().equals("")) {
            number = "No Number";
        }
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mNumber = number;
        mName = name;
        mImageUrl = imageUrl;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}