package com.example.appmobile.net.entries;

public class Recommendation {
    public String specializationNumber;
    public String specializationName;
    public Integer specializationId;
    public Integer percent;

    public Recommendation(String specializationNumber,
                          String specializationName,
                          Integer specializationId,
                          Integer percent) {
        this.specializationNumber = specializationNumber;
        this.specializationName = specializationName;
        this.specializationId = specializationId;
        this.percent = percent;
    }
}
