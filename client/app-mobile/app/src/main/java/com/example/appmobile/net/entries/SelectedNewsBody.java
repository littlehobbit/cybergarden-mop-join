package com.example.appmobile.net.entries;

import com.google.gson.annotations.Expose;

public class SelectedNewsBody {
    @Expose
    Integer news_id;

    public  SelectedNewsBody(Integer newsId) {
        this.news_id = newsId;
    }
}
