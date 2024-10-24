package com.example.movieapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    boolean isPasswordVisible = false;
    ImageView toggle;
    EditText password;
    EditText email;
    TextView register;
    TextView forget;
    Button login;
    FirebaseAuth auth;
    ProgressBar buttonProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        forget = findViewById(R.id.forgetPassword);
        register = findViewById(R.id.register);
        toggle = findViewById(R.id.icon_eye);
        auth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login);
        buttonProgress = findViewById(R.id.button_progress);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Set to password type
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    toggle.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    // Set to visible password type
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    toggle.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
                // Keep the cursor at the end of the text
                password.setSelection(password.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });

        register.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        forget.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, forgetPassword.class);
            startActivity(intent);
        });

        login.setOnClickListener(view -> {

            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(Login.this, "Email and Password cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(Login.this, "That is not a correct email", Toast.LENGTH_SHORT).show();
                return;
            }
            login.setEnabled(false);
            login.setText("");
            buttonProgress.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            buttonProgress.setVisibility(View.GONE);
                            login.setEnabled(true);
                            login.setText("Login");
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Profil.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, "There are no credentials with the provided information",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
            });
        });

    }
}