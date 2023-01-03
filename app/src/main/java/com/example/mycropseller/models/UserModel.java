package com.example.mycropseller.models;

public class UserModel {
    String name;
    String password;
    String email;
    String profileImg;
    int type;

    public UserModel() {

    }

    public UserModel(String name, String password, String email, int type) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
