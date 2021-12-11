package com.example.appmobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.net.entries.Paragraph;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParagraphListAdapter extends RecyclerView.Adapter<ParagraphListAdapter.ItemHolder> {

    ArrayList<Paragraph> paragraphs = new ArrayList<>();

    public static class ItemHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView text;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.paragraph_name);
            text = itemView.findViewById(R.id.paragraph_text);
        }

        public void setContent(Paragraph paragraph) {
            name.setText(paragraph.title);
            text.setText(paragraph.text);
        }
    }

    public void setList(ArrayList<Paragraph> list) {
        paragraphs = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParagraphListAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paragraph_item, parent, false);

        return new ParagraphListAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParagraphListAdapter.ItemHolder holder, int position) {
        holder.setContent(paragraphs.get(position));
    }

    @Override
    public int getItemCount() {
        return paragraphs.size();
    }
}
