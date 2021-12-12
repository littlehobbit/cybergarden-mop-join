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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.appmobile.R;
import com.example.appmobile.SignInScreen;
import com.example.appmobile.databinding.FragmentSecondPageRegistrationBinding;
import com.example.appmobile.viewmodels.RegistrationViewModel;

import java.util.HashMap;

public class SecondPageRegistration extends Fragment {

    RegistrationViewModel registrationViewModel;
    private FragmentSecondPageRegistrationBinding binding;

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
        binding = FragmentSecondPageRegistrationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(), R.layout.listitem, R.id.textview, roles);

        binding.roleField.setAdapter(adapter);

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
                    if (binding.birthdayField.getText().toString().isEmpty()) {
                        errorColor(binding.birthdayField);
                        return;
                    }

                    if (binding.roleField.getSelectedItem().toString().isEmpty()) {
                        errorColor(binding.roleField);
                        return;
                    }
                    try {
                        registrationViewModel.setRole(rolesDict.get(binding.roleField.getSelectedItem().toString()));
                    } catch (Exception e) {
                        errorColor(binding.roleField);
                        return;
                    }

                    openNext();
                }
        );
    }

    private void errorColor(Spinner element) {
        element.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.error_input_field_background));
    }

    private void errorColor(EditText element) {
        element.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.error_input_field_background));
        for (Drawable drawable : element.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.main_error), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private void openNext() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.to_thirdPage);
    }
}