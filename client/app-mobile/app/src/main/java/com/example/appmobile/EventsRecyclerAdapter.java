package com.example.appmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.net.entries.EventsListResults;
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

        boolean isDescriptionShown = false;
        TextView title;
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

        public boolean getIsDescriptionShown() {
            return isDescriptionShown;
        }

        public void swapIsDescriptionShown() {
            isDescriptionShown = !isDescriptionShown;
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
        viewHolder.getBtnCardGrow().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.swapIsDescriptionShown();
                if(viewHolder.getIsDescriptionShown()) {
                    viewHolder.getDescription().setVisibility(View.VISIBLE);
                    return;
                }
                viewHolder.getDescription().setVisibility(View.GONE);
            }
        });

        viewHolder.getTitle().setText(data.get(position).getTitle());
        viewHolder.getDescription().setText(data.get(position).getDescription());
        viewHolder.getDate().setText(data.get(position).getStartDate());
        viewHolder.getAddress().setText(data.get(position).getPlace());
        Picasso.get().load("http://192.168.43.124:3737/events/getImage?id=" + data.get(position).getId()).placeholder(R.drawable.placeholder_img).error(R.drawable.e3f0a108aabbd2325203e40177f21312).into(viewHolder.getImage());
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
