package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profil extends AppCompatActivity {

    TextView email;
    TextView username;
    TextView phone;
    Button logout;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    User user;
    private ApplicationDatabase database;

    @Override
    public void onStart() {
        super.onStart();

        if (auth != null) {
            currentUser = auth.getCurrentUser();
            if (currentUser == null) {
                Intent intent = new Intent(Profil.this, Login.class);
                startActivity(intent);
            } else {
                user = database.userDAO().getUserByEmail(currentUser.getEmail());
                username.setText(user.getUsername());
                phone.setText(user.getPhone().toString());
                email.setText(user.getEmail());
            }
        }else{
            Intent intent = new Intent(Profil.this, Login.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        auth = FirebaseAuth.getInstance();
        database = ApplicationDatabase.getAppDatabase(this);

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(view -> {
            auth.getInstance().signOut();
            Intent intent = new Intent(Profil.this,Login.class);
            startActivity(intent);
        });

    }
}