package com.example.movieapp.adaptater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieapp.R;
import com.example.movieapp.Episodes;
import com.example.movieapp.entities.Episode;
import java.util.List;

public class EpisodeAdminAdapter extends RecyclerView.Adapter<EpisodeAdminAdapter.EpisodeViewHolder> {
    private List<Episode> episodeList;
    private Context context;

    public EpisodeAdminAdapter(Context context, List<Episode> episodeList) {
        this.context = context;
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode_admin, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.episodeNumberTextView.setText("Episode " + episode.getNumber());
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        TextView episodeNumberTextView;
        EpisodeViewHolder(View itemView) {
            super(itemView);
            episodeNumberTextView = itemView.findViewById(R.id.number);
        }
    }
}
