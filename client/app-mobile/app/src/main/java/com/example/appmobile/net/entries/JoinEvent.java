package com.example.appmobile.net.entries;

import com.google.gson.annotations.SerializedName;

public class JoinEvent {
    @SerializedName("event_id")
    private Integer eventId;

    public Integer getEventId() {
        return eventId;
    }

    public JoinEvent(Integer eventId) {
        this.eventId = eventId;
    }
}
