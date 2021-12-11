package com.example.appmobile.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.adapters.RecommendationListAdapter;
import com.example.appmobile.databinding.FragmentProfileBinding;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.Recommendation;
import com.example.appmobile.net.entries.User;
import com.example.appmobile.viewmodels.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

        RecyclerView recommendationList = root.findViewById(R.id.recomendation_list);
        recommendationList.setLayoutManager(new LinearLayoutManager(requireContext()));

        RecommendationListAdapter adapter = new RecommendationListAdapter();

        ArrayList<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(
                new Recommendation(
                        "09.03.04",
                        "Программная инженерия",
                        3,
                        80)
        );
        recommendations.add(
                new Recommendation(
                        "02.03.01",
                        "Математическое обеспечение",
                        3,
                        70)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.02",
                        "Информационные системы",
                        3,
                        50)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.04",
                        "Программная инженерия",
                        3,
                        80)
        );
        recommendations.add(
                new Recommendation(
                        "02.03.01",
                        "Математическое обеспечение",
                        3,
                        70)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.02",
                        "Информационные системы",
                        3,
                        50)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.04",
                        "Программная инженерия",
                        3,
                        80)
        );
        recommendations.add(
                new Recommendation(
                        "02.03.01",
                        "Математическое обеспечение",
                        3,
                        70)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.02",
                        "Информационные системы",
                        3,
                        50)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.04",
                        "Программная инженерия",
                        3,
                        80)
        );
        recommendations.add(
                new Recommendation(
                        "02.03.01",
                        "Математическое обеспечение",
                        3,
                        70)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.02",
                        "Информационные системы",
                        3,
                        50)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.04",
                        "Программная инженерия",
                        3,
                        80)
        );
        recommendations.add(
                new Recommendation(
                        "02.03.01",
                        "Математическое обеспечение",
                        3,
                        70)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.02",
                        "Информационные системы",
                        3,
                        50)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.04",
                        "Программная инженерия",
                        3,
                        80)
        );
        recommendations.add(
                new Recommendation(
                        "02.03.01",
                        "Математическое обеспечение",
                        3,
                        70)
        );
        recommendations.add(
                new Recommendation(
                        "09.03.02",
                        "Информационные системы",
                        3,
                        50)
        );

        recommendationList.getLayoutParams().height = recommendations.size() * 220;

        adapter.serList(recommendations);

        recommendationList.setAdapter(adapter);


        return root;
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