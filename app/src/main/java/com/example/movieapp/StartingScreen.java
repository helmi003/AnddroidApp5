package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartingScreen extends AppCompatActivity {

    Button starting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);
        starting = findViewById(R.id.starting);
        starting.setOnClickListener(view -> {
            Intent intent = new Intent(StartingScreen.this,Login.class);
            startActivity(intent);
            finish();
        });
    }
}