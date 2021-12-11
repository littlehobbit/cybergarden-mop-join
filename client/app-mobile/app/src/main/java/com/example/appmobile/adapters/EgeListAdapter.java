package com.example.appmobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.net.entries.Ege;

import java.util.ArrayList;

public class EgeListAdapter extends RecyclerView.Adapter<EgeListAdapter.ItemHolder> {

    ArrayList<Ege> egeList = new ArrayList<>();

    public static class ItemHolder extends RecyclerView.ViewHolder {

        TextView grade;
        TextView subject;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            grade = itemView.findViewById(R.id.grade_field);
            subject = itemView.findViewById(R.id.subject_field);
        }

        public void setEge(Ege ege) {
            grade.setText(ege.grade.toString());
            subject.setText(ege.subject);
        }
    }

    public void setEgeList(ArrayList<Ege> egeList) {
        this.egeList = egeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EgeListAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ege_item, parent, false);

        return new EgeListAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EgeListAdapter.ItemHolder holder, int position) {
        holder.setEge(egeList.get(position));
    }

    @Override
    public int getItemCount() {
        return egeList.size();
    }
}
