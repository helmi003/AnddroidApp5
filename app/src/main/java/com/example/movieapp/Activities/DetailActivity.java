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
import com.example.movieapp.DAO.ActorMovieJoinDao;
import com.example.movieapp.DAO.MovieCategoryJoinDao;
import com.example.movieapp.DAO.MovieDAO;
import com.example.movieapp.SeatReservation;
import com.example.movieapp.adaptater.ActorsListAdapter;
import com.example.movieapp.adaptater.CategoryListAdapter;
import com.example.movieapp.database.ApplicationDatabase;

import java.util.List;

public class DetailActivity extends BaseActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt,movieRateTxt,movieTimeTxt,movieSummaryInfo,movieActorsInfo;
    private int idFilm;
    private ImageView pic2,backImg;
    private RecyclerView.Adapter adapterActorList,adapterCategory;
    private RecyclerView recyclerViewActors,recyclerViewCategory;
    private NestedScrollView scrollView;
    private MovieDAO movieDao;
    private ActorMovieJoinDao actorMovieJoinDao;
    private MovieCategoryJoinDao movieCategoryJoinDao;
    private ApplicationDatabase database;
    Button addReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        database = ApplicationDatabase.getAppDatabase(this);
        idFilm = getIntent().getIntExtra("id",0);
        addReservation = findViewById(R.id.addReservation);
        Log.d("id film", String.valueOf(idFilm));
        addReservation.setOnClickListener(view -> {
            Intent intent = new Intent(DetailActivity.this, SeatReservation.class);
            intent.putExtra("movieID", idFilm);
            startActivity(intent);
        });
        initView();

        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        movieDao = database.movieDAO();
        actorMovieJoinDao = database.actorMovieJoinDao();
        movieCategoryJoinDao = database.movieCategoryJoinDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Fetch the movie from the database in a background thread
                Movie movie = movieDao.getMovieById(idFilm);
                List<Actor> actors = actorMovieJoinDao.getActorsForMovie(idFilm);
                List<MovieCategoryJoin> movieCategoryJoins = movieCategoryJoinDao.getCategoriesForMovie(idFilm);
                List<MovieCategory> categories = movieCategoryJoinDao.getCategoriesAsEnums(movieCategoryJoins);

// Log the size of the lists to check if they are populated
                Log.d("DetailActivity", "Actors size: " + actors.size());
                Log.d("DetailActivity", "Categories size: " + categories.size());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        recyclerViewCategory.setVisibility(View.VISIBLE);

                        // Display the movie details
                        if (movie != null) {
                            displayMovieDetails(movie);

                            adapterActorList = new ActorsListAdapter(actors);
                            recyclerViewActors.setAdapter(adapterActorList);
                            adapterActorList.notifyDataSetChanged();

                            adapterCategory = new CategoryListAdapter(categories);
                            recyclerViewCategory.setAdapter(adapterCategory);
                            adapterCategory.notifyDataSetChanged();
                        } else {
                            movieSummaryInfo.setText("Movie not found");
                        }
                    }
                });
            }
        }).start();
    }

    private void displayMovieDetails(Movie movie) {
        titleTxt.setText(movie.getTitle());
        movieRateTxt.setText("null");
        movieTimeTxt.setText(movie.getReleaseDate().toString());  // Format the date properly if needed
        movieSummaryInfo.setText(movie.getDescription());

        // Use Glide to load the movie image into ImageView
        Glide.with(this).load(movie.getImageUri()).into(pic2);
    }
    private void initView() {
        titleTxt=findViewById(R.id.MovieNameTxt);
        progressBar=findViewById(R.id.progressBarDetail);
        scrollView=findViewById(R.id.scrollView2);
        pic2=findViewById(R.id.picDetail);
        movieRateTxt=findViewById(R.id.MovieStar);
        movieTimeTxt=findViewById(R.id.MovieTime);
        movieSummaryInfo=findViewById(R.id.MovieSummary);
        movieActorsInfo=findViewById(R.id.movieActorInfo);
        backImg=findViewById(R.id.backImg);
        recyclerViewCategory=findViewById(R.id.categoryView);
        recyclerViewActors=findViewById(R.id.actorsView);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        backImg.setOnClickListener(v -> finish());


    }
}