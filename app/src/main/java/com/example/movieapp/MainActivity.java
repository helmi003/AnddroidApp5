package com.example.movieapp;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.movieapp.Activities.AddMovieActivity;
import com.example.movieapp.Domain.SliderItems;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.MovieCategory;
import com.example.movieapp.adaptater.CategoryListAdapter;
import com.example.movieapp.adaptater.FilmListAdapter;
import com.example.movieapp.adaptater.SliderAdapters;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Role;
import com.example.movieapp.entities.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView navView;
    NavigationView navigationView;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    User user;
    ImageView profil;
    ImageView watchSerie;
    private RecyclerView.Adapter adapterBestMovies,adapterUpComing,adapterCategory;
    private RecyclerView recyclerViewBestMovies,recyclerViewUpComing,recyclerViewCategory;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest,mStringRequest2,mStringRequest3;
    private ProgressBar loading1,loading2,loading3;
    private ViewPager2 viewPager2;
    private Handler slideHandler= new Handler();
    private ApplicationDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        database = ApplicationDatabase.getAppDatabase(this);
        user = database.userDAO().getUserById(currentUser.getUid());
        initView();
        banners();
        sendRequestBestMovies();
        sendRequestUpComing();
        sendRequestCategories();
        drawerLayout = findViewById(R.id.drawer_layout);
        watchSerie = findViewById(R.id.watchSerie);
        navView = findViewById(R.id.menuIcon);
        profil = findViewById(R.id.profil);
        navigationView = findViewById(R.id.nav_view);
        watchSerie.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,WatchSeries.class);
            startActivity(intent);
        });
        navView.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        profil.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,Profil.class);
            startActivity(intent);
        });
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_support) {
                if(user.role== Role.ADMIN){
                    Intent intent = new Intent(MainActivity.this,UsersFeebacks.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this,Support.class);
                    startActivity(intent);
                }

            } else if (id == R.id.nav_users) {
                Intent intent = new Intent(MainActivity.this, Users.class);
                startActivity(intent);
            } else if (id == R.id.nav_reservations) {
                Intent intent = new Intent(MainActivity.this, Reservations.class);
                startActivity(intent);
            } else if (id == R.id.nav_series) {
                Intent intent = new Intent(MainActivity.this, Series.class);
                startActivity(intent);
            } else if (id == R.id.nav_my_reservations) {
                Intent intent = new Intent(MainActivity.this, MyReservations.class);
                startActivity(intent);
            } else if (id == R.id.nav_add_movie) {
                Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                auth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,StartingScreen.class);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }
    private void sendRequestBestMovies() {
        // Start loading indicator
        loading1.setVisibility(View.VISIBLE);

        // Use a background thread to fetch movies from the database
        AsyncTask.execute(() -> {
            // Retrieve all movies from the database
            List<Movie> movies = database.movieDAO().getAllMovies();

            // Update UI on the main thread after fetching data
            runOnUiThread(() -> {
                if (movies != null && !movies.isEmpty()) {
                    setupBestMoviesRecyclerView(movies);
                } else {
                    // Handle case when there are no movies in the database
                }
                // Stop loading indicator
                loading1.setVisibility(View.GONE);
            });
        });
    }
    private void sendRequestCategories() {
        // Start loading indicator
        loading2.setVisibility(View.VISIBLE);

        AsyncTask.execute(() -> {
            // Retrieve all categories from the MovieCategory enum
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
        // Initialize the adapter with the list of categories
        adapterCategory = new CategoryListAdapter(categories);

        // Set the adapter to the RecyclerView
        recyclerViewCategory.setAdapter(adapterCategory);
    }

    private void sendRequestUpComing() {
        // Start loading indicator
        loading3.setVisibility(View.VISIBLE);

        // Get today's date and set time to 00:00:00 (midnight)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();

        // Use a background thread to fetch upcoming movies from the database
        AsyncTask.execute(() -> {
            // Retrieve upcoming movies from the database
            List<Movie> upcomingMovies = database.movieDAO().getUpcomingMovies(today);

            // Update UI on the main thread after fetching data
            runOnUiThread(() -> {
                if (upcomingMovies != null && !upcomingMovies.isEmpty()) {
                    setupUpcomingMoviesRecyclerView(upcomingMovies);
                } else {
                    Log.d("Movies", "No upcoming movies found.");
                }
                // Stop loading indicator
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