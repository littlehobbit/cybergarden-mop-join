package com.example.appmobile.net.entries;

import java.util.ArrayList;

public class NewsListResults {

    Integer id;
    String title;
    String date;

    class Tag {
        Integer weight;
        String tag;

        public Integer getWeight() {
            return weight;
        }

        public String getTag() {
            return tag;
        }
    }

    ArrayList<Tag> tags;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }
}