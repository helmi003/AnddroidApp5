package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profil extends AppCompatActivity {

    TextView email;
    TextView username;
    TextView phone;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    User user;
    ImageView backArrow;
    private ApplicationDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // Initialize FirebaseAuth and Database
        auth = FirebaseAuth.getInstance();
        database = ApplicationDatabase.getAppDatabase(this);
        currentUser = auth.getCurrentUser();

        // Initialize views
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        backArrow = findViewById(R.id.backArrow);

        // Load user data
        if (currentUser != null) {
            user = database.userDAO().getUserById(currentUser.getUid());
            username.setText(user.getUsername());
            phone.setText(user.getPhone().toString());
            email.setText(user.getEmail());
        }

        backArrow.setOnClickListener(v -> {
            finish();
        });
    }
}
