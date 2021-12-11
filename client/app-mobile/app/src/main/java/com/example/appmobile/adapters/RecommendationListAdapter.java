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

public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.ItemHolder> {

    private ArrayList<Recommendation> recommendationList;

    public static class ItemHolder extends  RecyclerView.ViewHolder {

        TextView specName;
        TextView specNum;
        LinearProgressIndicator progressIndicator;
        TextView percent;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            specNum = itemView.findViewById(R.id.spec_number);
            specName = itemView.findViewById(R.id.spec_name);
            progressIndicator = itemView.findViewById(R.id.recomendation_percent_bar);
            percent = itemView.findViewById(R.id.recomendation_percent);
        }

        public void setData(Recommendation recommendation) {
            specNum.setText(recommendation.specializationNumber);
            specName.setText(recommendation.specializationName);
            progressIndicator.setProgress(recommendation.percent);
            percent.setText(recommendation.percent.toString() + "%");
        }

    }

    public void serList(ArrayList<Recommendation> list) {
        recommendationList = list;
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
