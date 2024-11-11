package com.example.movieapp.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.AppDatabase;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddMovieActivity extends BaseActivity {
    private static final int PICK_IMAGE = 1;
    private ImageView movieImageViewDisplay;
    private EditText releaseDateEditText;
    private Uri movieImageUri;
    private AppDatabase database;
    EditText movieTitleInput;
    EditText descriptionEditText;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        database = AppDatabase.getInstance(this);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(view -> finish());
        movieImageViewDisplay = findViewById(R.id.movieImageViewDisplay);
        releaseDateEditText = findViewById(R.id.releaseDateEditText);
        movieTitleInput = findViewById(R.id.movieTitleInput);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        Button selectImageButton = findViewById(R.id.selectImageButton);
        Button addMovieButton = findViewById(R.id.addMovieButton);

        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        releaseDateEditText.setOnClickListener(v -> showDatePickerDialog());

        addMovieButton.setOnClickListener(v -> {
            String descriptionText = descriptionEditText.getText().toString().trim();
            String titleText = movieTitleInput.getText().toString().trim();
            String releaseDate = releaseDateEditText.getText().toString();
            String imagePath = movieImageUri != null ? saveImageToInternalStorage(movieImageUri) : "";

            if (descriptionText.isEmpty() || titleText.isEmpty() || releaseDate.isEmpty() || imagePath.isEmpty()) {
                Toast.makeText(AddMovieActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    Movie movie = new Movie(titleText, descriptionText, releaseDate, imagePath);
                    database.movieDao().insertMovie(movie);
                    runOnUiThread(() -> {
                        Toast.makeText(AddMovieActivity.this, "Movie added successfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    });
                });
            }
        });
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
