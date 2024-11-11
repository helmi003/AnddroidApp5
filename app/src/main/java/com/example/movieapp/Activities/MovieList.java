package com.example.movieapp.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieapp.AppDatabase;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.R;
import com.example.movieapp.adaptater.MovieAdapter;

import java.util.List;

public class MovieList extends BaseActivity {

    private RecyclerView moviesRecyclerView;
    private ImageView backArrow;
    private ImageView plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);

        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        backArrow = findViewById(R.id.backArrow);
        plus = findViewById(R.id.plus);

        backArrow.setOnClickListener(v -> finish());
        plus.setOnClickListener(v -> {
            // Implement adding new movie functionality if required
        });

        loadMovies();
    }

    private void loadMovies() {
        new AsyncTask<Void, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(MovieList.this);
                return database.movieDao().getAllMovies();
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                MovieAdapter adapter = new MovieAdapter(MovieList.this, movies);
                moviesRecyclerView.setLayoutManager(new LinearLayoutManager(MovieList.this));
                moviesRecyclerView.setAdapter(adapter);
            }
        }.execute();
    }
}
