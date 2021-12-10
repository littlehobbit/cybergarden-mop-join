package com.example.appmobile.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.LoginParams;
import com.example.appmobile.net.entries.LoginResults;
import com.example.appmobile.net.entries.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    public MutableLiveData<User> user;
    private String token;

    public UserViewModel() {
        token = NetworkService.getInstance().getToken();
    }

    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
            loadUser();
        }
        return user;
    }


    private void loadUser() {
        NetworkService.getInstance().getJSONApi().getUserDetails(token).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.i("/user call", "response");
                        if (response.isSuccessful()) {
                            Log.i("/user call", "successful");
                            user.postValue(response.body());
                        } else {
                            Log.i("/user call", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.i("/user call", "completle failed");
                    }
                }
        );
    }

}
