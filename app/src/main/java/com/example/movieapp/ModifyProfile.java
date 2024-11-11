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

import com.bumptech.glide.Glide;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ModifyProfile extends AppCompatActivity {

    TextView username;
    TextView phone;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    User user;
    ImageView backArrow;
    Button modify;
    private ApplicationDatabase database;
    Button selectImageButton;
    private ImageView movieImageViewDisplay;
    private Uri movieImageUri;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        auth = FirebaseAuth.getInstance();
        database = ApplicationDatabase.getAppDatabase(this);
        currentUser = auth.getCurrentUser();
        backArrow = findViewById(R.id.backArrow);
        modify = findViewById(R.id.modify);
        movieImageViewDisplay = findViewById(R.id.movieImageViewDisplay);
        selectImageButton = findViewById(R.id.selectImageButton);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });
        user = database.userDAO().getUserById(currentUser.getUid());
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        username.setText(user.getUsername());
        phone.setText(user.getPhone().toString());
        if(!user.getImage().isEmpty()){
            Glide.with(this).load(user.getImage()).into(movieImageViewDisplay);
        }
        modify.setOnClickListener(view -> {
            String usernameText = username.getText().toString();
            String phoneText = phone.getText().toString();
            String imagePath = movieImageUri != null ? saveImageToInternalStorage(movieImageUri) : "";
            if (phoneText.isEmpty() || usernameText.isEmpty()) {
                Toast.makeText(ModifyProfile.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            } else if (phoneText.length()!=8) {
                Toast.makeText(ModifyProfile.this, "Phone number must contains 8 digits", Toast.LENGTH_SHORT).show();
                return;
            }else{
                user.setPhone(Long.parseLong(phoneText));
                user.setUsername(usernameText);
                if(!imagePath.isEmpty()){
                    user.setImage(imagePath);
                }
                database.userDAO().updateUser(user);
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