package com.example.appmobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.NewsListResults;
import com.example.appmobile.net.entries.Tag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    private ArrayList<NewsListResults> data = new ArrayList<>();

    public NewsRecyclerAdapter(ArrayList<NewsListResults> data) {
        this.data = data;
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView tags;
        ImageView image;

        public TextView getTitle() {
            return title;
        }

        public TextView getTags() {
            return tags;
        }

        public ImageView getImage() {
            return image;
        }


        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.event_card_title);
            tags = view.findViewById(R.id.news_card_tags);
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
        final NewsListResults object = data.get(position);
        StringBuilder tags = new StringBuilder();
        for(Tag item : object.getTags()) {
            tags.append(item.getTag()).append(" ");
        }
        viewHolder.getTags().setText(tags.toString());
        viewHolder.getTitle().setText(object.getTitle());
        Picasso.get().load(NetworkService.getInstance().getBaseUrl() + "/news/getImage?id=" + object.getId()).placeholder(R.drawable.placeholder_img).error(R.drawable.e3f0a108aabbd2325203e40177f21312).into(viewHolder.getImage());
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

    public void addAll(ArrayList<NewsListResults> newData) {
        if(data == null) return;
        data.addAll(newData);
        notifyDataSetChanged();
    }
}