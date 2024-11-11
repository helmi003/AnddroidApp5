package com.example.movieapp.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movieapp.AppDatabase;
import com.example.movieapp.Models.MovieCategory;
import com.example.movieapp.Models.MovieCategoryJoin;
import com.example.movieapp.R;

public class MovieCategoryActivity extends AppCompatActivity {

    private LinearLayout categoryCheckboxContainer;
    private Button saveButton;
    private int movieId;  // This will store the selected movie's ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_category);

        categoryCheckboxContainer = findViewById(R.id.categoryCheckboxContainer);
        saveButton = findViewById(R.id.saveButton);

        movieId = getIntent().getIntExtra("movieId", -1);
        Log.d("MovieCategoryActivity", "Movie ID: " + movieId);  // Log the movie ID

        if (movieId != -1) {
            setupCategoryCheckboxes();

            saveButton.setOnClickListener(v -> {
                Log.d("MovieCategoryActivity", "Save button clicked");  // Log button click
                saveCategories();
            });
        }
    }

    private void setupCategoryCheckboxes() {
        // Dynamically create checkboxes for each MovieCategory
        for (MovieCategory category : MovieCategory.values()) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(category.name());
            checkBox.setTextColor(Color.WHITE);
            categoryCheckboxContainer.addView(checkBox);
        }
    }

    private void saveCategories() {
        Log.d("MovieCategoryActivity", "saveCategories() called"); // Check if method is called

        new Thread(() -> {
            AppDatabase database = AppDatabase.getInstance(this);

            for (int i = 0; i < categoryCheckboxContainer.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) categoryCheckboxContainer.getChildAt(i);
                if (checkBox.isChecked()) {
                    String category = checkBox.getText().toString();
                    Log.d("MovieCategoryActivity", "Attempting to attach category: " + category + " to movie ID: " + movieId);

                    if (!database.movieCategoryJoinDao().isCategoryAttachedToMovie(movieId, category)) {
                        MovieCategoryJoin join = new MovieCategoryJoin(movieId, category);
                        database.movieCategoryJoinDao().insert(join);
                        Log.d("MovieCategoryActivity", "Category " + category + " added to movie with ID " + movieId);
                    } else {
                        Log.d("MovieCategoryActivity", "Category " + category + " is already attached to movie with ID " + movieId);
                    }
                }
            }

            Log.d("MovieCategoryActivity", "Finished saving categories for movie ID " + movieId);
        }).start();

        finish();
    }
}
