package com.example.appmobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.EventsListResults;
import com.example.appmobile.net.entries.JoinEvent;
import com.example.appmobile.net.entries.Tag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder> {

    private ArrayList<EventsListResults> data = new ArrayList<>();

    public EventsRecyclerAdapter(ArrayList<EventsListResults> data) {
        this.data = data;
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView tags;
        Button btnJoinEvent;
        TextView description;
        TextView date;
        TextView address;
        ImageView image;
        ImageButton btnCardGrow;

        public TextView getAddress() {
            return address;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getTags() {
            return tags;
        }

        public ImageButton getBtnCardGrow() {
            return btnCardGrow;
        }

        public Button getBtnJoinEvent() {
            return btnJoinEvent;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getDate() {
            return date;
        }

        public ImageView getImage() {
            return image;
        }


        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.event_card_title);
            tags = view.findViewById(R.id.event_card_tags);
            description = view.findViewById(R.id.event_card_description);
            date = view.findViewById(R.id.event_card_date);
            address = view.findViewById(R.id.event_card_address);
            image = view.findViewById(R.id.news_card_img);
            btnCardGrow = view.findViewById(R.id.btn_event_card_grow);
            btnJoinEvent = view.findViewById(R.id.btn_join_event);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.events_item_adapter, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Integer currentPos = position;
        final EventsListResults object = data.get(position);
        viewHolder.getBtnCardGrow().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.swapIsDescriptionShown();
                if(object.getIsDescriptionShown()) {
                    viewHolder.getDescription().setText(object.getDescription());
                    return;
                }
                viewHolder.getDescription().setText("");
            }
        });

        viewHolder.getBtnJoinEvent().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .joinEventById(NetworkService.getInstance().getToken(), new JoinEvent(object.getId()))
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(view.getContext(), "you are added to event", Toast.LENGTH_SHORT).show();
                                    data.get(currentPos).setVisited(1);
                                    viewHolder.getBtnJoinEvent().setEnabled(false);
                                    notifyItemChanged(currentPos);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                                Toast.makeText(view.getContext(), "Error while you join event", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                            }
                        });
            }
        });

        StringBuilder tags = new StringBuilder();
        for(Tag item : object.getTags()) {
            tags.append(item.getTag()).append(" ");
        }

        if(object.getVisited() == 1) viewHolder.getBtnJoinEvent().setEnabled(false);
        if(object.getVisited() == 0) viewHolder.getBtnJoinEvent().setEnabled(true);
        viewHolder.getDescription().setText("");
        viewHolder.getTitle().setText(object.getTitle());
        viewHolder.getTags().setText(tags.toString());
        viewHolder.getDate().setText(object.getStartDate());
        viewHolder.getAddress().setText(object.getPlace());
        Picasso.get().load(NetworkService.getInstance().getBaseUrl() + "/events/getImage?id=" + object.getId()).placeholder(R.drawable.placeholder_img).error(R.drawable.e3f0a108aabbd2325203e40177f21312).into(viewHolder.getImage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public void clearAll() {
        if(data == null) return;
        data.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<EventsListResults> newData) {
        if(data == null) return;
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
