package com.example.appmobile.net.entries;

import com.google.gson.annotations.SerializedName;

public class RegistrationParamsNameBirthday {
    @SerializedName("first_name")
    String firstName;
    @SerializedName("second_name")
    String secondName;
    @SerializedName("middle_name")
    String middleName;
    String birthdate;

    public RegistrationParamsNameBirthday(String firstName,
                                          String secondName,
                                          String middleName,
                                          String birthdate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.birthdate = birthdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getBirthdate() {
        return birthdate;
    }


}
