package com.example.appmobile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.NewsListResults;
import com.example.appmobile.net.entries.Tag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    public static interface OnClick {
        void onClicked(NewsListResults news);
    }

    private ArrayList<NewsListResults> data = new ArrayList<>();
    private OnClick onClickListener;

    public NewsRecyclerAdapter(ArrayList<NewsListResults> data, OnClick onClickListener) {
        this.onClickListener = onClickListener;
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
        NewsListResults news;

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

        public NewsListResults getNews() {
            return news;
        }

        public void setNews(NewsListResults newsRes) {
            this.news = newsRes;
            StringBuilder tags = new StringBuilder();
            for(Tag item : newsRes.getTags()) {
                tags.append(item.getTag()).append(" ");
            }
            this.tags.setText(tags.toString());
            this.title.setText(newsRes.getTitle());
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item_adapter, viewGroup, false);

        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            onClickListener.onClicked(holder.news);
        });

        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        NewsListResults object = data.get(position);
        viewHolder.setNews(object);
        Picasso.get().load(NetworkService.NEWS_IMAGE_URL
                + object.getId()).placeholder(R.drawable.placeholder_img).error(R.drawable.e3f0a108aabbd2325203e40177f21312).into(viewHolder.getImage());
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