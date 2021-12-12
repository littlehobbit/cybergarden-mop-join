package com.example.appmobile;

public class NewsCardData {
    private String title;
    private String description;
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }


    public NewsCardData(String title, String description, String imgUrl) {
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
    }
}
