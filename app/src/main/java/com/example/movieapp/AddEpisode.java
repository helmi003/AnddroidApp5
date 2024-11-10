package com.example.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddEpisode extends AppCompatActivity {

    private ApplicationDatabase database;
    private static final int PICK_IMAGE = 1;
    Button addEpisode;
    EditText number;
    EditText video;
    ImageView backArrow;
    private int seasonID;
    private ImageView movieImageViewDisplay;
    Button selectImageButton;
    private Uri movieImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_episode);
        seasonID = getIntent().getIntExtra("seasonID", -1);
        movieImageViewDisplay = findViewById(R.id.movieImageViewDisplay);
        selectImageButton = findViewById(R.id.selectImageButton);
        database = ApplicationDatabase.getAppDatabase(this);
        addEpisode = findViewById(R.id.addEpisode);
        number = findViewById(R.id.number);
        video = findViewById(R.id.video);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });
        addEpisode.setOnClickListener(view -> {
            String numberText = number.getText().toString().trim();
            String videoText = video.getText().toString().trim();
            String imagePath = movieImageUri != null ? saveImageToInternalStorage(movieImageUri) : "";
            if (numberText.isEmpty() || videoText.isEmpty() || imagePath.isEmpty()) {
                Toast.makeText(AddEpisode.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }else{
                database.episodeDAO().createEpisode(new Episode(0,Integer.parseInt(numberText),imagePath,videoText,seasonID));
                Toast.makeText(AddEpisode.this, "Episode added successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
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