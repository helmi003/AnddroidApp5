package com.example.movieapp.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Activities.BaseActivity;
import com.example.movieapp.Activities.EditActorActivity;
import com.example.movieapp.Adapters.ActorAdapter;
import com.example.movieapp.AppDatabase;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.List;

public class ActorListActivity extends BaseActivity implements ActorAdapter.OnItemClickListener {
    private RecyclerView actorsRecyclerView;
    private ActorAdapter actorAdapter;
    private List<Actor> actorList;
    private AppDatabase appDatabase; // Database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_list);

        // Initialize RecyclerView and set the adapter
        actorsRecyclerView = findViewById(R.id.actorsRecyclerView);
        actorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the actor list and adapter
        actorList = new ArrayList<>();
        actorAdapter = new ActorAdapter(this, actorList, this);
        actorsRecyclerView.setAdapter(actorAdapter);

        // Initialize Room database
        appDatabase = AppDatabase.getInstance(this);

        // Fetch data from Room
        fetchActorData();
    }

    private void fetchActorData() {
        // Fetch data from Room database (executed on a background thread)
        new Thread(() -> {
            actorList.clear();
            actorList.addAll(appDatabase.actorDao().getAllActors()); // Get all actors from the database
            runOnUiThread(() -> actorAdapter.notifyDataSetChanged()); // Notify adapter on the main thread
        }).start();

    }

    @Override
    public void onEditClick(Actor actor) {
        // Start the EditActorActivity and pass the actor details
        Intent intent = new Intent(this, EditActorActivity.class);
        intent.putExtra("ACTOR_ID", actor.getId()); // Pass the actor's ID
        intent.putExtra("ACTOR_NAME", actor.getName()); // Pass the actor's name
        intent.putExtra("ACTOR_BIO", actor.getBio()); // Pass the actor's bio
        intent.putExtra("ACTOR_IMAGE_URL", actor.getProfileImageUrl()); // Pass the actor's image URL
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Actor actor) {
        // Show a confirmation dialog before deleting
        new AlertDialog.Builder(this)
                .setTitle("Delete Actor")
                .setMessage("Are you sure you want to delete this actor?")
                .setPositiveButton("Delete", (dialog, which) -> deleteActor(actor))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteActor(Actor actor) {
        // Remove actor from Room database (executed on a background thread)
        new Thread(() -> {
            appDatabase.actorDao().delete(actor);
            actorList.remove(actor);
            runOnUiThread(() -> {
                actorAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Actor deleted", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
