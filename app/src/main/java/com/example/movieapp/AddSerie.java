package com.example.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddSerie extends AppCompatActivity {

    private ApplicationDatabase database;
    private static final int PICK_IMAGE = 1;
    Button addSerie;
    TextView title;
    TextView description;
    TextView star;
    ImageView backArrow;
    Button selectImageButton;
    private ImageView movieImageViewDisplay;
    private Uri movieImageUri;
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
        movieImageViewDisplay = findViewById(R.id.movieImageViewDisplay);
        selectImageButton = findViewById(R.id.selectImageButton);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });
        addSerie.setOnClickListener(view -> {
            String titleText = title.getText().toString().trim();
            String descriptionText = description.getText().toString().trim();
            String starText = star.getText().toString().trim();
            String imagePath = movieImageUri != null ? saveImageToInternalStorage(movieImageUri) : "";
            if (titleText.isEmpty() || descriptionText.isEmpty() || starText.isEmpty() || imagePath.isEmpty()) {
                Toast.makeText(AddSerie.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }else{
                database.serieDAO().createSerie(new Serie(0,titleText,descriptionText,Long.parseLong(starText),imagePath));
                Toast.makeText(AddSerie.this, "Serie added successfully", Toast.LENGTH_SHORT).show();
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