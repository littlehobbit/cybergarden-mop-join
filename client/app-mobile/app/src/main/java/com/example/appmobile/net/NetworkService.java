package com.example.appmobile.net;

import com.example.appmobile.net.entries.LoginResults;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://192.168.43.124:3737";
    private Retrofit mRetrofit;
    private String fullToken;

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
}