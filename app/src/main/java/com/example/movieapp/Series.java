package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movieapp.adaptater.SerieAdminAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Serie;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Series extends AppCompatActivity {

    private RecyclerView seriesRecyclerView;
    private ImageView backArrow;
    private ImageView plus;

    private final ActivityResultLauncher<Intent> addSeasonLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadSeries();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        seriesRecyclerView = findViewById(R.id.seriesRecyclerView);
        backArrow = findViewById(R.id.backArrow);
        plus = findViewById(R.id.plus);

        backArrow.setOnClickListener(v -> finish());
        plus.setOnClickListener(v -> {
            Intent intent = new Intent(Series.this, AddSerie.class);
            addSeasonLauncher.launch(intent);
        });

        loadSeries();
    }

    private void loadSeries() {
        List<Serie> series = ApplicationDatabase.getAppDatabase(this).serieDAO().getAllSeries();
        SerieAdminAdapter adapter = new SerieAdminAdapter(this, series);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        seriesRecyclerView.setLayoutManager(layoutManager);
        seriesRecyclerView.setAdapter(adapter);
    }

}
