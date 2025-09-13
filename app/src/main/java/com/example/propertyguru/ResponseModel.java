package com.example.propertyguru;

public class ResponseModel {
    public String name, date, score, type, details, listing;

    public ResponseModel(String name, String date, String score, String type, String details, String listing) {
        this.name = name;
        this.date = date;
        this.score = score;
        this.type = type;
        this.details = details;
        this.listing = listing;
    }
}
