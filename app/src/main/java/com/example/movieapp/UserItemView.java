package com.example.movieapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.movieapp.entities.User;

import java.util.function.BiConsumer;

public class UserItemView extends LinearLayout {

    private TextView userNameTextView;
    private TextView userEmailTextView;
    private AppCompatButton blockButton;
    private User user;

    public UserItemView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.activity_user_item, this, true);
        userNameTextView = findViewById(R.id.userNameTextView);
        userEmailTextView = findViewById(R.id.userEmailTextView);
        blockButton = findViewById(R.id.blockButton);
    }

    // Updated method to accept a BiConsumer
    public void bind(User user, BiConsumer<String, User> onBlockClick) {
        this.user = user;
        userNameTextView.setText(user.username);
        userEmailTextView.setText(user.email);
        blockButton.setText(user.isBlocked ? "Unblock" : "Block");

        // Set click listener using the BiConsumer
        blockButton.setOnClickListener(v -> onBlockClick.accept(user.id, user));
    }
}
