package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {

    TextView login;
    TextView email;
    Button send;
    FirebaseAuth auth;
    ProgressBar buttonProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        send = findViewById(R.id.send);
        buttonProgress = findViewById(R.id.button_progress);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            Intent intent = new Intent(forgetPassword.this, Login.class);
            startActivity(intent);
        });

        send.setOnClickListener(view -> {
            String emailText = email.getText().toString().trim();

            if (emailText.isEmpty()) {
                Toast.makeText(forgetPassword.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(forgetPassword.this, "That is not a correct email", Toast.LENGTH_SHORT).show();
                return;
            }


            send.setEnabled(false);
            send.setText("");
            buttonProgress.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(emailText).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    buttonProgress.setVisibility(View.GONE);
                    send.setEnabled(true);
                    send.setText("Login");
                    if (task.isSuccessful()) {
                        Toast.makeText(forgetPassword.this, "Check your inbox, an email has been sent.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(forgetPassword.this, Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(forgetPassword.this, "There is no credentials with the provided email",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}