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

public class SignUpScreen extends AppCompatActivity {

    Button btnToSignIn;
    Button btnRegistration;

    EditText name;
    EditText email;
    EditText password;
    EditText passwordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);

        name = findViewById(R.id.name_registration);
        email = findViewById(R.id.email_registration);
        password = findViewById(R.id.password_registration);
        passwordRepeat = findViewById(R.id.password_repeat_registration);

        btnRegistration = findViewById(R.id.btn_sign_up_registration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .registrationPostRequest(new RegistrationParams(email.getText().toString().trim(), password.getText().toString()))
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(SignUpScreen.this, "Reg is success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpScreen.this, SignInScreen.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                                Toast.makeText(SignUpScreen.this, "Error while sign up", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                            }
                        });
            }
        });

        btnToSignIn = findViewById(R.id.btn_back_to_sign_in);
        btnToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpScreen.this, SignInScreen.class);
                startActivity(intent);
            }
        });
    }
}