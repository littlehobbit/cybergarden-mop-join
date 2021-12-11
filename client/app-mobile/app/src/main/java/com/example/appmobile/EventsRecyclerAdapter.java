package com.example.appmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.net.entries.EventsListResults;
import com.example.appmobile.net.entries.Tag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

        StringBuilder tags = new StringBuilder();
        for(Tag item : object.getTags()) {
            tags.append(item.getTag()).append(" ");
        }

        viewHolder.getDescription().setText("");
        viewHolder.getTitle().setText(object.getTitle());
        viewHolder.getTags().setText(tags.toString());
        viewHolder.getDate().setText(object.getStartDate());
        viewHolder.getAddress().setText(object.getPlace());
        Picasso.get().load("http://192.168.43.124:3737/events/getImage?id=" + object.getId()).placeholder(R.drawable.placeholder_img).error(R.drawable.e3f0a108aabbd2325203e40177f21312).into(viewHolder.getImage());
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
