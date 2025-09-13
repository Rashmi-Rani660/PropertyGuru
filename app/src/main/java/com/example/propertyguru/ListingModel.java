package com.example.propertyguru;

import java.util.Date;
import java.util.List;

public class ListingModel {

    private String propertyType;
    private String location;
    private String title;
    private String price;
    private String developerName;
    private String possessionDetails;
    private String projectName;
    private String projectLocation;
    private String builderName;
    private String builderType;

    private String bhk2Price;
    private String bhk3Price;

    // ✅ Updated: image URLs instead of drawable IDs
    private List<String> image;

    private String imageUrl;

    private int numericPrice;
    private double pricePerSqft;
    private int relevanceScore;
    private Date dateAdded;

    private String type;

    // ✅ Default constructor
    public ListingModel() {
    }

    // ✅ Full constructor (if needed)
    public ListingModel(String propertyType, String location, String title, String price,
                        String developerName, String possessionDetails,
                        String projectName, String projectLocation,
                        String builderName, String builderType,
                        String bhk2Price, String bhk3Price,
                        List<String> images) {
        this.propertyType = propertyType;
        this.location = location;
        this.title = title;
        this.price = price;
        this.developerName = developerName;
        this.possessionDetails = possessionDetails;
        this.projectName = projectName;
        this.projectLocation = projectLocation;
        this.builderName = builderName;
        this.builderType = builderType;
        this.bhk2Price = bhk2Price;
        this.bhk3Price = bhk3Price;
        this.image = image;
    }

    // 🔽 Getters and Setters

    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getDeveloperName() { return developerName; }
    public void setDeveloperName(String developerName) { this.developerName = developerName; }

    public String getPossessionDetails() { return possessionDetails; }
    public void setPossessionDetails(String possessionDetails) { this.possessionDetails = possessionDetails; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectLocation() { return projectLocation; }
    public void setProjectLocation(String projectLocation) { this.projectLocation = projectLocation; }

    public String getBuilderName() { return builderName; }
    public void setBuilderName(String builderName) { this.builderName = builderName; }

    public String getBuilderType() { return builderType; }
    public void setBuilderType(String builderType) { this.builderType = builderType; }

    public String getBhk2Price() { return bhk2Price; }
    public void setBhk2Price(String bhk2Price) { this.bhk2Price = bhk2Price; }

    public String getBhk3Price() { return bhk3Price; }
    public void setBhk3Price(String bhk3Price) { this.bhk3Price = bhk3Price; }

    public List<String> getImages() { return image; }
    public void setImages(List<String> images) { this.image = images; }

    public int getPriceValue() { return numericPrice; }
    public void setPriceValue(int numericPrice) { this.numericPrice = numericPrice; }

    public double getPricePerSqft() { return pricePerSqft; }
    public void setPricePerSqft(double pricePerSqft) { this.pricePerSqft = pricePerSqft; }

    public int getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(int relevanceScore) { this.relevanceScore = relevanceScore; }

    public Date getDateAdded() { return dateAdded; }
    public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
