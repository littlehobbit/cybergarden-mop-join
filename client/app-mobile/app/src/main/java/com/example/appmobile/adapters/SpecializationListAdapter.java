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

import java.util.ArrayList;

public class SpecializationListAdapter extends RecyclerView.Adapter<SpecializationListAdapter.ItemHolder> {

    ArrayList<Specialization> specializationList = new ArrayList<>();

    public static class ItemHolder extends RecyclerView.ViewHolder {

        Specialization specialization;
        TextView specName;
        TextView specDesc;

        RecyclerView recyclerView;
        EgeListAdapter adapter;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            specName = itemView.findViewById(R.id.spec_name);
            specDesc = itemView.findViewById(R.id.spec_desc);

            recyclerView = itemView.findViewById(R.id.ege_list);
            adapter = new EgeListAdapter();
            recyclerView.setAdapter(adapter);
        }

        public void setSpecialization(Specialization specialization) {
            this.specialization = specialization;

            String codificator = "<font color='#000000'>" + specialization.getCodificator()
                    + " — " + "</font>"  + specialization.getTitle();

            specName.setText(Html.fromHtml(codificator, Html.FROM_HTML_MODE_COMPACT));

            specDesc.setText(specialization.getTagGrad() + "\n" +
                    "Возможности трудоустройства: " + specialization.getDescription());

            adapter.setEgeList(specialization.ege);
        }
    }

    public void setSpecializationList(ArrayList<Specialization> specializationList) {
        this.specializationList = specializationList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specialization_item, parent, false);

        return new SpecializationListAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.setSpecialization(specializationList.get(position));
    }

    @Override
    public int getItemCount() {
        return specializationList.size();
    }

}
