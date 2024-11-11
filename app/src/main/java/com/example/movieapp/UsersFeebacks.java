package com.example.movieapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.ChatFeed;
import com.example.movieapp.adaptater.UserAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Role;
import com.example.movieapp.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersFeebacks extends AppCompatActivity {

    private RecyclerView usersRecyclerView;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_feebacks);
        usersRecyclerView = findViewById(R.id.feedsRecyclerView);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finish());
        loadUsers();
    }

    private void loadUsers() {
        List<User> allUsers = ApplicationDatabase.getAppDatabase(this).userDAO().getAllUsers();
        List<User> filteredUsers = allUsers.stream()
                .filter(user -> user.role != Role.ADMIN)
                .collect(Collectors.toList());

        ChatFeed adapter = new ChatFeed(this, filteredUsers);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersRecyclerView.setAdapter(adapter);
    }

}
