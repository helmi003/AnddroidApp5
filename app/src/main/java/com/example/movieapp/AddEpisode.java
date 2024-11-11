package com.example.movieapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Season;

public class AddEpisode extends AppCompatActivity {

    private ApplicationDatabase database;
    Button addEpisode;
    EditText number;
    EditText image;
    EditText video;
    ImageView backArrow;
    private int seasonID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_episode);
        seasonID = getIntent().getIntExtra("seasonID", -1);
        database = ApplicationDatabase.getAppDatabase(this);
        addEpisode = findViewById(R.id.addEpisode);
        number = findViewById(R.id.number);
        video = findViewById(R.id.video);
        image = findViewById(R.id.image);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        addEpisode.setOnClickListener(view -> {
            String numberText = number.getText().toString().trim();
            String imageText = image.getText().toString().trim();
            String videoText = video.getText().toString().trim();

            if (numberText.isEmpty() || imageText.isEmpty() || videoText.isEmpty()) {
                Toast.makeText(AddEpisode.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }else{
                database.episodeDAO().createEpisode(new Episode(0,Integer.parseInt(numberText),imageText,videoText,seasonID));
                Toast.makeText(AddEpisode.this, "Episode added successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}