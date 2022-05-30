package com.example.assignment2;

public class menuItemModel {
    private int itemIcon;
    private String itemText;

    public menuItemModel(int itemIcon, String itemText) {
        this.itemIcon = itemIcon;
        this.itemText = itemText;
    }

    public menuItemModel(){}

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}
