package com.vincentlaf.story.bean;

/**
 * Created by Johnson on 2018/1/1.
 */

public class User {
    private int userId;
    private String userPhone;
    private String userName;
    private String userPass;

    public User() {
    }

    public User(int userId, String userPhone, String userName, String userPass) {
        this.userId = userId;
        this.userPhone = userPhone;
        this.userName = userName;
        this.userPass = userPass;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
