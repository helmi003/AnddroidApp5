package com.example.movieapp;

import android.os.Bundle;
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
import com.example.movieapp.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ModifyProfile extends AppCompatActivity {

    TextView username;
    TextView phone;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    User user;
    ImageView backArrow;
    Button modify;
    private ApplicationDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        auth = FirebaseAuth.getInstance();
        database = ApplicationDatabase.getAppDatabase(this);
        currentUser = auth.getCurrentUser();
        backArrow = findViewById(R.id.backArrow);
        modify = findViewById(R.id.modify);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        user = database.userDAO().getUserById(currentUser.getUid());
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        username.setText(user.getUsername());
        phone.setText(user.getPhone().toString());

        modify.setOnClickListener(view -> {
            String usernameText = username.getText().toString();
            String phoneText = phone.getText().toString();
            if (phoneText.isEmpty() || usernameText.isEmpty()) {
                Toast.makeText(ModifyProfile.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            } else if (phoneText.length()!=8) {
                Toast.makeText(ModifyProfile.this, "Phone number must contains 8 digits", Toast.LENGTH_SHORT).show();
                return;
            }else{
                user.setPhone(Long.parseLong(phoneText));
                user.setUsername(usernameText);
                database.userDAO().updateUser(user);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}