package com.example.appmobile.ui.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.NewsRecyclerAdapter;
import com.example.appmobile.NewsCardData;
import com.example.appmobile.R;
import com.example.appmobile.databinding.FragmentNewsBinding;
import com.example.appmobile.viewmodels.UserViewModel;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(new ArrayList<>());
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
        adapter.clearAll();
        ArrayList<NewsCardData> recyclerData = new ArrayList<>();

        recyclerData.add(new NewsCardData("День открытых дверей", "День открытых дверей пройдет онлайн в 10:00 бакалавриат и в 11:00 магистратура\n" +
                "Для абитуриентов, поступающих на образовательные программы Академии психологии и педагогики, предлагается дополнительно программа образовательного интенсива, который проводится в рамках Дней открытых дверей АПП ЮФУ.", "1"));
        recyclerData.add(new NewsCardData("День открытых дверей", "День открытых дверей пройдет онлайн в 10:00 бакалавриат и в 11:00 магистратура\n" +
                "Для абитуриентов, поступающих на образовательные программы Академии психологии и педагогики, предлагается дополнительно программа образовательного интенсива, который проводится в рамках Дней открытых дверей АПП ЮФУ.", "1"));
        recyclerData.add(new NewsCardData("День открытых дверей", "День открытых дверей пройдет онлайн в 10:00 бакалавриат и в 11:00 магистратура\n" +
                "Для абитуриентов, поступающих на образовательные программы Академии психологии и педагогики, предлагается дополнительно программа образовательного интенсива, который проводится в рамках Дней открытых дверей АПП ЮФУ.", "1"));
        recyclerData.add(new NewsCardData("День открытых дверей", "День открытых дверей пройдет онлайн в 10:00 бакалавриат и в 11:00 магистратура\n" +
                "Для абитуриентов, поступающих на образовательные программы Академии психологии и педагогики, предлагается дополнительно программа образовательного интенсива, который проводится в рамках Дней открытых дверей АПП ЮФУ.", "1"));
        recyclerData.add(new NewsCardData("День открытых дверей", "День открытых дверей пройдет онлайн в 10:00 бакалавриат и в 11:00 магистратура\n" +
                "Для абитуриентов, поступающих на образовательные программы Академии психологии и педагогики, предлагается дополнительно программа образовательного интенсива, который проводится в рамках Дней открытых дверей АПП ЮФУ.", "1"));

        adapter.addAll(recyclerData);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}