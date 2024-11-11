package com.example.movieapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.R;
import com.example.movieapp.DAO.ActorDao;
import com.example.movieapp.database.ApplicationDatabase;

public class EditActorActivity extends AppCompatActivity {
    private EditText editActorName, editActorBio;
    private ImageView editActorProfileImage;
    private Button saveButton;

    private ApplicationDatabase database;
    private ActorDao actorDao;
    private Actor actor; // Actor object for Room
    private int actorKey;
    ImageView backArrow;

    private static final int PICK_IMAGE_REQUEST = 1; // Request code for picking an image
    private Uri imageUri; // To store the image URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_actor);

        editActorName = findViewById(R.id.editActorName);
        editActorBio = findViewById(R.id.editActorBio);
        editActorProfileImage = findViewById(R.id.editActorProfileImage);
        saveButton = findViewById(R.id.saveButton);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(view -> finish());
        // Initialize Room database
        database = ApplicationDatabase.getAppDatabase(this);
        actorDao = database.actorDao();

        // Get the actor data passed via intent
        Intent intent = getIntent();
        actorKey = intent.getIntExtra("ACTOR_ID", -1); // Actor ID passed as int
        String actorName = intent.getStringExtra("ACTOR_NAME");
        String actorBio = intent.getStringExtra("ACTOR_BIO");
        String actorImageUrl = intent.getStringExtra("ACTOR_IMAGE_URL");

        // Set the data to EditText fields
        editActorName.setText(actorName);
        editActorBio.setText(actorBio);

        // Load actor image if available (use Glide if you're loading from a URL, or a local URI)
        if (actorImageUrl != null) {
            Glide.with(this).load(actorImageUrl).into(editActorProfileImage);
        }

        // Load the actor from the Room database
        loadActorFromDatabase(actorKey);

        // Set button click listeners
        saveButton.setOnClickListener(view -> updateActor());
    }

    private void loadActorFromDatabase(int actorKey) {
        AsyncTask.execute(() -> {
            actor = actorDao.getActorById(actorKey); // Fetch the actor by key (ID)
            if (actor != null) {
                runOnUiThread(() -> {
                    editActorName.setText(actor.getName());
                    editActorBio.setText(actor.getBio());
                    // Load the local actor image URI if available
                    if (actor.getProfileImageUrl() != null) {
                        Glide.with(this).load(actor.getProfileImageUrl()).into(editActorProfileImage);
                    }
                });
            }
        });
    }

    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Get the selected image URI
            editActorProfileImage.setImageURI(imageUri); // Display the selected image
        }
    }

    private void updateActor() {
        String name = editActorName.getText().toString().trim();
        String bio = editActorBio.getText().toString().trim();

        if (!name.isEmpty() && !bio.isEmpty()) {
            // Check if a new image was selected
            String imageUrl = (imageUri != null) ? imageUri.toString() : actor.getProfileImageUrl();

            // Log the values before updating
            Log.d("EditActorActivity", "Updating actor:");
            Log.d("EditActorActivity", "Name: " + name);
            Log.d("EditActorActivity", "Bio: " + bio);
            Log.d("EditActorActivity", "Image URL: " + imageUrl);

            actor.setName(name);
            actor.setBio(bio);
            actor.setProfileImageUrl(imageUrl); // Ensure image URL is set

            saveActorToDatabase(actor);
        } else {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveActorToDatabase(Actor updatedActor) {
        AsyncTask.execute(() -> {
            actorDao.update(updatedActor); // Update actor in Room database
            runOnUiThread(() -> {
                Toast.makeText(EditActorActivity.this, "Actor updated successfully!", Toast.LENGTH_SHORT).show();
                redirectToActorList(); // Redirect after editing
            });
        });
    }

    private void redirectToActorList() {
        Intent intent = new Intent(EditActorActivity.this, ActorListActivity.class); // Make sure this activity exists
        startActivity(intent);
        finish(); // Finish the current activity
    }

}
