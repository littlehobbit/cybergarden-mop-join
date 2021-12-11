package com.example.appmobile.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.adapters.RecommendationListAdapter;
import com.example.appmobile.databinding.FragmentProfileBinding;
import com.example.appmobile.net.JSONPlaceHolderApi;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.NewsListResults;
import com.example.appmobile.net.entries.Recommendation;
import com.example.appmobile.net.entries.User;
import com.example.appmobile.viewmodels.UserViewModel;
import com.squareup.picasso.Picasso;

import java.security.Provider;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private ImageView userAvatar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userAvatar = binding.userAvatar;

        UserViewModel userViewModel = UserViewModel.create(requireActivity());
        userViewModel.getUser().observe(getViewLifecycleOwner(),
                user -> {
                    binding.userEmail.setText(user.email);
                    binding.userFio.setText(user.getFullName());
                    binding.userRole.setText(user.role);
                    loadUserImage(user);
                }
        );
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recommendationList = view.findViewById(R.id.recomendation_list);
        RecommendationListAdapter adapter = new RecommendationListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        NetworkService.getInstance()
                .getJSONApi()
                .getUserRecomendations(NetworkService.getInstance().getToken())
                .enqueue(new Callback<ArrayList<Recommendation>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<Recommendation>> call, @NonNull Response<ArrayList<Recommendation>> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), "Get user recommendations, yeeeee", Toast.LENGTH_SHORT).show();
                            adapter.setList(response.body());
                            recommendationList.setLayoutManager(linearLayoutManager);
                            recommendationList.setAdapter(adapter);
                            //recommendationList.getLayoutParams().height = response.body().size() * 220;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<Recommendation>> call, @NonNull Throwable t) {

                        Toast.makeText(getContext(), "Error while you get recommendations", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });



    }

    private void loadUserImage(User user) {
        Picasso.get()
                .load(NetworkService.USER_IMAGE_URL + user.id.toString())
                .into(userAvatar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}