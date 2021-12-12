package com.example.appmobile.ui.job_test;

import android.graphics.Color;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class SwipeRightViewModel extends ViewModel {
    public final MutableLiveData<SwipeRightModel> stream;
    public int currentIndex = 0;
    public ArrayList<SwipeRightCardModel> data = new ArrayList<>();

    public SwipeRightViewModel() {
        currentIndex = 0;
        data.add(new SwipeRightCardModel( Color.parseColor("#E91E63")));
        data.add(new SwipeRightCardModel( Color.parseColor("#2196F3")));
        data.add(new SwipeRightCardModel( Color.parseColor("#F44336")));
        data.add(new SwipeRightCardModel( Color.parseColor("#9E9E9E")));
        stream = new MutableLiveData<>(new SwipeRightModel(getTopCard(), getBottomCard()));
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
