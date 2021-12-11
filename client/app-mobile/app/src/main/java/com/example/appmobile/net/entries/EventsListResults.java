package com.example.appmobile.net.entries;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsListResults {

    Integer id;
    String title;
    String description;
    @SerializedName("start_date")
    String startDate;
    @SerializedName("end_date")
    String endDate;
    String place;
    
    ArrayList<Tag> tags;

    boolean isDescriptionShown = false;

    public boolean getIsDescriptionShown() {
        return isDescriptionShown;
    }

    public void swapIsDescriptionShown() {
        isDescriptionShown = !isDescriptionShown;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPlace() {
        return place;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }
}
