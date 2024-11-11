package com.example.movieapp;

import android.os.Bundle;
import android.util.Patterns;
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
import com.example.movieapp.entities.Serie;

public class AddSerie extends AppCompatActivity {

    private ApplicationDatabase database;
    Button addSerie;
    TextView title;
    TextView description;
    TextView star;
    ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);

        database = ApplicationDatabase.getAppDatabase(this);
        addSerie = findViewById(R.id.addSerie);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        star = findViewById(R.id.star);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        addSerie.setOnClickListener(view -> {
            String titleText = title.getText().toString().trim();
            String descriptionText = description.getText().toString().trim();
            String starText = star.getText().toString().trim();

            if (titleText.isEmpty() || descriptionText.isEmpty() || starText.isEmpty()) {
                Toast.makeText(AddSerie.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }else{
                database.serieDAO().createSerie(new Serie(0,titleText,descriptionText,Long.parseLong(starText)));
                Toast.makeText(AddSerie.this, "Serie added successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}