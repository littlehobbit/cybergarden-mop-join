package com.example.appmobile.net.entries;

import com.google.gson.annotations.SerializedName;

public class Recommendation {
    private Integer id;
    private String codificator;
    private String title;
    @SerializedName("tag_grad")
    private String tagGrad;
    @SerializedName("tag_type")
    private String tagType;
    private String description;
    private Integer weight;

    public Integer getId() {
        return id;
    }

    public String getCodificator() {
        return codificator;
    }

    public String getTitle() {
        return title;
    }

    public String getTagGrad() {
        return tagGrad;
    }

    public String getTagType() {
        return tagType;
    }

    public String getDescription() {
        return description;
    }

    public Integer getWeight() {
        return weight;
    }


    public Recommendation(Integer id,
                          String codificator,
                          String title,
                          String tagGrad,
                          String tagType,
                          String description,
                          Integer weight) {
        this.id = id;
        this.codificator = codificator;
        this.title = title;
        this.tagGrad = tagGrad;
        this.tagType = tagType;
        this.description = description;
        this.weight = weight;
    }
}
