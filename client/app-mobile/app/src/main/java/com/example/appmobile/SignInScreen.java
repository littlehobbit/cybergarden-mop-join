package com.example.appmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        setContentView(R.layout.sign_in_screen);

        login = findViewById(R.id.login_sign_in);
        password = findViewById(R.id.password_sign_in);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .loginPostRequest(new LoginParams(login.getText().toString().trim(), password.getText().toString()))
                        .enqueue(new Callback<LoginResults>() {
                            @Override
                            public void onResponse(@NonNull Call<LoginResults> call, @NonNull Response<LoginResults> response) {
                                if(response.isSuccessful()) {
                                    LoginResults loginResults = response.body();

                                    Toast.makeText(SignInScreen.this, "Login success", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(SignInScreen.this, MainScreen.class);
//                                    startActivity(intent);
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
                Intent intent = new Intent(SignInScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });
    }
}