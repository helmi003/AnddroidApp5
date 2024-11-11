package com.example.movieapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.AddMovieHelmi;
import com.example.movieapp.AppDatabase;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.R;
import com.example.movieapp.adaptater.EpisodeAdminAdapter;
import com.example.movieapp.adaptater.MovieAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Episode;

import java.util.List;

public class MovieList extends BaseActivity {

    private RecyclerView moviesRecyclerView;
    private ImageView backArrow;
    private ImageView plus;

    private final ActivityResultLauncher<Intent> addMovieLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadMovies();
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        backArrow = findViewById(R.id.backArrow);
        plus = findViewById(R.id.plus);
        backArrow.setOnClickListener(v -> finish());
        plus.setOnClickListener(v -> {
            Intent intent = new Intent(MovieList.this, AddMovieHelmi.class);
            addMovieLauncher.launch(intent);
        });
        loadMovies();
    }

    private void loadMovies() {
        List<Movie> movies = ApplicationDatabase.getAppDatabase(this).movieDAO().getAllMovies();
        MovieAdapter adapter = new MovieAdapter(this, movies);
        Log.d("salem",movies.toString());
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moviesRecyclerView.setAdapter(adapter);
    }
}
