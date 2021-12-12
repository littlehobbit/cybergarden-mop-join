package com.example.appmobile.ui.job_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.appmobile.MainActivity;
import com.example.appmobile.SignInScreen;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.QaListResults;
import com.example.appmobile.net.entries.TestDataResults;

import java.util.ArrayList;
import java.util.TooManyListenersException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeRightViewModel extends ViewModel {
    public MutableLiveData<SwipeRightModel> stream = new MutableLiveData<>();
    public int currentIndex = 0;
    public ArrayList<SwipeRightCardModel> data = new ArrayList<>();

    public SwipeRightViewModel() {
        currentIndex = 0;

        NetworkService.getInstance().getJSONApi().getTestData().enqueue(new Callback<ArrayList<TestDataResults>>() {
            @Override
            public void onResponse(Call<ArrayList<TestDataResults>> call, Response<ArrayList<TestDataResults>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        for(TestDataResults i: response.body()) {
                            data.add(new SwipeRightCardModel(Color.parseColor("#ffffff"), i.answers.get(0).answer, i.answers.get(1).answer, i.question, i.id));
                        }
                        stream.setValue(new SwipeRightModel(getTopCard(), getBottomCard()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TestDataResults>> call, Throwable t) {

            }
        });
    }

    public SwipeRightCardModel getTopCard() {
        return data.get(currentIndex % data.size());
    }

    public SwipeRightCardModel getBottomCard() {
        return data.get((currentIndex + 1) % data.size());
    }

    public void swipe() {
        currentIndex++;
        stream.postValue(new SwipeRightModel(getTopCard(), getBottomCard()));
    }
}
