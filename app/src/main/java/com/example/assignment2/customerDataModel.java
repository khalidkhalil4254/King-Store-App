package com.example.assignment2;

public class customerDataModel {
    public String name;
    public String phone;
    public String email;
    public String password;
    public boolean isActive;

    customerDataModel(String name, String phone, String email, String password,boolean isActive) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.isActive=isActive;
    }

    @Override
    public String toString() {
        return "customerDataModel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean isActive() {
        return isActive;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
