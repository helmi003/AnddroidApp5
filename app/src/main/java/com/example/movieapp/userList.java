package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Role;
import com.example.movieapp.entities.User;

import java.util.List;

public class userList extends AppCompatActivity {

    private LinearLayout usersContainer;
    private ImageView backArrow; // Declare backArrow

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        usersContainer = findViewById(R.id.usersContainer);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        loadUsers();
    }

    private void loadUsers() {
        // Clear any existing views before adding
        usersContainer.removeAllViews();

        // Fetch all users
        List<User> users = ApplicationDatabase.getAppDatabase(this).userDAO().getAllUsers();
        for (User user : users) {
            if (user.role != Role.ADMIN) {
                UserItemView userItemView = new UserItemView(this);
                userItemView.bind(user, (id, userObj) -> {
                    toggleBlockUser(userItemView, userObj);
                });
                usersContainer.addView(userItemView);
            }
        }
    }

    private void toggleBlockUser(UserItemView userItemView, User user) {
        System.out.println("Toggling block status for user ID: " + user.id);
        ApplicationDatabase.getAppDatabase(this).userDAO().toggleBlockUser(user.id, !user.isBlocked);
        user.isBlocked = !user.isBlocked;
        userItemView.bind(user, this::onBlockClick);
    }

    public void onBlockClick(String id, User user) {
        ApplicationDatabase.getAppDatabase(this).userDAO().toggleBlockUser(user.id, !user.isBlocked);
        user.isBlocked = !user.isBlocked;
        System.out.println("Block/Unblock action for user with ID: " + id);
    }
}
