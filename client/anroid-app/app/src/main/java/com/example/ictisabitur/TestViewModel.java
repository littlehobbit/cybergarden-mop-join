package com.example.ictisabitur;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {

    public MutableLiveData<Integer> intLiveData;

    public MutableLiveData<Integer> getIntLiveData() {
        if (intLiveData == null) {
            intLiveData = new MutableLiveData<>();
            loadData();
        }

        return intLiveData;
    }

    private void loadData() {
        /// post.valu
    }
}
