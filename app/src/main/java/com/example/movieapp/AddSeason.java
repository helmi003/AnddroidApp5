package com.example.movieapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Season;
import com.example.movieapp.entities.Serie;

public class AddSeason extends AppCompatActivity {

    private ApplicationDatabase database;
    Button addSeason;
    TextView number;
    ImageView backArrow;
    private int serieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_season);
        serieID = getIntent().getIntExtra("serieID", -1);
        database = ApplicationDatabase.getAppDatabase(this);
        addSeason = findViewById(R.id.addSeason);
        number = findViewById(R.id.number);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        addSeason.setOnClickListener(view -> {
            String numberText = number.getText().toString().trim();

            if (numberText.isEmpty()) {
                Toast.makeText(AddSeason.this, "You must add a season number.", Toast.LENGTH_SHORT).show();
                return;
            }else{
                database.seasonDAO().createSeason(new Season(0,Integer.parseInt(numberText),serieID));
                Toast.makeText(AddSeason.this, "Serie added successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}