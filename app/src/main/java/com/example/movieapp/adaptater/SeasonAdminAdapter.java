package com.example.movieapp.adaptater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Episodes;
import com.example.movieapp.R;
import com.example.movieapp.Seasons;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Season;
import java.util.List;

public class SeasonAdminAdapter extends RecyclerView.Adapter<SeasonAdminAdapter.SeasonViewHolder> {
    private List<Season> seasonList;
    private Context context;

    public SeasonAdminAdapter(Context context, List<Season> seasonList) {
        this.context = context;
        this.seasonList = seasonList;
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_season_admin, parent, false);
        return new SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonViewHolder holder, int position) {
        Season season = seasonList.get(position);
        holder.seasonNumberTextView.setText("Season " + season.getNumber());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Episodes.class);
            intent.putExtra("seasonID", season.getId());
            context.startActivity(intent);
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
            seasonNumberTextView = itemView.findViewById(R.id.number);
        }
    }
}
