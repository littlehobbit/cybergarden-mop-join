package com.example.appmobile.ui.qa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appmobile.R;
import com.example.appmobile.adapters.QaRecyclerAdapter;
import com.example.appmobile.databinding.FragmentQaBinding;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.QaListResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QaFragment extends Fragment {

    private FragmentQaBinding binding;
    QaRecyclerAdapter adapter = new QaRecyclerAdapter(new ArrayList<>());
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentQaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = binding.qaList;
        adapter.clearAll();
        updateQAList();

    }

    public void updateQAList() {
        NetworkService.getInstance()
                .getJSONApi()
                .getQaListData()
                .enqueue(new Callback<ArrayList<QaListResults>>() {
                    @Override
                    public void onResponse(Call<ArrayList<QaListResults>> call, Response<ArrayList<QaListResults>> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), "Get qa list, yeeeee", Toast.LENGTH_SHORT).show();
                            recyclerView.setAdapter(adapter);
                            adapter.addAll(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<QaListResults>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error while you get qa list", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}