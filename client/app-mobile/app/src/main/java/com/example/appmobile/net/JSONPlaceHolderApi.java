package com.example.appmobile.net;

import com.example.appmobile.net.entries.EventsListResults;
import com.example.appmobile.net.entries.JoinEvent;
import com.example.appmobile.net.entries.LoginParams;
import com.example.appmobile.net.entries.LoginResults;
import com.example.appmobile.net.entries.NewsListResults;
import com.example.appmobile.net.entries.Recommendation;
import com.example.appmobile.net.entries.RegistrationParams;
import com.example.appmobile.net.entries.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @POST("/auth/register")
    public Call<Void> registrationPostRequest(@Body RegistrationParams data);

    @POST("/auth/login")
    public Call<LoginResults> loginPostRequest(@Body LoginParams data);

    @GET("/user/user")
    public Call<User> getUserDetails(@Header("Authorization") String token);

    @GET("/specialization/recommendation")
    public Call<ArrayList<Recommendation>> getUserRecomendations(@Header("Authorization") String token);

    @GET("/events/all")
    public Call<ArrayList<EventsListResults>> getEventsList(@Header("Authorization") String token);

    @GET("/news/all")
    public Call<ArrayList<NewsListResults>> getNewsList();

    @POST("/events/join")
    public Call<Void> joinEventById(@Header("Authorization") String token, @Body JoinEvent joinEvent);
}