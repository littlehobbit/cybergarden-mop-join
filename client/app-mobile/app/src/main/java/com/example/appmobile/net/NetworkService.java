package com.example.appmobile.net;

import com.example.appmobile.net.entries.LoginResults;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://10.131.56.127:3737";
    private Retrofit mRetrofit;
    private String fullToken;

    public static final String NEWS_IMAGE_URL = BASE_URL + "/news/getImage?id=";
    public static final String USER_IMAGE_URL = BASE_URL + "/user/getImage?id=";

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

    public void setLoginResults(LoginResults loginResults) {
        this.fullToken = loginResults.getFullToken();
    }

    public String getToken() {
        return fullToken;
    }

    public static String fixDate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date.substring(0, date.length() - 2));
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getBaseUrl() { return BASE_URL;}

    public static String getUserImageUrl() { return USER_IMAGE_URL; };

    public static String getNewsImageUrl() { return NEWS_IMAGE_URL; };
}