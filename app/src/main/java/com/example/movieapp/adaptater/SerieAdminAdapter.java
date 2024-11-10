package com.example.movieapp.adaptater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.Seasons;
import com.example.movieapp.entities.Serie;
import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;


public class SerieAdminAdapter extends RecyclerView.Adapter<SerieAdminAdapter.SerieViewHolder> {

    private List<Serie> series;
    private Context context;

    public SerieAdminAdapter(Context context, List<Serie> series) {
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout directly
        View view = LayoutInflater.from(context).inflate(R.layout.item_serie, parent, false);
        return new SerieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieViewHolder holder, int position) {
        Serie serie = series.get(position);
        holder.bind(serie);
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    class SerieViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;

        public SerieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Serie serie = series.get(position);
                    Intent intent = new Intent(context, Seasons.class);
                    intent.putExtra("serieID", serie.getId());
                    context.startActivity(intent);
                }
            });
        }

        public void bind(Serie serie) {
            title.setText(serie.getTitle());
            Glide.with(context).load(serie.getImageUri()).into(image);
        }
    }
}

