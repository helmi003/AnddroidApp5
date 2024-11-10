package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profil extends AppCompatActivity {

    TextView email;
    TextView username;
    TextView phone;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    User user;
    ImageView backArrow;
    Button modify;
    private ApplicationDatabase database;

    private final ActivityResultLauncher<Intent> addSeasonLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadProfile();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        database = ApplicationDatabase.getAppDatabase(this);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        backArrow = findViewById(R.id.backArrow);
        modify = findViewById(R.id.modify);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        modify.setOnClickListener(v -> {
            Intent intent = new Intent(Profil.this,ModifyProfile.class);
            addSeasonLauncher.launch(intent);
        });
        loadProfile();
    }

    private void loadProfile() {
        if (currentUser != null) {
            user = database.userDAO().getUserById(currentUser.getUid());
            if (user != null) {
                Log.d("user", user.toString());
                username.setText(user.getUsername());
                phone.setText(user.getPhone().toString());
                email.setText(user.getEmail());
            } else {
                Log.d("Profil", "User data not found in local database");
                // You can handle this case, e.g., show a default message or fetch user data from Firebase
            }
        } else {
            Log.d("Profil", "No current Firebase user");
        }
    }

}
