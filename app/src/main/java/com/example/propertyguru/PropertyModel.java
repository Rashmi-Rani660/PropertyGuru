package com.example.propertyguru;

public class PropertyModel {
    private String title;
    private int price;
    private String location;
    private String imageUrl;
    private String postedBy;

    // Required no-arg constructor
    public PropertyModel() {}

    public PropertyModel(String title, int price, String location, String imageUrl, String postedBy) {
        this.title = title;
        this.price = price;
        this.location = location;
        this.imageUrl = imageUrl;
        this.postedBy = postedBy;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getPostedBy() { return postedBy; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }
}
