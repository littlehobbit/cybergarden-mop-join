package com.example.appmobile.ui.job_test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.TransitionAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.adapters.QaRecyclerAdapter;
import com.example.appmobile.databinding.FragmentQaBinding;
import com.example.appmobile.databinding.FragmentTestJobBinding;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.QaListResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JobTestFragment extends Fragment {

    private FragmentTestJobBinding binding;
    SwipeRightViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTestJobBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SwipeRightViewModel.class);

        viewModel.stream.observe(getViewLifecycleOwner(), swipeRightModel -> {
            bindCard(swipeRightModel);
        });

        binding.motionLayout.setTransitionListener(new TransitionAdapter() {
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                super.onTransitionCompleted(motionLayout, currentId);

                if( R.id.offScreenPass == currentId || R.id.offScreenLike == currentId) {
                    motionLayout.setProgress(0f);
                    motionLayout.setTransition(R.id.rest, R.id.like);
                    viewModel.swipe();
                }
            }
        });

        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.motionLayout.transitionToState(R.id.like);
            }
        });

        binding.passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.motionLayout.transitionToState(R.id.pass);
            }
        });

    }

    private void bindCard(SwipeRightModel swipeRightModel) {
        binding.topCard.setBackgroundColor(swipeRightModel.top.backgroundColor);
        binding.bottomCard.setBackgroundColor(swipeRightModel.bottom.backgroundColor);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}