package com.example.appmobile.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.net.entries.Specialization;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.ItemHolder> {

    private ArrayList<Specialization> recommendationList;

    public static class ItemHolder extends  RecyclerView.ViewHolder {

        Specialization recommendation;
        TextView title;
        TextView desc;
        LinearProgressIndicator progressIndicator;
        TextView percent;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.spec_name);
            progressIndicator = itemView.findViewById(R.id.recomendation_percent_bar);
            percent = itemView.findViewById(R.id.recomendation_percent);
            desc = itemView.findViewById(R.id.spec_desc);
        }

        public void setData(Specialization recommendation) {
            this.recommendation = recommendation;

            String codificator = "<font color='#000000'>" + recommendation.getCodificator()
                    + " — " + "</font>"  + recommendation.getTitle();

            title.setText(Html.fromHtml(codificator, Html.FROM_HTML_MODE_COMPACT));

            progressIndicator.setProgress(recommendation.getWeight());
            percent.setText(recommendation.getWeight().toString() + "%");
            desc.setText(recommendation.getTagGrad() + "\n" +
                    "Возможности трудоустройства: " + recommendation.getDescription());
        }

    }

    public void setList(ArrayList<Specialization> list) {
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
