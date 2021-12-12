package com.example.appmobile.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmobile.R;
import com.example.appmobile.adapters.SpecializationListAdapter;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.Specialization;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SpecializationListFragment extends Fragment {

    SpecializationListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_specialization_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.spec_list);

        adapter = new SpecializationListAdapter();

        recyclerView.setAdapter(adapter);



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NetworkService.getInstance()
                .getJSONApi()
                .getAllSpecializations()
                .enqueue(
                        new Callback<ArrayList<Specialization>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Specialization>> call, Response<ArrayList<Specialization>> response) {
                                if (response.isSuccessful()) {
                                    adapter.setSpecializationList(response.body());
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Specialization>> call, Throwable t) {

                            }
                        }
                );
    }
}