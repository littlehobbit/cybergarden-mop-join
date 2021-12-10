package com.example.appmobile.net;

import com.example.appmobile.net.entries.EventsListResults;
import com.example.appmobile.net.entries.LoginParams;
import com.example.appmobile.net.entries.LoginResults;
import com.example.appmobile.net.entries.NewsListResults;
import com.example.appmobile.net.entries.RegistrationParams;
import com.example.appmobile.net.entries.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/auth/register")
    public Call<Void> registrationPostRequest(@Body RegistrationParams data);

    @POST("/auth/login")
    public Call<LoginResults> loginPostRequest(@Body LoginParams data);

    @GET("/user/user")
    public Call<User> getUserDetails(@Header("Authorization") String token);

    @GET("/events/all")
    public Call<ArrayList<EventsListResults>> getEventsList(@Header("Authorization") String token);

    @GET("/news/all")
    public Call<ArrayList<NewsListResults>> getNewsList();
}