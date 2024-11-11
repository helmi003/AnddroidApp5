package com.example.movieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Activities.ActorSelectionActivity;
import com.example.movieapp.Activities.MovieCategoryActivity;
import com.example.movieapp.R;
import com.example.movieapp.Models.Movie;

import java.util.List;

import androidx.annotation.NonNull;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context context;
    private final List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieTitle.setText(movie.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieCategoryActivity.class);
            intent.putExtra("movieId", movie.getId());
            context.startActivity(intent);
        });

        holder.selectActorsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActorSelectionActivity.class);
            intent.putExtra("movieId", movie.getId());
            context.startActivity(intent);
        });

        Glide.with(context)
                .load(movie.getImageUri())
                .placeholder(R.drawable.ic_placeholder_image)
                .into(holder.movieThumbnail);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieThumbnail;
        TextView movieTitle;
        ImageView selectActorsButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieThumbnail = itemView.findViewById(R.id.movieThumbnail);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            selectActorsButton = itemView.findViewById(R.id.selectActorsButton);
        }
    }
}
