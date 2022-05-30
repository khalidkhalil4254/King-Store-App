package com.example.assignment2;

import android.graphics.Bitmap;

public class productDataModel {
    private String cate;
    private String title;
    private String price;
    private String game_info;
    private String description;
    private Bitmap img;
    private int rate;

    public productDataModel(String cate, String title, String price, String game_info, String description, Bitmap img, int rate) {
        this.cate = cate;
        this.title = title;
        this.price = price;
        this.game_info = game_info;
        this.description = description;
        this.img = img;
        this.rate = rate;
    }

    productDataModel(){}


    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGame_info() {
        return game_info;
    }

    public void setGame_info(String game_info) {
        this.game_info = game_info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}
