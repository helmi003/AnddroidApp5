package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Role;
import com.example.movieapp.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    boolean isPasswordVisible = false;
    boolean isPasswordVisible2 = false;
    TextView login;
    EditText email;
    EditText password;
    EditText username;
    EditText phone;
    EditText confirmPassword;
    ImageView toggle;
    ImageView toggle2;
    Button register;
    FirebaseAuth auth;
    ProgressBar buttonProgress;
    private ApplicationDatabase database;
    Role role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.password2);
        toggle = findViewById(R.id.icon_eye);
        toggle2 = findViewById(R.id.icon_eye2);
        register = findViewById(R.id.register);
        buttonProgress = findViewById(R.id.button_progress);
        database = ApplicationDatabase.getAppDatabase(this);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    toggle.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    toggle.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
                password.setSelection(password.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });

        toggle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible2) {
                    // Set to password type
                    confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    toggle2.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    // Set to visible password type
                    confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    toggle2.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
                // Keep the cursor at the end of the text
                confirmPassword.setSelection(confirmPassword.getText().length());
                isPasswordVisible2 = !isPasswordVisible2;
            }
        });

        login.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });

        register.setOnClickListener(view -> {
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            String phoneText = phone.getText().toString().trim();
            String usernameText = username.getText().toString().trim();
            String confirmPasswordText = confirmPassword.getText().toString().trim();

            if (emailText.isEmpty() || passwordText.isEmpty() || phoneText.isEmpty() || usernameText.isEmpty()) {
                Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(Register.this, "That is not a correct email", Toast.LENGTH_SHORT).show();
                return;
            } else if (phoneText.length()!=8) {
                Toast.makeText(Register.this, "Phone number must contains 8 digits", Toast.LENGTH_SHORT).show();
                return;
            } else if(!passwordText.equals(confirmPasswordText)) {
                Toast.makeText(Register.this, "The confirm password should match the password.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(emailText.matches("helmi.benromdhane@esprit.tn")){
                role = Role.ADMIN;
            }else{
                role = Role.USER;
            }
            register.setEnabled(false);
            register.setText("");
            buttonProgress.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            buttonProgress.setVisibility(View.GONE);
                            register.setEnabled(true);
                            register.setText("Register");
                            if (task.isSuccessful()) {
                                FirebaseUser user = task.getResult().getUser();
                                String userId = user.getUid();

                                database.userDAO().createUser(new User(userId,usernameText,Long.parseLong(phoneText),emailText, role,passwordText,false,false));
                                Toast.makeText(Register.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Register.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(Register.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}