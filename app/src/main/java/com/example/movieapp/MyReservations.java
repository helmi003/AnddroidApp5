package com.example.movieapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.ReservationAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MyReservations extends AppCompatActivity {

    private RecyclerView reservationsRecyclerView;
    private ImageView backArrow;
    FirebaseUser currentUser;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        reservationsRecyclerView = findViewById(R.id.reservationsRecyclerView);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        loadReservations();
    }

    private void loadReservations() {
        List<Reservation> reservations = ApplicationDatabase.getAppDatabase(this).reservationDAO().getAllReservationsByUser(currentUser.getUid());
        ReservationAdapter adapter = new ReservationAdapter(this, reservations);
        reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reservationsRecyclerView.setAdapter(adapter);
    }
}