package com.example.appmobile.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmobile.R;
import com.example.appmobile.adapters.ParagraphListAdapter;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.News;
import com.example.appmobile.viewmodels.SelectedNewsModel;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectednewsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selectednews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView paragraphList = view.findViewById(R.id.paragraphs);
        TextView title = view.findViewById(R.id.selected_news_title);
        TextView desc = view.findViewById(R.id.selected_news_desc);
        TextView date = view.findViewById(R.id.selected_news_date);

        ParagraphListAdapter adapter = new ParagraphListAdapter();
        paragraphList.setAdapter(adapter);

        SelectedNewsModel model = new ViewModelProvider(requireActivity()).get(SelectedNewsModel.class);
        model.selected.observe(getViewLifecycleOwner(),
            selected -> {
                NetworkService.getInstance().getJSONApi()
                        .getSpecifiedNews(NetworkService.getInstance().getToken(), selected.getId())
                        .enqueue(
                                new Callback<News>() {
                                    @Override
                                    public void onResponse(Call<News> call, Response<News> response) {
                                        if (response.isSuccessful()) {
                                            title.setText(response.body().title);
                                            desc.setText(response.body().description);
                                            date.setText(response.body().date);
                                            adapter.setList(response.body().paragraphs);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<News> call, Throwable t) {

                                    }
                                }
                        );
        });

    }
}