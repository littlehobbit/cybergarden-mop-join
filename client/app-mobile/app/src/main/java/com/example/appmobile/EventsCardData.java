package com.example.appmobile;

public class EventsCardData {
    private String title;
    private String description;
    private String imgUrl;
    private String address;
    private String date;

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }


    public EventsCardData(String title, String description, String imgUrl, String address, String date) {
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.address = address;
        this.date = date;
    }

}
