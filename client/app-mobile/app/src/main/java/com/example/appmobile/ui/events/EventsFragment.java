package com.example.appmobile.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.adapters.EventsRecyclerAdapter;
import com.example.appmobile.R;
import com.example.appmobile.databinding.FragmentEventsBinding;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.EventsListResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {

    private FragmentEventsBinding binding;
    EventsRecyclerAdapter adapter = new EventsRecyclerAdapter(new ArrayList<>());
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.events_list);
        adapter.setHasStableIds(true);
        adapter.clearAll();
        updateEventsList();

    }

    public void updateEventsList() {
        NetworkService.getInstance()
                .getJSONApi()
                .getEventsList(NetworkService.getInstance().getToken())
                .enqueue(new Callback<ArrayList<EventsListResults>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<EventsListResults>> call, @NonNull Response<ArrayList<EventsListResults>> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), "Get events list, yeeeee", Toast.LENGTH_SHORT).show();
                            adapter.addAll(response.body());
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<EventsListResults>> call, @NonNull Throwable t) {

                        Toast.makeText(getContext(), "Error while you get events list", Toast.LENGTH_SHORT).show();
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