package com.example.movieapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.Adapters.CategoryListAdapter;
import com.example.movieapp.Adapters.FilmListAdapter;
import com.example.movieapp.Adapters.SliderAdapters;
import com.example.movieapp.AppDatabase;
import com.example.movieapp.Domain.SliderItems;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.MovieCategory;
import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Movies extends BaseActivity {
private RecyclerView.Adapter adapterBestMovies,adapterUpComing,adapterCategory;
private RecyclerView recyclerViewBestMovies,recyclerViewUpComing,recyclerViewCategory;
private ProgressBar loading1,loading2,loading3;
private ViewPager2 viewPager2;
private Handler slideHandler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movies);
        initView();
        banners();
        sendRequestBestMovies();
        sendRequestUpComing();
        sendRequestCategories();

        EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String movieTitle = searchEditText.getText().toString().trim();
                if (!movieTitle.isEmpty()) {
                    searchMovieByTitle(movieTitle);
                } else {
                    Toast.makeText(this, "Please enter a movie title", Toast.LENGTH_SHORT).show();
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    private void searchMovieByTitle(String title) {
        AsyncTask.execute(() -> {
            Log.d("Movies", "Searching for movie with title: " + title); // Log to ensure search logic is working
            // Query the movie database
            Movie movie = AppDatabase.getInstance(this).movieDao().getMovieByTitle(title);

            // Log the result for debugging
            if (movie != null) {
                Log.d("Movies", "Movie found: " + movie.getTitle());
            } else {
                Log.d("Movies", "No movie found with title: " + title);
            }

            runOnUiThread(() -> {
                if (movie != null) {
                    Intent intent = new Intent(Movies.this, DetailActivity.class);
                    intent.putExtra("id", movie.getId());
                    startActivity(intent);
                } else {
                    // If no movie is found, show a Toast
                    Toast.makeText(Movies.this, "No movie found with that title", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void sendRequestBestMovies() {
        loading1.setVisibility(View.VISIBLE);

        AsyncTask.execute(() -> {
            List<Movie> movies = AppDatabase.getInstance(this).movieDao().getAllMovies();

            runOnUiThread(() -> {
                if (movies != null && !movies.isEmpty()) {
                    setupBestMoviesRecyclerView(movies);
                } else {
                    // Handle case when there are no movies in the database
                }
                loading1.setVisibility(View.GONE);
            });
        });
    }
    private void sendRequestCategories() {
        loading2.setVisibility(View.VISIBLE);

        AsyncTask.execute(() -> {
            List<MovieCategory> categories = Arrays.asList(MovieCategory.values());

            runOnUiThread(() -> {
                if (categories != null && !categories.isEmpty()) {
                    setupCategoryRecyclerView(categories);
                } else {
                    Log.d("Categories", "No categories found.");
                }
                // Stop loading indicator
                loading2.setVisibility(View.GONE);
            });
        });
    }

    private void setupCategoryRecyclerView(List<MovieCategory> categories) {
        adapterCategory = new CategoryListAdapter(categories);

        recyclerViewCategory.setAdapter(adapterCategory);
    }

    private void sendRequestUpComing() {
        loading3.setVisibility(View.VISIBLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();

        AsyncTask.execute(() -> {
            List<Movie> upcomingMovies = AppDatabase.getInstance(this).movieDao().getUpcomingMovies(today);

            runOnUiThread(() -> {
                if (upcomingMovies != null && !upcomingMovies.isEmpty()) {
                    setupUpcomingMoviesRecyclerView(upcomingMovies);
                } else {
                    Log.d("Movies", "No upcoming movies found.");
                }
                loading3.setVisibility(View.GONE);
            });
        });
    }

    private void setupBestMoviesRecyclerView(List<Movie> movies) {
        adapterBestMovies = new FilmListAdapter(movies);
        recyclerViewBestMovies.setAdapter(adapterBestMovies);
    }
    private void setupUpcomingMoviesRecyclerView(List<Movie> movies) {
        adapterUpComing = new FilmListAdapter(movies);
        recyclerViewUpComing.setAdapter(adapterUpComing);
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));
        sliderItems.add(new SliderItems(R.drawable.wide));
        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
            }
        });
    }

    private  Runnable sliderRunnable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable,2000);
    }

    private void initView(){
        viewPager2=findViewById(R.id.viewpagerSlider);
        recyclerViewBestMovies=findViewById(R.id.View1);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewUpComing=findViewById(R.id.View3);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCategory=findViewById(R.id.View2);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        loading1=findViewById(R.id.progressBar1);
        loading2=findViewById(R.id.progressBar2);
        loading3=findViewById(R.id.progressBar3);

    }

}