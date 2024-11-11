package com.example.movieapp.adaptater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.entities.User;
import com.example.movieapp.R;

import java.util.List;

public class UserFeed extends RecyclerView.Adapter<UserFeed.ViewHolder> {
    private final List<User> userList;
    private final OnUserClickListener listener;

    public UserFeed(List<User> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        if (user != null) {
            holder.usernameTextView.setText(user.getUsername() != null ? user.getUsername() : "Unknown User");
            // Set other views based on user object (e.g., user profile picture)
        } else {
            holder.usernameTextView.setText("User data unavailable");
        }

        holder.itemView.setOnClickListener(v -> {
            if (user != null) {
                listener.onUserClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView usernameTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username);
        }
    }
}
