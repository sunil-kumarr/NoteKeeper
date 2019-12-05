package com.capstone.notekeeper.BuyAndSellModule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductModel {
    private String userEmail,userName,userPhone,userImageUrl,userId;
    private String productName,productDesc,productPrice,productImageUrl,productLocation;
    private String productID;
    private long postTimeStamp;

    public ProductModel() {
    }

    public ProductModel(String pUserEmail, String pUserName, String pUserPhone, String pUserImageUrl, String pUserId, String pProductName, String pProductDesc, String pProductPrice, String pProductImageUrl, String pProductLocation, String pProductID, long pPostTimeStamp) {
        userEmail = pUserEmail;
        userName = pUserName;
        userPhone = pUserPhone;
        userImageUrl = pUserImageUrl;
        userId = pUserId;
        productName = pProductName;
        productDesc = pProductDesc;
        productPrice = pProductPrice;
        productImageUrl = pProductImageUrl;
        productLocation = pProductLocation;
        productID = pProductID;
        postTimeStamp = pPostTimeStamp;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String pProductLocation) {
        productLocation = pProductLocation;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String pProductID) {
        productID = pProductID;
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

    public long getPostTimeStamp() {
        return postTimeStamp;
    }

    public void setPostTimeStamp(long postTimeStamp) {
        this.postTimeStamp = postTimeStamp;
    }
}