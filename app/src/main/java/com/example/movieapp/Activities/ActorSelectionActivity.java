package com.example.movieapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.movieapp.AppDatabase;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.ActorMovieJoin;
import com.example.movieapp.R;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActorSelectionActivity extends BaseActivity {

    private LinearLayout actorCheckboxContainer;
    private Button saveButton;
    private int movieId;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_selection);

        actorCheckboxContainer = findViewById(R.id.actorCheckboxContainer);
        saveButton = findViewById(R.id.saveButton);

        // Get movie ID from intent
        movieId = getIntent().getIntExtra("movieId", -1);

        if (movieId != -1) {
            executorService.execute(this::setupActorCheckboxes);
            saveButton.setOnClickListener(v -> {
                executorService.execute(this::saveSelectedActors);
            });
        }
    }

    private void setupActorCheckboxes() {
        AppDatabase database = AppDatabase.getInstance(this);
        List<Actor> actors = database.actorDao().getAllActors();

        runOnUiThread(() -> {
            for (Actor actor : actors) {
                LinearLayout actorRow = new LinearLayout(this);
                actorRow.setOrientation(LinearLayout.HORIZONTAL);
                actorRow.setPadding(8, 16, 8, 16);
                actorRow.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rowParams.setMargins(0, 8, 0, 8);
                actorRow.setLayoutParams(rowParams);

                ImageView profileImage = new ImageView(this);
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(120, 120);
                imageParams.setMargins(0, 0, 16, 0);
                profileImage.setLayoutParams(imageParams);
                Glide.with(this)
                        .load(actor.getProfileImageUrl())
                        .placeholder(R.drawable.ic_placeholder_image)
                        .into(profileImage);

                TextView actorName = new TextView(this);
                actorName.setText(actor.getName());
                actorName.setTextColor(getResources().getColor(R.color.white));
                actorName.setTextSize(16);
                actorName.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                CheckBox checkBox = new CheckBox(this);
                checkBox.setButtonTintList(getResources().getColorStateList(R.color.white));
                checkBox.setTag(actor.getId());

                actorRow.addView(profileImage);
                actorRow.addView(actorName);
                actorRow.addView(checkBox);

                actorCheckboxContainer.addView(actorRow);
            }
        });
    }

    private void saveSelectedActors() {
        AppDatabase database = AppDatabase.getInstance(this);

        for (int i = 0; i < actorCheckboxContainer.getChildCount(); i++) {
            LinearLayout actorRow = (LinearLayout) actorCheckboxContainer.getChildAt(i);
            CheckBox checkBox = (CheckBox) actorRow.getChildAt(2);

            if (checkBox.isChecked()) {
                int actorId = (int) checkBox.getTag();
                Log.d("ActorSelectionActivity", "Attempting to add Actor ID: " + actorId + " to Movie ID: " + movieId);

                if (!database.actorMovieJoinDao().isActorAttachedToMovie(movieId, actorId)) {
                    ActorMovieJoin join = new ActorMovieJoin();
                    join.setActorId(actorId);
                    join.setMovieId(movieId);
                    database.actorMovieJoinDao().insert(join);
                    Log.d("ActorSelectionActivity", "Actor ID " + actorId + " added to movie with ID " + movieId);
                } else {
                    Log.d("ActorSelectionActivity", "Actor ID " + actorId + " is already attached to movie with ID " + movieId);
                }
            }
        }

        List<Actor> attachedActors = database.actorMovieJoinDao().getActorsForMovie(movieId);
        Log.d("ActorSelectionActivity", "Attached Actors Count: " + attachedActors.size());

        runOnUiThread(this::finish);
    }
}
