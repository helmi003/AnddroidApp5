package com.example.movieapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class AddMovieByHelmi extends AppCompatActivity {

    private ApplicationDatabase database;
    Button addMovie;
    EditText title;
    ImageView backArrow;
    private int seasonID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_by_helmi);
        seasonID = getIntent().getIntExtra("seasonID", -1);
        database = ApplicationDatabase.getAppDatabase(this);
        addMovie = findViewById(R.id.addMovie);
        title = findViewById(R.id.title);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        addMovie.setOnClickListener(view -> {
            String titleText = title.getText().toString().trim();

            if (titleText.isEmpty()) {
                Toast.makeText(AddMovieByHelmi.this, "Title is required.", Toast.LENGTH_SHORT).show();
                return;
            }else{
                List<String> emptyList = new ArrayList<>();
                database.movieDAO().createMovie(new Movie(0, titleText, emptyList));
                Toast.makeText(AddMovieByHelmi.this, "Movie added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}