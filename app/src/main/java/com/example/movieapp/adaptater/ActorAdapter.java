package com.example.movieapp.adaptater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.R;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> {
    private List<Actor> actorList;
    private Context context;
    private OnItemClickListener listener; // Add a listener for item clicks

    public interface OnItemClickListener {
        void onEditClick(Actor actor);
        void onDeleteClick(Actor actor);
    }

    public ActorAdapter(Context context, List<Actor> actorList, OnItemClickListener listener) {
        this.context = context;
        this.actorList = actorList;
        this.listener = listener; // Initialize the listener
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.actor_item, parent, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = actorList.get(position);
        holder.actorName.setText(actor.getName() != null ? actor.getName() : "Unknown Name");
        holder.actorBio.setText(actor.getBio() != null ? actor.getBio() : "No bio available.");

        if (actor.getProfileImageUrl() != null && !actor.getProfileImageUrl().isEmpty()) {
            Glide.with(context).load(actor.getProfileImageUrl()).into(holder.actorProfileImage);
        } else {
            holder.actorProfileImage.setImageResource(R.drawable.ic_placeholder_image);
        }

        holder.editButton.setOnClickListener(v -> listener.onEditClick(actor));

        holder.deleteButton.setOnClickListener(v  -> listener.onDeleteClick(actor));
    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }

    public static class ActorViewHolder extends RecyclerView.ViewHolder {
        public TextView actorName, actorBio;
        public ImageView actorProfileImage;
        public ImageButton editButton, deleteButton;

        public ActorViewHolder(View itemView) {
            super(itemView);
            actorName = itemView.findViewById(R.id.actorName);
            actorBio = itemView.findViewById(R.id.actorBio);
            actorProfileImage = itemView.findViewById(R.id.actorProfileImage);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
