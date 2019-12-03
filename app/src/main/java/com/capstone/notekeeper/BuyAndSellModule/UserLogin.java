package com.capstone.notekeeper.BuyAndSellModule;

public class UserLogin {
    private String mFullName;
    private String mEmail;
    private String mPassword;

    public UserLogin(String fullName, String email, String password) {
        setFullName(fullName);
        setEmail(email);
        setPassword(password);
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
