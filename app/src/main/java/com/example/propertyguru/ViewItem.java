package com.example.propertyguru;

public class ViewItem {
    public String title, location, type;

    public ViewItem(String title, String location, String type) {
        this.title = title;
        this.location = location;
        this.type = type; // "property" or "project"
    }
}
