package com.example.appmobile.ui.registration;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appmobile.MainActivity;
import com.example.appmobile.R;
import com.example.appmobile.SignInScreen;
import com.example.appmobile.databinding.FragmentSecondPageRegistrationBinding;
import com.example.appmobile.databinding.FragmentThirdPageRegistrationBinding;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.LoginParams;
import com.example.appmobile.net.entries.LoginResults;
import com.example.appmobile.net.entries.RegistrationParamsBasic;
import com.example.appmobile.net.entries.RegistrationParamsNameBirthday;
import com.example.appmobile.net.entries.RegistrationParamsUserSettings;
import com.example.appmobile.viewmodels.RegistrationViewModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirdPageRegistration extends Fragment {

    RegistrationViewModel registrationViewModel;
    private FragmentThirdPageRegistrationBinding binding;

    HashMap<String, Integer> rolesDict = new HashMap<>();
    String roles[] = new String[]{"школьник", "выпускник школы", "коледж", "выпускник коледжа"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        rolesDict.put("школьник", 1);
        rolesDict.put("выпускник школы", 2);
        rolesDict.put("коледж", 3);
        rolesDict.put("выпускник коледжа", 4);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentThirdPageRegistrationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registrationViewModel = RegistrationViewModel.create(requireActivity());

        binding.btnToSignIn.setOnClickListener(
                btn -> {
                    Intent loginActivity = new Intent(requireContext(), SignInScreen.class);
                    startActivity(loginActivity);
                }
        );

        binding.toSecondPage.setOnClickListener(
                btn -> {
                    if (binding.registrationEmail.getText().toString().isEmpty()) {
                        errorColor(binding.registrationEmail);
                        return;
                    }
                    if (binding.registrationPassword.toString().isEmpty()) {
                        errorColor(binding.registrationPassword);
                        return;
                    }
                    if (binding.registrationPasswordRepeat.toString().isEmpty()) {
                        errorColor(binding.registrationPasswordRepeat);
                        return;
                    }
                    if (!(binding.registrationPasswordRepeat.getText().toString().equals(binding.registrationPassword.getText().toString()))) {
                        errorColor(binding.registrationPassword);
                        errorColor(binding.registrationPasswordRepeat);
                    }

                    registrationViewModel.setEmail(binding.registrationEmail.getText().toString());
                    registrationViewModel.setPassword(binding.registrationPassword.getText().toString());
                    sendRegistrationForm();
                }
        );
    }

    private void errorColor(EditText element) {
        element.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.error_input_field_background));
        for (Drawable drawable : element.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.main_error), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private void sendRegistrationForm() {
        RegistrationParamsBasic basic = new RegistrationParamsBasic(registrationViewModel.email.getValue(), registrationViewModel.password.getValue());
        NetworkService.
                getInstance().
                getJSONApi().
                registrationPostRequest(basic).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            loginAfterRegistration();
                        } else {
                            Toast.makeText(getContext(), "Retry again later", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("w","w");
                    }
                });
    }

    private void loginAfterRegistration() {
        LoginParams params = new LoginParams(registrationViewModel.email.getValue(), registrationViewModel.password.getValue());
        NetworkService.getInstance().getJSONApi().loginPostRequest(params).enqueue(new Callback<LoginResults>() {
            @Override
            public void onResponse(Call<LoginResults> call, Response<LoginResults> response) {
                if (response.isSuccessful()) {
                    LoginResults loginResults = response.body();
                    NetworkService.getInstance().setLoginResults(loginResults);
                    setSetting();
                }
            }

            @Override
            public void onFailure(Call<LoginResults> call, Throwable t) {

            }
        });
    }

    private void setSetting() {
        RegistrationParamsUserSettings params = new RegistrationParamsUserSettings();
        params.role = registrationViewModel.role.getValue();
        NetworkService.getInstance().getJSONApi().setUserSettings(NetworkService.getInstance().getToken(), params).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    setUserData();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void setUserData() {
        RegistrationParamsNameBirthday params = new RegistrationParamsNameBirthday(registrationViewModel.name.getValue(),
                registrationViewModel.surname.getValue(),
                registrationViewModel.middleName.getValue(),
                registrationViewModel.birthday.getValue());
        NetworkService.getInstance().getJSONApi().setUserFullNameBirthday(NetworkService.getInstance().getToken(), params).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}