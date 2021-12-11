package com.example.appmobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.EventsListResults;
import com.example.appmobile.net.entries.JoinEvent;
import com.example.appmobile.net.entries.QaListResults;
import com.example.appmobile.net.entries.Tag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QaRecyclerAdapter extends RecyclerView.Adapter<QaRecyclerAdapter.ViewHolder> {

    private ArrayList<QaListResults> data = new ArrayList<>();

    public QaRecyclerAdapter(ArrayList<QaListResults> data) {
        this.data = data;
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageButton button;
        TextView question;
        TextView full_answer;
        TextView short_answer;
        CardView cardView;

        public CardView getCardView() {
            return cardView;
        }

        public TextView getQuestion() {
            return question;
        }

        public View getView() {
            return view;
        }

        public TextView getFull_answer() {
            return full_answer;
        }

        public TextView getShort_answer() {
            return short_answer;
        }

        public ImageButton getButton(){return button;}

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            button = view.findViewById(R.id.btn_qa_card_grow);
            cardView = view.findViewById(R.id.qa_card_background);
            question = view.findViewById(R.id.qa_card_question);
            full_answer = view.findViewById(R.id.qa_card_full_answer);
            short_answer = view.findViewById(R.id.qa_card_short_answer);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.qa_item_adapter, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {



        final QaListResults object = data.get(position);
        viewHolder.getButton().setOnClickListener(view -> {
            object.swapIsDescriptionShown();
            if(viewHolder.getFull_answer().getVisibility() == View.VISIBLE)
                viewHolder.getFull_answer().setVisibility(View.GONE);
            else
                viewHolder.getFull_answer().setVisibility(View.VISIBLE);
        });

        viewHolder.getCardView().setOnClickListener(view -> {
            object.swapIsDescriptionShown();
            if(viewHolder.getFull_answer().getVisibility() == View.VISIBLE)
                viewHolder.getFull_answer().setVisibility(View.GONE);
            else
                viewHolder.getFull_answer().setVisibility(View.VISIBLE);
        });

        viewHolder.getShort_answer().setText(object.getShortAnswer());
        viewHolder.getFull_answer().setText(object.getFullAnswer());
        viewHolder.getQuestion().setText(object.getQuestion());

        viewHolder.getFull_answer().setVisibility(View.GONE);

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

    public void addAll(ArrayList<QaListResults> newData) {
        if(data == null) return;
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
