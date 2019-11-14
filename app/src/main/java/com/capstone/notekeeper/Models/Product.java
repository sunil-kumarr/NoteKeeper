package com.capstone.notekeeper.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {
    private String userEmail,userName,userPhone,userImageUrl,userId;
    private String productName,productDesc,productPrice,productImageUrl;
    private String productUploadId;
    private long postTimeStamp;

    public Product() {
    }

    public Product(String userEmail, String userName, String userPhone, String userImageUrl, String userId, String productName, String productDesc, String productPrice, String productImageUrl, String productUploadId, long postTimeStamp) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userImageUrl = userImageUrl;
        this.userId = userId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productImageUrl = productImageUrl;
        this.productUploadId = productUploadId;
        this.postTimeStamp = postTimeStamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductUploadId() {
        return productUploadId;
    }

    public void setProductUploadId(String productUploadId) {
        this.productUploadId = productUploadId;
    }

    public long getPostTimeStamp() {
        return postTimeStamp;
    }

    public void setPostTimeStamp(long postTimeStamp) {
        this.postTimeStamp = postTimeStamp;
    }
}