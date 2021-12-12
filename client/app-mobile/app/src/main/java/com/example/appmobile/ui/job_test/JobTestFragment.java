package com.example.appmobile.ui.job_test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.TransitionAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.appmobile.R;
import com.example.appmobile.databinding.FragmentTestJobBinding;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.TestDataResults;
import com.example.appmobile.net.entries.answerQuestionData;

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
            if(viewModel.currentIndex <= viewModel.data.size() - 1) bindCard(swipeRightModel);
            else {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_from_test_to_user);
            }
        });

        binding.motionLayout.setTransitionListener(new TransitionAdapter() {
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                super.onTransitionCompleted(motionLayout, currentId);
                if( R.id.offScreenPass == currentId) {
                    motionLayout.setProgress(0f);
                    answerQuestionData data = new answerQuestionData();
                    data.question_id = viewModel.data.get(viewModel.currentIndex).id;
                    data.answer = viewModel.data.get(viewModel.currentIndex).ans1;

                    NetworkService.getInstance().getJSONApi().answerQuestion(data, NetworkService.getInstance().getToken()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(!response.isSuccessful()) Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });


                    motionLayout.setTransition(R.id.rest, R.id.like);
                    viewModel.swipe();
                    return;
                }
                if (R.id.offScreenLike == currentId){
                    motionLayout.setProgress(0f);
                    answerQuestionData data = new answerQuestionData();
                    data.question_id = viewModel.data.get(viewModel.currentIndex).id;
                    data.answer = viewModel.data.get(viewModel.currentIndex).ans2;

                    NetworkService.getInstance().getJSONApi().answerQuestion(data, NetworkService.getInstance().getToken()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(!response.isSuccessful()) Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                    motionLayout.setTransition(R.id.rest, R.id.like);
                    viewModel.swipe();
                }
            }
        });

        binding.notInterestingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.motionLayout.transitionToState(R.id.pass);
            }
        });

        binding.interestingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.motionLayout.transitionToState(R.id.like);
            }
        });
    }

    private void bindCard(SwipeRightModel swipeRightModel) {
        binding.topCard.setBackgroundColor(swipeRightModel.top.backgroundColor);
        binding.bottomCard.setBackgroundColor(swipeRightModel.bottom.backgroundColor);

        binding.question.setText(swipeRightModel.top.question);
        binding.questionBack.setText(swipeRightModel.bottom.question);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}