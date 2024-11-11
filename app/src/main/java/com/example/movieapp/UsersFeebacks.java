package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.UserFeed;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersFeebacks extends AppCompatActivity {

    private ApplicationDatabase database;
    private RecyclerView recyclerViewUsers;
    private UserFeed userAdapter;
    private FirebaseFirestore db;
    private List<User> userList;
    User user;
    private Set<String> userIdsSet;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_feebacks);

        database = ApplicationDatabase.getAppDatabase(this);
        recyclerViewUsers = findViewById(R.id.feedsRecyclerView);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        userAdapter = new UserFeed(userList, this::onUserClick);
        recyclerViewUsers.setAdapter(userAdapter);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finish());
        db = FirebaseFirestore.getInstance();

        userIdsSet = new HashSet<>(); // Initialize the set
        loadUsersFromChats(); // Load users from chats
    }

    private void loadUsersFromChats() {
        CollectionReference chatsCollection = db.collection("chats");
        chatsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String userId = document.getString("userId");
                    if (userId != null && !userIdsSet.contains(userId)) {
                        userIdsSet.add(userId);
                        loadUserDetails(userId);
                    }
                }
            } else {
                Log.w("UsersFeebacks", "Error getting chat documents.", task.getException());
            }
        });
    }

    private void loadUserDetails(String userId) {
        user = database.userDAO().getUserById(userId);
        if (user != null) {
            userList.add(user);
            userAdapter.notifyDataSetChanged();
        } else {
            Log.w("UsersFeebacks", "User not found for userId: " + userId);
        }
    }


    private void onUserClick(User user) {
        Intent intent = new Intent(UsersFeebacks.this, Support.class);
        intent.putExtra("userId", user.getId());
        startActivity(intent);
    }
}
