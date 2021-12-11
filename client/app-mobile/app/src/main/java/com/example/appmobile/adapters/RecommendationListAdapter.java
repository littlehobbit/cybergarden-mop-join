package com.example.appmobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.net.entries.Recommendation;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.Comparator;

public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.ItemHolder> {

    private ArrayList<Recommendation> recommendationList;

    public static class ItemHolder extends  RecyclerView.ViewHolder {

        Recommendation recommendation;
        TextView title;
        TextView specNum;
        TextView desc;
        LinearProgressIndicator progressIndicator;
        TextView percent;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            specNum = itemView.findViewById(R.id.spec_number);
            title = itemView.findViewById(R.id.spec_name);
            progressIndicator = itemView.findViewById(R.id.recomendation_percent_bar);
            percent = itemView.findViewById(R.id.recomendation_percent);
            desc = itemView.findViewById(R.id.spec_desc);
        }

        public void setData(Recommendation recommendation) {
            this.recommendation = recommendation;
            specNum.setText(recommendation.getCodificator());
            title.setText(recommendation.getTitle());
            progressIndicator.setProgress(recommendation.getWeight());
            percent.setText(recommendation.getWeight().toString() + "%");
            desc.setText(recommendation.getTagGrad() + "\nВозможности трудоустройства: " + recommendation.getDescription());
        }

    }

    public void setList(ArrayList<Recommendation> list) {
        recommendationList = list;
        recommendationList.sort((lhs, rhs) -> Integer.compare(rhs.getWeight(), lhs.getWeight()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecommendationListAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recomendation_item, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationListAdapter.ItemHolder holder, int position) {
        holder.setData(recommendationList.get(position));
    }

    @Override
    public int getItemCount() {
        return recommendationList.size();
    }
}
