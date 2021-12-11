package com.example.appmobile.net.entries;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class News {
    @Expose
    public Integer id;

    @Expose
    public String title;

    @Expose
    public String date;

    @Expose
    public String description;

    @Expose
    public ArrayList<Paragraph> paragraphs;
}
