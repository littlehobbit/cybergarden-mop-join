package com.example.appmobile.net.entries;

import com.google.gson.annotations.SerializedName;

public class LoginResults {
    @SerializedName("access_token")
    String token;

    @SerializedName("token_type")
    String tokenType;

    public String getFullToken() {
        return tokenType + " " + token;
    }
}
