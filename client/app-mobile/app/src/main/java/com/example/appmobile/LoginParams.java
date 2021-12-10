package com.example.appmobile;

import com.google.gson.annotations.SerializedName;

public class LoginParams {
    @SerializedName("identifier")
    String identifier;

    @SerializedName("password")
    String password;

    public LoginParams(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }


}
