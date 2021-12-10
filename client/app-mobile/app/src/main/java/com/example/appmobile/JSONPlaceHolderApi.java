package com.example.appmobile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/auth/register")
    public Call<Void> registrationPostRequest(@Body RegistrationParams data);

    @POST("/auth/login")
    public Call<LoginResults> loginPostRequest(@Body LoginParams data);
}