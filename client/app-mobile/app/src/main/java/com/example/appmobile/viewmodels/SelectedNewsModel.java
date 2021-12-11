package com.example.appmobile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appmobile.net.entries.NewsListResults;

public class SelectedNewsModel extends ViewModel {
    public final MutableLiveData<NewsListResults> selected = new MutableLiveData<>();
}
