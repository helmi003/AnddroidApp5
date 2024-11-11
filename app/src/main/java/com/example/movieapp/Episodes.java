package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.EpisodeAdminAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Episode;

import java.util.List;

public class Episodes extends AppCompatActivity {

    private RecyclerView episodesRecyclerView;
    private ImageView backArrow;
    private ImageView plus;
    private int seasonID;

    private final ActivityResultLauncher<Intent> addEpisodeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadEpisodes();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        episodesRecyclerView = findViewById(R.id.episodesRecyclerView);
        seasonID = getIntent().getIntExtra("seasonID", -1);
        backArrow = findViewById(R.id.backArrow);
        plus = findViewById(R.id.plus);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        plus.setOnClickListener(v -> {
            Intent intent = new Intent(Episodes.this, AddEpisode.class);
            intent.putExtra("seasonID", seasonID);
            addEpisodeLauncher.launch(intent);
        });
        loadEpisodes();
    }

    private void loadEpisodes() {
        List<Episode> episodes = ApplicationDatabase.getAppDatabase(this).episodeDAO().getAllEpisodessBySeason(seasonID);
        EpisodeAdminAdapter adapter = new EpisodeAdminAdapter(this, episodes);
        Log.d("salem",episodes.toString());
        episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        episodesRecyclerView.setAdapter(adapter);
    }
}