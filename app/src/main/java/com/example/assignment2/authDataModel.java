package com.example.assignment2;

public class authDataModel {
    private String username;
    private String password;
    boolean isActive;

    public authDataModel(String username, String password,boolean isActive) {
        this.username = username;
        this.password = password;
        this.isActive=isActive;
    }
    authDataModel(){}
    public String getEmail() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setEmail(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
}
