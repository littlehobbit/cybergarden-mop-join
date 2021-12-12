package com.example.appmobile.ui.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.adapters.NewsRecyclerAdapter;
import com.example.appmobile.R;
import com.example.appmobile.databinding.FragmentNewsBinding;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.NewsListResults;
import com.example.appmobile.viewmodels.SelectedNewsModel;
import com.example.appmobile.viewmodels.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    NewsRecyclerAdapter adapter;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUser().observe(getViewLifecycleOwner(),
                user -> {
                    Log.i("NewsFragment", "loaded user " + user.firstName);
                }
        );
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.news_list);

        SelectedNewsModel model = new ViewModelProvider(requireActivity()).get(SelectedNewsModel.class);
        adapter = new NewsRecyclerAdapter(new ArrayList<>(), news -> {
            model.selected.postValue(news);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.to_selectedNews);
        });

        adapter.clearAll();

        NetworkService.getInstance()
                .getJSONApi()
                .getNewsList()
                .enqueue(new Callback<ArrayList<NewsListResults>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<NewsListResults>> call, @NonNull Response<ArrayList<NewsListResults>> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), "Get news list, yeeeee", Toast.LENGTH_SHORT).show();
                            adapter.addAll(response.body());
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<NewsListResults>> call, @NonNull Throwable t) {

                        Toast.makeText(getContext(), "Error while you get news list", Toast.LENGTH_SHORT).show();
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