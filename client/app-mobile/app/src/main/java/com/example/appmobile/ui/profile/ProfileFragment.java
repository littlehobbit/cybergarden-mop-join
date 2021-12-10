package com.example.appmobile.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appmobile.databinding.FragmentProfileBinding;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.User;
import com.example.appmobile.viewmodels.UserViewModel;
import com.squareup.picasso.Picasso;

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