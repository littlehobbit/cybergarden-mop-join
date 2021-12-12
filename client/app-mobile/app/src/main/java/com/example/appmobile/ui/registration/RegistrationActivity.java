package com.example.appmobile.ui.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appmobile.MainActivity;
import com.example.appmobile.R;
import com.example.appmobile.SignInScreen;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.LoginParams;
import com.example.appmobile.net.entries.LoginResults;
import com.example.appmobile.net.entries.RegistrationParamsBasic;
import com.example.appmobile.net.entries.RegistrationParamsNameBirthday;
import com.example.appmobile.net.entries.RegistrationParamsUserSettings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
//    Button btnToSignIn;
//    Button btnRegistration;
//
//    EditText firstName;
//    EditText secondName;
//    EditText middleName;
//    EditText birthday;
//
//    EditText role;
//    EditText email;
//    EditText password;
//    EditText passwordRepeat;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
//
//        firstName = findViewById(R.id.name_registration);
//        secondName = findViewById(R.id.surname_registration);
//        middleName = findViewById(R.id.midle_name_registration);
//        birthday = findViewById(R.id.birthday_registration);
//
//        role = findViewById(R.id.role_registration);
//        email = findViewById(R.id.email_registration);
//        password = findViewById(R.id.password_registration);
//        passwordRepeat = findViewById(R.id.password_repeat_registration);
//
//        btnRegistration = findViewById(R.id.btn_sign_up_registration);
//        btnRegistration.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkEmptyFields() && checkPasswordsEquality()) {
//                    makeBasicRegistration();
//                }
//            }
//        });
//
//        btnToSignIn = findViewById(R.id.btn_back_to_sign_in);
//        btnToSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RegistrationActivity.this, SignInScreen.class);
//                startActivity(intent);
//            }
//        });
    }

//
//    void makeBasicRegistration() {
//        NetworkService.getInstance()
//                .getJSONApi()
//                .registrationPostRequest(new RegistrationParamsBasic(email.getText().toString().trim(), password.getText().toString()))
//                .enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(RegistrationActivity.this, "basic registration is success", Toast.LENGTH_SHORT).show();
//                            loginAfterBasicRegistration();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
//
//                        Toast.makeText(RegistrationActivity.this, "Error while sign up", Toast.LENGTH_SHORT).show();
//                        t.printStackTrace();
//                    }
//                });
//    }
//
//    void loginAfterBasicRegistration() {
//        NetworkService.getInstance()
//                .getJSONApi()
//                .loginPostRequest(new LoginParams(email.getText().toString().trim(), password.getText().toString()))
//                .enqueue(new Callback<LoginResults>() {
//                    @Override
//                    public void onResponse(@NonNull Call<LoginResults> call, @NonNull Response<LoginResults> response) {
//                        if (response.isSuccessful()) {
//                            LoginResults loginResults = response.body();
//                            NetworkService.getInstance().setLoginResults(loginResults);
//
//                            Toast.makeText(RegistrationActivity.this, "login after registration is success", Toast.LENGTH_SHORT).show();
//                            registrationUserFullNameBirthday();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<LoginResults> call, @NonNull Throwable t) {
//
//                        Toast.makeText(RegistrationActivity.this, "Error while login after registration", Toast.LENGTH_SHORT).show();
//                        t.printStackTrace();
//                    }
//                });
//    }
//
//    void registrationUserFullNameBirthday() {
//        NetworkService.getInstance()
//                .getJSONApi()
//                .setUserFullNameBirthday(NetworkService.getInstance().getToken(), new RegistrationParamsNameBirthday(
//                        firstName.getText().toString(),
//                        secondName.getText().toString(),
//                        middleName.getText().toString(),
//                        birthday.getText().toString()
//                ))
//                .enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(RegistrationActivity.this, "you set full name and birthday is success", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
//
//                        Toast.makeText(RegistrationActivity.this, "Error while set full name and birthday", Toast.LENGTH_SHORT).show();
//                        t.printStackTrace();
//                    }
//                });
//    }
//
//    void setUserSettings() {
//        NetworkService.getInstance()
//                .getJSONApi()
//                .setUserSettings(NetworkService.getInstance().getToken(), new RegistrationParamsUserSettings(
//                        password.getText().toString(),
//                        email.getText().toString()
//                ))
//                .enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(RegistrationActivity.this, "set user profile fields is success", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
//
//                        Toast.makeText(RegistrationActivity.this, "Error while set user profile fields", Toast.LENGTH_SHORT).show();
//                        t.printStackTrace();
//                    }
//                });
//    }
//
//    boolean checkEmptyFields() {
//        return !firstName.getText().toString().trim().equals("") &&
//                !secondName.getText().toString().trim().equals("") &&
//                !middleName.getText().toString().trim().equals("") &&
//                !role.getText().toString().trim().equals("") &&
//                !email.getText().toString().trim().equals("") &&
//                !password.getText().toString().equals("") &&
//                !passwordRepeat.getText().toString().equals("");
//    }
//
//    boolean checkPasswordsEquality() {
//        return password.getText().toString().equals(passwordRepeat.getText().toString());
//    }

}