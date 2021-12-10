package com.example.appmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    private ArrayList<NewsCardData> data = new ArrayList<>();

    public NewsRecyclerAdapter(ArrayList<NewsCardData> data) {
        this.data = data;
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView image;

        public TextView getTitle() {
            return title;
        }

        public TextView getDescription() {
            return description;
        }

        public ImageView getImage() {
            return image;
        }


        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.event_card_title);
            description = view.findViewById(R.id.news_card_description);
            image = view.findViewById(R.id.news_card_img);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item_adapter, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTitle().setText(data.get(position).getTitle());
        viewHolder.getDescription().setText(data.get(position).getDescription());
        Picasso.get().load(data.get(position).getImgUrl()).placeholder(R.drawable.placeholder_img).error(R.drawable.e3f0a108aabbd2325203e40177f21312).into(viewHolder.getImage());
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

    public void addAll(ArrayList<NewsCardData> newData) {
        if(data == null) return;
        data.addAll(newData);
        notifyDataSetChanged();
    }
}