package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.adaptater.EpisodeAdapter;
import com.example.movieapp.adaptater.SeasonAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Season;
import com.example.movieapp.entities.Serie;

import java.util.List;


public class Streaming extends AppCompatActivity {

    private RecyclerView episodesRecyclerView;
    private RecyclerView seasonsRecyclerView;
    ImageView backArrow;
    ImageView cover;
    TextView title;
    TextView description;
    Serie serie;
    private ApplicationDatabase database;

    private ImageButton playButton;
    private int serieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);
        database = ApplicationDatabase.getAppDatabase(this);
        episodesRecyclerView = findViewById(R.id.episodesRecyclerView);
        seasonsRecyclerView = findViewById(R.id.seasonsRecyclerView);
        cover = findViewById(R.id.cover);
        serieID = getIntent().getIntExtra("serieID", -1);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        serie = database.serieDAO().getSerieById(serieID);
        title.setText(serie.getTitle());
        description.setText(serie.getDescription());
        Glide.with(this).load(serie.getImageUri()).into(cover);
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
                intent.putExtra("videoUrl", serie.getTrailler());
                startActivity(intent);
            }
        });
    }

    private void loadSeasons() {
        List<Season> seasons = database.seasonDAO().getAllSeasonsBySerie(serieID);
        SeasonAdapter adapter = new SeasonAdapter(seasons, seasonId -> loadEpisodes(seasonId), this);
        seasonsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        seasonsRecyclerView.setAdapter(adapter);
    }

    private void loadEpisodes(int seasonId) {
        List<Episode> episodes = database.episodeDAO().getAllEpisodessBySeason(seasonId);
        EpisodeAdapter adapter = new EpisodeAdapter(this, episodes, episode -> {
            Intent intent = new Intent(Streaming.this, FullScreenVideo.class);
            //intent.putExtra("videoUrl", episode.getVideo());
            intent.putExtra("videoUrl", episode.getVideo());
            startActivity(intent);
        });
        episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        episodesRecyclerView.setAdapter(adapter);
    }

}