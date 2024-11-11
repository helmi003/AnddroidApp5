package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.SeasonAdapter;
import com.example.movieapp.adaptater.SeasonAdminAdapter;
import com.example.movieapp.adaptater.SerieAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Season;
import com.example.movieapp.entities.Serie;

import java.util.List;

public class Seasons extends AppCompatActivity {

    private RecyclerView seasonsRecyclerView;
    private ImageView backArrow;
    private ImageView plus;
    private int serieID;

    private final ActivityResultLauncher<Intent> addSeasonLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadSeasons(); // Refresh the list of seasons
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);
        seasonsRecyclerView = findViewById(R.id.seasonsRecyclerView);
        serieID = getIntent().getIntExtra("serieID", -1);
        backArrow = findViewById(R.id.backArrow);
        plus = findViewById(R.id.plus);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        plus.setOnClickListener(v -> {
            Intent intent = new Intent(Seasons.this, AddSeason.class);
            intent.putExtra("serieID", serieID);
            addSeasonLauncher.launch(intent);
        });
        loadSeasons();
    }

    private void loadSeasons() {
        List<Season> seasons = ApplicationDatabase.getAppDatabase(this).seasonDAO().getAllSeasonsBySerie(serieID);
        SeasonAdminAdapter adapter = new SeasonAdminAdapter(this, seasons);
        seasonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        seasonsRecyclerView.setAdapter(adapter);
    }
}