package com.example.movieapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.movieapp.Models.Actor;
import com.example.movieapp.R;
import com.example.movieapp.database.ApplicationDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddActorActivity extends BaseActivity {
    private EditText actorName, actorBio;
    private ImageView actorProfileImage;
    private Uri imageUri;
    private ApplicationDatabase database;
    ImageView backArrow;

    private static final int PICK_IMAGE_REQUEST = 1; // Constant for image selection request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_actor);

        actorName = findViewById(R.id.actorName);
        actorBio = findViewById(R.id.actorBio);
        actorProfileImage = findViewById(R.id.actorProfileImage);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(view -> finish());
        // Get database instance
        database = ApplicationDatabase.getAppDatabase(this);

        // Set click listener for the add button
        findViewById(R.id.addActorButton).setOnClickListener(v -> addActorToDatabase());
    }

    // Method to choose an image from the gallery
    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();  // Get image Uri
            actorProfileImage.setImageURI(imageUri); // Set image to ImageView
        } else {
            Toast.makeText(this, "Image selection failed", Toast.LENGTH_SHORT).show(); // Show error message if image selection fails
        }
    }

    // Method to add actor data to Room database
    private void addActorToDatabase() {
        String name = actorName.getText().toString().trim();
        String bio = actorBio.getText().toString().trim();
        String profileImageUrl = (imageUri != null) ? saveImageToInternalStorage(imageUri) : "";

        if (!name.isEmpty() && !bio.isEmpty()) {
            // Create a new Actor object
            Actor actor = new Actor(name, bio, profileImageUrl);

            // Insert the actor data into the Room database asynchronously
            AsyncTask.execute(() -> {
                database.actorDao().insert(actor);
                runOnUiThread(() -> {
                    Toast.makeText(AddActorActivity.this, "Actor added successfully!", Toast.LENGTH_SHORT).show();

                    // Redirect to the actor list
                    Intent intent = new Intent(AddActorActivity.this, ActorListActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                });
            });
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    // Save image to internal storage and return the file path
    private String saveImageToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), "actor_" + System.currentTimeMillis() + ".jpg");
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
}
