package com.example.appmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.LoginParams;
import com.example.appmobile.net.entries.LoginResults;
import com.example.appmobile.ui.registration.RegistrationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {

    Button btnSignIn;
    Button btnToRegScreen;

    EditText login;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.sign_in_screen);
        getSupportActionBar().setTitle("Вход в приложение");

        login = findViewById(R.id.login_sign_in);
        password = findViewById(R.id.password_sign_in);

        btnSignIn = findViewById(R.id.btn_sign_in);
        Context ctx = this;
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login.getText().toString().equals("") && password.getText().toString().equals("")) {
                    Intent intent = new Intent(SignInScreen.this, MainActivity.class);
                    startActivity(intent);
                    return;
                }

                login.setBackground(AppCompatResources.getDrawable(ctx, R.drawable.input_field_bsckground));
                for (Drawable drawable : login.getCompoundDrawables()) {
                    if (drawable != null) {
                        drawable.setColorFilter(new PorterDuffColorFilter(getColor(R.color.main_blue), PorterDuff.Mode.SRC_IN));
                    }
                }
                password.setBackground(AppCompatResources.getDrawable(ctx, R.drawable.input_field_bsckground));
                for (Drawable drawable : password.getCompoundDrawables()) {
                    if (drawable != null) {
                        drawable.setColorFilter(new PorterDuffColorFilter(getColor(R.color.main_blue), PorterDuff.Mode.SRC_IN));
                    }
                }

                NetworkService.getInstance()
                        .getJSONApi()
                        .loginPostRequest(new LoginParams(login.getText().toString().trim(), password.getText().toString()))
                        .enqueue(new Callback<LoginResults>() {
                            @Override
                            public void onResponse(@NonNull Call<LoginResults> call, @NonNull Response<LoginResults> response) {
                                if(response.isSuccessful()) {
                                    LoginResults loginResults = response.body();
                                    NetworkService.getInstance().setLoginResults(loginResults);

                                    Toast.makeText(SignInScreen.this, "Login success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignInScreen.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    login.setBackground(AppCompatResources.getDrawable(ctx, R.drawable.error_input_field_background));
                                    for (Drawable drawable : login.getCompoundDrawables()) {
                                        if (drawable != null) {
                                            drawable.setColorFilter(new PorterDuffColorFilter(getColor(R.color.main_error), PorterDuff.Mode.SRC_IN));
                                        }
                                    }
                                    password.setBackground(AppCompatResources.getDrawable(ctx, R.drawable.error_input_field_background));
                                    for (Drawable drawable : password.getCompoundDrawables()) {
                                        if (drawable != null) {
                                            drawable.setColorFilter(new PorterDuffColorFilter(getColor(R.color.main_error), PorterDuff.Mode.SRC_IN));
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<LoginResults> call, @NonNull Throwable t) {

                                Toast.makeText(SignInScreen.this, "Error while sign in", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                            }
                        });
            }
        });

        btnToRegScreen = findViewById(R.id.btn_to_sign_up_screen);
        btnToRegScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInScreen.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        Thread t = new Thread(){
            @Override
            public synchronized void start() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "ИКТИБ Абитуриентам";
                    String description = "ИКТИБ Абитуриентам";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel("ИКТИБ Абитуриентам", name, importance);
                    channel.setDescription(description);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }
            }
        };
        t.start();
    }
}