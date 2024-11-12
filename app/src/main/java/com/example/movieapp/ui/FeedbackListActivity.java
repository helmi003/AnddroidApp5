package com.example.movieapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.DAO.Feedback;
import com.example.movieapp.R;
import com.example.movieapp.database.ApplicationDatabase;

import java.util.List;

public class FeedbackListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;
    private Button viewUpdateButton;
    ImageView backArrow;
    private TextView bestRatingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        recyclerView = findViewById(R.id.recyclerView);
        backArrow = findViewById(R.id.backArrow);
        bestRatingTextView = findViewById(R.id.bestRatingTextView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backArrow.setOnClickListener(v -> finish());
        ApplicationDatabase db = ApplicationDatabase.getAppDatabase(getApplicationContext());
        db.feedbackDao().getAllFeedbacks().observe(this, new Observer<List<Feedback>>() {
            @Override
            public void onChanged(List<Feedback> feedbackList) {
                if (feedbackList == null || feedbackList.isEmpty()) {
                    Toast.makeText(FeedbackListActivity.this, "Aucun feedback disponible", Toast.LENGTH_SHORT).show();
                } else {
                    if (feedbackAdapter == null) {
                        feedbackAdapter = new FeedbackAdapter(feedbackList, FeedbackListActivity.this, new FeedbackAdapter.OnFeedbackClickListener() {
                            @Override
                            public void onEditFeedback(int feedbackId) {
                                Intent intent = new Intent(FeedbackListActivity.this, EditFeedbackActivity.class);
                                intent.putExtra("FEEDBACK_ID", feedbackId);
                                startActivity(intent);
                                Toast.makeText(FeedbackListActivity.this, "Modification du feedback en cours", Toast.LENGTH_SHORT).show();
                            }
                        });
                        recyclerView.setAdapter(feedbackAdapter);
                        Toast.makeText(FeedbackListActivity.this, "Feedbacks chargés", Toast.LENGTH_SHORT).show();
                    } else {
                        feedbackAdapter.updateFeedbackList(feedbackList);
                        Toast.makeText(FeedbackListActivity.this, "Feedbacks mis à jour", Toast.LENGTH_SHORT).show();
                    }
                    displayBestRatedFilm(feedbackList);
                }
            }
        });
    }

    private void displayBestRatedFilm(List<Feedback> feedbackList) {
        if (feedbackList != null && !feedbackList.isEmpty()) {
            float bestRating = -1;
            String bestRatedMovie = "";

            for (Feedback feedback : feedbackList) {
                if (feedback.getRating() > bestRating) {
                    bestRating = feedback.getRating();
                    bestRatedMovie = feedback.getTitle();
                }
            }
            bestRatingTextView.setText("Le meilleur film jusqu'à maintenant est : " + bestRatedMovie + " avec un rating de " + bestRating);
        }
    }
}
