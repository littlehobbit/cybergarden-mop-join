package com.example.appmobile.net.entries;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("first_name")
    public String firstName;

    @SerializedName("second_name")
    public String secondName;

    @SerializedName("middle_name")
    public String middleName;

    @SerializedName("birthdate")
    public String birthDate;

    public String username;

    public String email;

    public String phone;

    public String role;
}
