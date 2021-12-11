package com.example.appmobile.net;

import com.example.appmobile.net.entries.EventsListResults;
import com.example.appmobile.net.entries.JoinEvent;
import com.example.appmobile.net.entries.LoginParams;
import com.example.appmobile.net.entries.LoginResults;
import com.example.appmobile.net.entries.News;
import com.example.appmobile.net.entries.NewsListResults;
import com.example.appmobile.net.entries.Specialization;
import com.example.appmobile.net.entries.QaListResults;
import com.example.appmobile.net.entries.Recommendation;
import com.example.appmobile.net.entries.RegistrationParamsBasic;
import com.example.appmobile.net.entries.RegistrationParamsNameBirthday;
import com.example.appmobile.net.entries.RegistrationParamsUserSettings;
import com.example.appmobile.net.entries.SelectedNewsBody;
import com.example.appmobile.net.entries.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/auth/register")
    public Call<Void> registrationPostRequest(@Body RegistrationParamsBasic data);

    @POST("/user/edit")
    public Call<Void> setUserFullNameBirthday(@Header("Authorization") String token, @Body RegistrationParamsNameBirthday data);

    @POST("/auth/register")
    public Call<Void> setUserSettings(@Header("Authorization") String token, @Body RegistrationParamsUserSettings data);

    @POST("/auth/login")
    public Call<LoginResults> loginPostRequest(@Body LoginParams data);

    @GET("/user/user")
    public Call<User> getUserDetails(@Header("Authorization") String token);

    @GET("/specialization/recommendation")
    public Call<ArrayList<Specialization>> getUserRecomendations(@Header("Authorization") String token);

    @GET("/specialization/all")
    public Call<ArrayList<Specialization>> getAllSpecializations();

    @GET("/events/all")
    public Call<ArrayList<EventsListResults>> getEventsList(@Header("Authorization") String token);

    @GET("/news/all")
    public Call<ArrayList<NewsListResults>> getNewsList();

    @POST("/news/read/")
    public Call<News> getSpecifiedNews(@Header("Authorization") String token, @Body SelectedNewsBody body);
    
    @POST("/events/join")
    public Call<Void> joinEventById(@Header("Authorization") String token, @Body JoinEvent joinEvent);

    @GET("/qna/all")
    public Call<ArrayList<QaListResults>> getQaListData();

}