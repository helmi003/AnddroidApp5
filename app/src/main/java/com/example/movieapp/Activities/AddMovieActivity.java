package com.example.movieapp.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.MultiSelectAdapter;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.MovieCategory;
import com.example.movieapp.R;
import com.example.movieapp.database.ApplicationDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddMovieActivity extends BaseActivity {
    private static final int PICK_IMAGE = 1;

    private ImageView movieImageViewDisplay;
    private EditText releaseDateEditText;
    private RecyclerView actorsRecyclerView;
    private RecyclerView categoriesRecyclerView;
    private MultiSelectAdapter<Actor> actorAdapter;
    private MultiSelectAdapter<MovieCategory> categoryAdapter;

    private List<Actor> actorList = new ArrayList<>();
    private List<Actor> selectedActors = new ArrayList<>();
    private List<MovieCategory> categoryList = Arrays.asList(MovieCategory.values());
    private List<MovieCategory> selectedCategories = new ArrayList<>();
    private Uri movieImageUri;
    private ApplicationDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // Initialize database instance
        database = ApplicationDatabase.getAppDatabase(this);

        // Set up UI elements
        movieImageViewDisplay = findViewById(R.id.movieImageViewDisplay);
        releaseDateEditText = findViewById(R.id.releaseDateEditText);
        Button selectImageButton = findViewById(R.id.selectImageButton);
        Button addMovieButton = findViewById(R.id.addMovieButton);
        actorsRecyclerView = findViewById(R.id.actorsRecyclerView);
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);

        // Set up RecyclerViews with LayoutManagers
        actorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Set<Actor> selectedActors = new HashSet<>(); // For actors
        Set<MovieCategory> selectedCategories = new HashSet<>(); // For categories

        // Set up actor and category adapters
        actorAdapter = new MultiSelectAdapter<>(actorList, Actor::getName);
        actorsRecyclerView.setAdapter(actorAdapter);

        categoryAdapter = new MultiSelectAdapter<>(categoryList, MovieCategory::name);
        categoriesRecyclerView.setAdapter(categoryAdapter);

        // Load actors from database
        loadActorsFromDatabase();

        // Set up image selection button
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        // Set up date picker for release date
        releaseDateEditText.setOnClickListener(v -> showDatePickerDialog());
        // Set up button to save a new movie
        addMovieButton.setOnClickListener(v -> saveMovie());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddMovieActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    releaseDateEditText.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void loadActorsFromDatabase() {
        AsyncTask.execute(() -> {
            actorList.clear();

            List<Actor> actors = database.actorDao().getAllActors();
            actorList.addAll(actors);

            // Log for debugging
            Log.d("AddMovieActivity", "Loaded actors: " + actors.size());

            runOnUiThread(() -> actorAdapter.notifyDataSetChanged());
        });
    }


    private void saveMovie() {
        // Log selected items from actor adapter

        Log.d("AddMovieActivity", "Selected Actors: " + actorAdapter.getSelectedItems());

        // Log selected items from category adapter
        Log.d("AddMovieActivity", "Selected Categories: " + categoryAdapter.getSelectedItems());

        String title = ((EditText) findViewById(R.id.movieTitleInput)).getText().toString();
        String description = ((EditText) findViewById(R.id.descriptionEditText)).getText().toString();
        String releaseDate = releaseDateEditText.getText().toString();
        String imagePath = movieImageUri != null ? saveImageToInternalStorage(movieImageUri) : "";

        // Get selected actor IDs from the actor adapter
        List<Integer> actorIds = new ArrayList<>();
        for (Actor actor : actorAdapter.getSelectedItems()) {
            actorIds.add(actor.getId());
        }

        // Get selected categories from the category adapter
        List<String> categories = new ArrayList<>();
        for (MovieCategory category : categoryAdapter.getSelectedItems()) {
            categories.add(category.name());
        }

        // Create a new movie instance with categories
        List<String> emptyList = new ArrayList<>();
        Movie newMovie = new Movie(title, description, releaseDate, imagePath, actorIds, categories,emptyList);

        // Save the movie in the database
        AsyncTask.execute(() -> {
            database.movieDAO().insertMovie(newMovie);

            // Log the movie details after saving
            Log.d("AddMovieActivity", "Movie added: ");
            Log.d("AddMovieActivity", "Title: " + newMovie.getTitle());
            Log.d("AddMovieActivity", "Description: " + newMovie.getDescription());
            Log.d("AddMovieActivity", "Release Date: " + newMovie.getReleaseDate());
            Log.d("AddMovieActivity", "Image Path: " + newMovie.getImageUri());
            Log.d("AddMovieActivity", "Actor IDs: " + actorIds.toString());
            Log.d("AddMovieActivity", "Categories: " + categories.toString());

            runOnUiThread(() -> {
                Toast.makeText(AddMovieActivity.this, "Movie added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
    private String saveImageToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), "movie_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return file.getAbsolutePath(); // Return the file path for the image
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            movieImageUri = data.getData();  // Store the selected image URI
            movieImageViewDisplay.setImageURI(movieImageUri); // Set image to ImageView
        }
    }
}
