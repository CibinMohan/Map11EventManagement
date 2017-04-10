package com.example.c4cib.map1111.ui;

/**
 * Created by c4cib on 15/11/2016.
 */

public class userDTO {
    private String userName=null;
    private String password=null;
    private String recPassword = null;
    private String email = null;
    int id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecPassword() {
        return recPassword;
    }

    public void setRecPassword(String recpassword) {
        this.recPassword = recpassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
