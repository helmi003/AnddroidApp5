package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Feedback;
import com.example.movieapp.ui.FeedbackListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FeedbackActivity extends AppCompatActivity {
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private ApplicationDatabase db;
    private RatingBar ratingBar;
    private EditText feedbackEditText;
    private Button submitButton;
    private Button viewFeedbackButton;
    private ImageButton backButton;
    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private final int movieId = 1; // Movie ID fixé à 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialiser Firebase Auth
        auth = FirebaseAuth.getInstance();

        // AuthStateListener pour surveiller les changements d'état de l'utilisateur
        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                Log.d("FeedbackActivity", "Utilisateur connecté : " + currentUser.getUid());
                Toast.makeText(FeedbackActivity.this, "Utilisateur connecté", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("FeedbackActivity", "Aucun utilisateur connecté.");
                Toast.makeText(FeedbackActivity.this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            }
        };

        // Initialiser la base de données et les vues
        db = ApplicationDatabase.getAppDatabase(this);
        ratingBar = findViewById(R.id.ratingBar);
        feedbackEditText = findViewById(R.id.feedbackEditText);
        submitButton = findViewById(R.id.submitButton);
        viewFeedbackButton = findViewById(R.id.viewFeedbackButton);
        backButton = findViewById(R.id.backButton);

        // Bouton retour
        backButton.setOnClickListener(v -> onBackPressed());

        // Soumettre un feedback
        submitButton.setOnClickListener(v -> {
            if (currentUser == null) {
                Toast.makeText(FeedbackActivity.this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
                return;
            }

            String comment = feedbackEditText.getText().toString();
            float rating = ratingBar.getRating();

            if (comment.isEmpty()) {
                Toast.makeText(FeedbackActivity.this, "Veuillez entrer un commentaire", Toast.LENGTH_SHORT).show();
                return;
            }

            // Récupérer l'UID de l'utilisateur connecté
            String userId = currentUser.getUid();
            Log.d("FeedbackActivity", "User UID: " + userId);

            // Récupérer le titre du film (passé en extra dans l'Intent)
            String movieTitle = getIntent().getStringExtra("movieTitle");
            if (movieTitle == null) {
                movieTitle = "Titre du film par défaut";
            }

            // Créer un objet Feedback et définir ses champs
            Feedback feedback = new Feedback();
            feedback.setComment(comment);
            feedback.setRating(rating);
            feedback.setTitle(movieTitle);
            feedback.setUserId(userId);
            feedback.setMovieId(movieId);  // Utilise toujours movieId = 1

            // Vérifier la validité de userId et movieId avant d'insérer
            databaseExecutor.execute(() -> {
                boolean userExists = (db.userDAO().getUserById(userId) != null);
                boolean movieExists = (db.movieDAO().getMovieById(movieId) != null);

                if (userExists && movieExists) {
                    db.feedbackDao().insert(feedback);
                    runOnUiThread(() -> Toast.makeText(FeedbackActivity.this, "Feedback soumis", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> {
                        if (!userExists) {
                            Toast.makeText(FeedbackActivity.this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                        }
                        if (!movieExists) {
                            Toast.makeText(FeedbackActivity.this, "Film non trouvé", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        });

        // Voir les feedbacks
        viewFeedbackButton.setOnClickListener(v -> {
            Log.d("FeedbackActivity", "Voir les Feedbacks bouton cliqué");
            Intent intent = new Intent(FeedbackActivity.this, FeedbackListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
