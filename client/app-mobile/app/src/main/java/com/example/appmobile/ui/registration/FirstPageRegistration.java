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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.appmobile.R;
import com.example.appmobile.SignInScreen;
import com.example.appmobile.databinding.FragmentFirstPageRegistrationBinding;
import com.example.appmobile.viewmodels.RegistrationViewModel;

public class FirstPageRegistration extends Fragment {

    RegistrationViewModel registrationViewModel;
    private FragmentFirstPageRegistrationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFirstPageRegistrationBinding.inflate(inflater, container, false);

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
                    // check fields
                    if (binding.surnameField.getText().toString().isEmpty()) {
                        errorColor(binding.surnameField);
                        return;
                    }

                    if (binding.nameField.getText().toString().isEmpty()) {
                        errorColor(binding.nameField);
                        return;
                    }

                    if (binding.middleNameField.getText().toString().isEmpty()) {
                        errorColor(binding.middleNameField);
                        return;
                    }

                    registrationViewModel.setSurname(binding.surnameField.getText().toString());
                    registrationViewModel.setName(binding.nameField.getText().toString());
                    registrationViewModel.setMiddleName(binding.middleNameField.getText().toString());

                    openNext();
                }
        );
    }

    private void errorColor(EditText editText) {
        editText.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.error_input_field_background));
        for (Drawable drawable : editText.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.main_error), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private void openNext() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.to_secondPage);
    }
}