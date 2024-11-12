package com.example.movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.MovieCategory;
import com.example.movieapp.Models.MovieCategoryJoin;
import com.example.movieapp.R;
import com.example.movieapp.SeatReservation;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.adaptater.ActorsListAdapter;
import com.example.movieapp.adaptater.CategoryListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends BaseActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, movieSummaryInfo;
    private int idFilm;
    private ImageView pic2, backArrow, plus;
    private RecyclerView.Adapter adapterActorList, adapterCategory;
    private RecyclerView recyclerViewActors, recyclerViewCategory;
    private ImageView addReservation;
    private Movie movie;
    private ApplicationDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        idFilm = getIntent().getIntExtra("id", -1);
        addReservation = findViewById(R.id.addReservation);
        titleTxt = findViewById(R.id.MovieNameTxt);
        progressBar = findViewById(R.id.progressBarDetail);
        pic2 = findViewById(R.id.picDetail);
        movieRateTxt = findViewById(R.id.MovieStar);
        movieTimeTxt = findViewById(R.id.MovieTime);
        movieSummaryInfo = findViewById(R.id.MovieSummary);
        backArrow = findViewById(R.id.backArrow);
        recyclerViewCategory = findViewById(R.id.categoryView);
        recyclerViewActors = findViewById(R.id.actorsRecyclerView);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        backArrow.setOnClickListener(v -> finish());
        addReservation.setOnClickListener(view -> {
            Intent intent = new Intent(DetailActivity.this, SeatReservation.class);
            intent.putExtra("movieID", idFilm);
            startActivity(intent);
        });
        database = ApplicationDatabase.getAppDatabase(this);
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);

        new Thread(() -> {
            movie = database.movieDAO().getMovieById(idFilm);

            if (movie != null) {
                Log.d("movie", movie.toString());
                List<Actor> actors = database.actorMovieJoinDao().getActorsForMovie(idFilm);
                List<MovieCategoryJoin> movieCategoryJoins = database.movieCategoryJoinDao().getCategoriesForMovie(idFilm);

                List<MovieCategory> categories = new ArrayList<>();
                for (MovieCategoryJoin join : movieCategoryJoins) {
                    MovieCategory category = MovieCategory.fromString(join.getCategoryId());
                    if (category != null) {
                        categories.add(category);
                    }
                }

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    displayMovieDetails(movie);

                    adapterActorList = new ActorsListAdapter(actors);
                    recyclerViewActors.setAdapter(adapterActorList);
                    adapterActorList.notifyDataSetChanged();

                    adapterCategory = new CategoryListAdapter(categories);
                    recyclerViewCategory.setAdapter(adapterCategory);
                    adapterCategory.notifyDataSetChanged();
                });
            } else {
                Log.e("DetailActivity", "Movie not found with ID: " + idFilm);
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    movieSummaryInfo.setText("Movie not found");
                });
            }
        }).start();
    }

    private void displayMovieDetails(Movie movie) {
        titleTxt.setText(movie.getTitle());
        movieRateTxt.setText("N/A");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        movieTimeTxt.setText(movie.getReleaseDate() != null ? dateFormat.format(movie.getReleaseDate()) : "--");
        movieSummaryInfo.setText(movie.getDescription());
        Glide.with(this).load(movie.getImageUri()).into(pic2);
    }
}
