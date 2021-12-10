package com.example.ictisabitur.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import com.example.ictisabitur.R;
import com.example.ictisabitur.TestViewModel;

public class LoginFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TestViewModel viewModel = new ViewModelProvider(requireActivity()).get(TestViewModel.class);

//        viewModel.intLiveData.pos
        
        viewModel.intLiveData.observe(getViewLifecycleOwner(),
                integer -> {

                }
                );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public static void open(View view) {
        Navigation.findNavController(view).navigate(R.id.action_to_login);
    }
}