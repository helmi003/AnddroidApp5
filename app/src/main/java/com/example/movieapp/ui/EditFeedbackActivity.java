package com.example.movieapp.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.example.movieapp.R;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Feedback;

public class EditFeedbackActivity extends AppCompatActivity {
    private EditText commentEditText;
    private RatingBar ratingBar;
    private Button saveButton;
    private int feedbackId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feedback);

        commentEditText = findViewById(R.id.commentEditText);
        ratingBar = findViewById(R.id.ratingBar);
        saveButton = findViewById(R.id.saveButton);

        feedbackId = getIntent().getIntExtra("FEEDBACK_ID", -1);

        if (feedbackId != -1) {
            loadFeedback(feedbackId);
        }

        saveButton.setOnClickListener(v -> saveFeedback());
    }

    private void loadFeedback(int feedbackId) {
        ApplicationDatabase db = ApplicationDatabase.getAppDatabase(getApplicationContext());
        LiveData<Feedback> feedbackLiveData = db.feedbackDao().getFeedbackById(feedbackId);

        // Observer pour mettre à jour l'UI
        feedbackLiveData.observe(this, new Observer<Feedback>() {
            @Override
            public void onChanged(Feedback feedback) {
                if (feedback != null) {
                    commentEditText.setText(feedback.getComment());
                    ratingBar.setRating(feedback.getRating());
                    Log.d("EditFeedbackActivity", "Feedback chargé: " + feedback.toString());
                } else {
                    Log.e("EditFeedbackActivity", "Feedback non trouvé avec l'ID: " + feedbackId);
                    Toast.makeText(EditFeedbackActivity.this, "Feedback non trouvé", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveFeedback() {
        String updatedComment = commentEditText.getText().toString();
        float updatedRating = ratingBar.getRating();

        // Récupérer l'objet Feedback à mettre à jour
        ApplicationDatabase db = ApplicationDatabase.getAppDatabase(getApplicationContext());
        db.feedbackDao().getFeedbackById(feedbackId).observe(this, feedback -> {
            if (feedback != null) {
                // Mise à jour du Feedback
                feedback.setComment(updatedComment);
                feedback.setRating(updatedRating);

                // Mise à jour de la base de données
                new Thread(() -> {
                    db.feedbackDao().updateFeedback(feedback);
                    runOnUiThread(() -> {
                        Toast.makeText(EditFeedbackActivity.this, "Feedback mis à jour", Toast.LENGTH_SHORT).show();
                        Log.d("EditFeedbackActivity", "Feedback mis à jour avec succès");
                        finish(); // Fermer l'activité après la mise à jour
                    });
                }).start();
            } else {
                Log.e("EditFeedbackActivity", "Feedback non trouvé pour la mise à jour");
                Toast.makeText(EditFeedbackActivity.this, "Feedback non trouvé", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
