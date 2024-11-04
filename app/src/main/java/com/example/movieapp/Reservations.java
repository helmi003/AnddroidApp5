package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adaptater.ReservationAdapter;
import com.example.movieapp.adaptater.SeasonAdminAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Reservation;
import com.example.movieapp.entities.Season;

import java.util.List;

public class Reservations extends AppCompatActivity {

    private RecyclerView reservationsRecyclerView;
    private ImageView backArrow;
    private ImageView scanQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_resertvations);
        reservationsRecyclerView = findViewById(R.id.reservationsRecyclerView);
        backArrow = findViewById(R.id.backArrow);
        scanQRCode = findViewById(R.id.scanQRCode);
        backArrow.setOnClickListener(v -> {
            finish();
        });
        scanQRCode.setOnClickListener(v -> {
            Intent intent = new Intent(Reservations.this, QRCodeScanner.class);
            startActivity(intent);
        });
        loadReservations();
    }

    private void loadReservations() {
        List<Reservation> reservations = ApplicationDatabase.getAppDatabase(this).reservationDAO().getAllReservations();
        ReservationAdapter adapter = new ReservationAdapter(this, reservations);
        reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reservationsRecyclerView.setAdapter(adapter);
    }
}