package com.example.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.EpisodeAdapter;
import com.example.movieapp.adaptater.SeasonAdapter;
import com.example.movieapp.adaptater.SerieAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Season;
import com.example.movieapp.entities.Serie;

import java.util.ArrayList;
import java.util.List;


public class Streaming extends AppCompatActivity {

    private RecyclerView episodesRecyclerView;
    private RecyclerView seasonsRecyclerView;
    ImageView backArrow;
    private ImageButton playButton;
    int serieId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);
        episodesRecyclerView = findViewById(R.id.episodesRecyclerView);
        seasonsRecyclerView = findViewById(R.id.seasonsRecyclerView);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        loadSeasons();
        loadEpisodes(1);

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Streaming.this, FullScreenVideo.class);
                intent.putExtra("videoUrl", "https://www.youtube.com/watch?v=jac53THxO0I");
                startActivity(intent);
            }
        });
    }

    private void loadSeasons() {
        List<Season> seasons = ApplicationDatabase.getAppDatabase(this).seasonDAO().getAllSeasonsBySerie(serieId);
        SeasonAdapter adapter = new SeasonAdapter(seasons, seasonId -> loadEpisodes(seasonId), this);
        seasonsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        seasonsRecyclerView.setAdapter(adapter);
    }

    private void loadEpisodes(int seasonId) {
        List<Episode> episodes = ApplicationDatabase.getAppDatabase(this).episodeDAO().getAllEpisodessBySeason(seasonId);
        EpisodeAdapter adapter = new EpisodeAdapter(this, episodes, episode -> {
            Intent intent = new Intent(Streaming.this, FullScreenVideo.class);
            //intent.putExtra("videoUrl", episode.getVideo());
            intent.putExtra("videoUrl", "https://www.youtube.com/watch?v=jac53THxO0I");
            startActivity(intent);
        });
        episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        episodesRecyclerView.setAdapter(adapter);
    }

}