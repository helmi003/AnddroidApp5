package com.example.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.R;

import java.util.List;

public class ActorsListAdapter extends RecyclerView.Adapter<ActorsListAdapter.ViewHolder> {

    private List<Actor> actors;
    private Context context;

    public ActorsListAdapter(List<Actor> actors) {
        this.actors = actors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_actors, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Actor actor = actors.get(position);

        holder.nameTextView.setText(actor.getName());

        Glide.with(context)
                .load(actor.getProfileImageUrl())
                .placeholder(R.drawable.ic_placeholder_image)
                .into(holder.profileImageView);
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.actorImage); // Assuming actorImage is in the layout
            nameTextView = itemView.findViewById(R.id.actorName); // Assuming actorName is in the layout
        }
    }
}
