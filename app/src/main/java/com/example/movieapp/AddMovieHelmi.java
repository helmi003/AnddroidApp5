package com.example.movieapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapp.Activities.AddMovieActivity;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Serie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddMovieHelmi extends AppCompatActivity {

    private ApplicationDatabase database;
    private static final int PICK_IMAGE = 1;
    Button addMovie;
    TextView title;
    TextView description;
    TextView release;
    ImageView backArrow;
    Button selectImageButton;
    private ImageView movieImageViewDisplay;
    private Uri movieImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_helmi);
        database = ApplicationDatabase.getAppDatabase(this);
        addMovie = findViewById(R.id.addMovie);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        release = findViewById(R.id.release);
        backArrow = findViewById(R.id.backArrow);
        movieImageViewDisplay = findViewById(R.id.movieImageViewDisplay);
        selectImageButton = findViewById(R.id.selectImageButton);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });
        release.setOnClickListener(v -> showDatePickerDialog());
        addMovie.setOnClickListener(view -> {
            String titleText = title.getText().toString().trim();
            String descriptionText = description.getText().toString().trim();
            String releaseDate = release.getText().toString().trim();
            String imagePath = movieImageUri != null ? saveImageToInternalStorage(movieImageUri) : "";
            if(descriptionText.isEmpty()|| titleText.isEmpty() || releaseDate.isEmpty() || imagePath.isEmpty()){
                Toast.makeText(AddMovieHelmi.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }else{
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    database.movieDAO().insertMovie(new Movie(titleText, descriptionText, releaseDate, imagePath));
                    runOnUiThread(() -> {
                        Toast.makeText(AddMovieHelmi.this, "Movie added successfully", Toast.LENGTH_SHORT).show();
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddMovieHelmi.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    release.setText(date);
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