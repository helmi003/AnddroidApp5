package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieapp.DAO.Feedback;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.database.ApplicationDatabase;
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
    private int movieId;
    Movie movie;
    TextView movieTitle;
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = ApplicationDatabase.getAppDatabase(this);
        movieId = getIntent().getIntExtra("movieId", -1);
        movie = db.movieDAO().getMovieById(movieId);
        ratingBar = findViewById(R.id.ratingBar);
        movieTitle = findViewById(R.id.movieTitle);
        moviePoster = findViewById(R.id.moviePoster);
        movieTitle.setText(movie.getTitle());
        if(!movie.getImageUri().isEmpty()){
            Glide.with(this).load(movie.getImageUri()).into(moviePoster);
        }
        feedbackEditText = findViewById(R.id.feedbackEditText);
        submitButton = findViewById(R.id.submitButton);
        viewFeedbackButton = findViewById(R.id.viewFeedbackButton);
        backButton = findViewById(R.id.backButton);


        // Initialize the AuthStateListener
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d("FeedbackActivity", "User signed in: " + user.getUid());
            } else {
                Log.d("FeedbackActivity", "No user signed in");
            }
        };

        backButton.setOnClickListener(v -> finish());

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
            Feedback feedback = new Feedback();
            feedback.setComment(comment);
            feedback.setRating(rating);
            feedback.setTitle(movieTitle.getText().toString());
            feedback.setUserId(currentUser.getUid());
            feedback.setMovieId(movieId);  // Utilise toujours movieId = 1

            // Vérifier la validité de userId et movieId avant d'insérer
            databaseExecutor.execute(() -> {
                boolean userExists = (db.userDAO().getUserById(currentUser.getUid()) != null);
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
