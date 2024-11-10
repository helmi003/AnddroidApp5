package com.example.movieapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapp.Domain.SliderItems;
import com.example.movieapp.adaptater.SerieAdapter;
import com.example.movieapp.adaptater.SliderAdapters;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Serie;

import java.util.ArrayList;
import java.util.List;

public class WatchSeries extends AppCompatActivity {

    private RecyclerView seriesRecyclerView;
    private ImageView backArrow;
    private ViewPager2 viewpagerSlider;
    private Handler slideHandler= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_watch_series);

        // Initialize viewpagerSlider before calling banners()
        viewpagerSlider = findViewById(R.id.viewpagerSlider);

        // Other initializations
        seriesRecyclerView = findViewById(R.id.seriesRecyclerView);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finish());
        loadSeries();
        banners();
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));
        sliderItems.add(new SliderItems(R.drawable.wide));

        // Now you can safely set the adapter
        viewpagerSlider.setAdapter(new SliderAdapters(sliderItems, viewpagerSlider));

        viewpagerSlider.setClipToPadding(false);
        viewpagerSlider.setClipChildren(false);
        viewpagerSlider.setOffscreenPageLimit(3);
        viewpagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewpagerSlider.setPageTransformer(compositePageTransformer);
        viewpagerSlider.setCurrentItem(1);

        viewpagerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
            viewpagerSlider.setCurrentItem(viewpagerSlider.getCurrentItem()+1);
        }
    };

    private void loadSeries() {
        List<Serie> series = ApplicationDatabase.getAppDatabase(this).serieDAO().getAllSeries();
        SerieAdapter adapter = new SerieAdapter(this, series);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        seriesRecyclerView.setLayoutManager(layoutManager);
        seriesRecyclerView.setAdapter(adapter);
    }

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
}