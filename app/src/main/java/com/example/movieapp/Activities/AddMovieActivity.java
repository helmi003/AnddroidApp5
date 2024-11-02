package com.example.movieapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.AppDatabase;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.ActorMovieJoin;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.MovieCategory;
import com.example.movieapp.Models.MovieCategoryJoin;
import com.example.movieapp.R; // Adjust this import based on your package structure

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddMovieActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView movieImageView;
    private EditText movieTitleInput, descriptionEditText, releaseDateEditText;
    private RecyclerView actorsRecyclerView, categoriesRecyclerView;
    private Button selectImageButton, addMovieButton;
    private Uri imageUri;

    private List<Actor> selectedActors = new ArrayList<>();
    private List<MovieCategory> selectedCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie); // Adjust according to your layout

        movieImageView = findViewById(R.id.movieImageView);
        movieTitleInput = findViewById(R.id.movieTitleInput);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        releaseDateEditText = findViewById(R.id.releaseDateEditText);
        actorsRecyclerView = findViewById(R.id.actorsRecyclerView);
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        selectImageButton = findViewById(R.id.selectImageButton);
        addMovieButton = findViewById(R.id.addMovieButton);

        selectImageButton.setOnClickListener(v -> openFileChooser());

        addMovieButton.setOnClickListener(v -> addMovie());

        // Set up RecyclerViews for actors and categories
        setupRecyclerViews();
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                movieImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupRecyclerViews() {
        // Set up adapters for the RecyclerViews (You need to implement these adapters)
        // Example:
        // ActorAdapter actorAdapter = new ActorAdapter(this, selectedActors);
        // actorsRecyclerView.setAdapter(actorAdapter);

        // CategoryAdapter categoryAdapter = new CategoryAdapter(this, selectedCategories);
        // categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    private void addMovie() {
        String title = movieTitleInput.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String releaseDateString = releaseDateEditText.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || releaseDateString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setReleaseDate(parseReleaseDate(releaseDateString));
        movie.setImage(imageUri.toString()); // Save the image URI as a string

        // Save movie and handle relationships
        saveMovieToDatabase(movie);
    }

    private void saveMovieToDatabase(Movie movie) {
        AppDatabase database = AppDatabase.getInstance(this); // Ensure this is implemented in your AppDatabase

        // Launching a coroutine in the lifecycleScope
       // lifecycleScope.launch {
            // Insert the movie and get the generated ID
            long movieId = database.movieDao().insertMovie(movie); // Ensure this method returns long ID

            // Save selected actors to join table
            for (Actor actor : selectedActors) {
                database.actorMovieJoinDao().insert(new ActorMovieJoin(actor.getId(), (int) movieId));
            }

            // Save selected categories to join table
        for (MovieCategory category : selectedCategories) {
            database.movieCategoryJoinDao().insert(new MovieCategoryJoin((int) movieId, category.name())); // Convert enum to String
        }


        // Show success message on the main thread
            runOnUiThread(() -> {
                Toast.makeText(AddMovieActivity.this, "Movie added successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after adding the movie
            });
        //}
    }

    // Implement your date parsing logic here
    private Date parseReleaseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if needed
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parse exception as needed
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
