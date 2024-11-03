package com.example.movieapp.adaptater;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.entities.Season;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {
    private List<Season> seasonList;
    private OnSeasonClickListener listener;
    private int selectedPosition = 0;
    private Context context;

    public SeasonAdapter(List<Season> seasonList, OnSeasonClickListener listener, Context context) {
        this.seasonList = seasonList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_season, parent, false);
        return new SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonViewHolder holder, int position) {
        Season season = seasonList.get(position);
        holder.seasonNumberTextView.setText("Season " + season.getNumber());
        if (position == selectedPosition) {
            Drawable roundedDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_selected_item);
            holder.itemView.setBackground(roundedDrawable);
        } else {
            Drawable roundedDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_seatdetail);
            holder.itemView.setBackground(roundedDrawable);
        }

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition(); // Use getAdapterPosition()
            if (adapterPosition != RecyclerView.NO_POSITION) { // Check if the position is valid
                selectedPosition = adapterPosition; // Update selected position
                notifyDataSetChanged(); // Refresh the RecyclerView

                if (listener != null) {
                    listener.onSeasonClick(season.getId()); // Assume Season has a method getId()
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    static class SeasonViewHolder extends RecyclerView.ViewHolder {
        TextView seasonNumberTextView;

        SeasonViewHolder(View itemView) {
            super(itemView);
            seasonNumberTextView = itemView.findViewById(R.id.seasonNumber);
        }
    }

    public interface OnSeasonClickListener {
        void onSeasonClick(int seasonId);
    }
}
