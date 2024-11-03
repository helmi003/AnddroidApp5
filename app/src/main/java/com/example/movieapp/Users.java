package com.example.movieapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.UserAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Role;
import com.example.movieapp.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class Users extends AppCompatActivity {

    private RecyclerView usersRecyclerView;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finish());
        loadUsers();
    }

    private void loadUsers() {
        List<User> allUsers = ApplicationDatabase.getAppDatabase(this).userDAO().getAllUsers();
        List<User> filteredUsers = allUsers.stream()
                .filter(user -> user.role != Role.ADMIN)
                .collect(Collectors.toList());

        UserAdapter adapter = new UserAdapter(this, filteredUsers);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersRecyclerView.setAdapter(adapter);
    }
}
