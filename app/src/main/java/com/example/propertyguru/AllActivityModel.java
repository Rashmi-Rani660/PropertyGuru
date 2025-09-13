package com.example.propertyguru;

public class AllActivityModel {
    private String title, location, type, price, developer;
    private int imageResId;

    public AllActivityModel(String title, String location, String type, String price, String developer, int imageResId) {
        this.title = title;
        this.location = location;
        this.type = type;
        this.price = price;
        this.developer = developer;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getType() { return type; }
    public String getPrice() { return price; }
    public String getDeveloper() { return developer; }
    public int getImageResId() { return imageResId; }
}
