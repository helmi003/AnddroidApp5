package com.example.movieapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

public class SeatReservation extends AppCompatActivity {

    private List<String> selectedSeats = new ArrayList<>();
    ImageView backArrow;
    Button addReservation;
    Movie movie;
    TextView allSelectedSeats;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    private int movieID;
    TextView appBarTitle;
    TextView date;
    ImageView picDetail;
    private ApplicationDatabase database;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_reservation);

        movieID = getIntent().getIntExtra("movieID", -1);
        backArrow = findViewById(R.id.backArrow);
        appBarTitle = findViewById(R.id.appBarTitle);
        date = findViewById(R.id.date);
        addReservation = findViewById(R.id.addReservation);
        picDetail = findViewById(R.id.picDetail);
        allSelectedSeats = findViewById(R.id.selectedSeats);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        backArrow.setOnClickListener(v -> finish());
        database = ApplicationDatabase.getAppDatabase(this);
        movie = database.movieDAO().getMovieById(movieID);

        if (movie == null) {
            Log.e("SeatReservation", "Movie not found with ID: " + movieID);
            Toast.makeText(this, "Movie not found", Toast.LENGTH_SHORT).show();
            return;
        }
        appBarTitle.setText(movie.getTitle());
        Glide.with(this).load(movie.getImageUri()).into(picDetail);
        date.setText(dateFormat.format(movie.getReleaseDate()));
        List<String> movieSeats = movie.getSeats() != null ? movie.getSeats() : new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        Log.d("movie", movie.toString());

        String[] seatIds = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10",
                "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10",
                "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10",
                "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10",
                "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10",
                "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"};

        for (String seatId : seatIds) {
            ImageButton seatButton = findViewById(getResources().getIdentifier(seatId, "id", getPackageName()));
            if (movieSeats.contains(seatId)) {
                seatButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            } else {
                seatButton.setOnClickListener(view -> {
                    boolean isSelected = selectedSeats.contains(seatId);
                    if (!isSelected) {
                        selectedSeats.add(seatId);
                        seatButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    } else {
                        selectedSeats.remove(seatId);
                        seatButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    }
                    if (selectedSeats.isEmpty()) {
                        allSelectedSeats.setText("-");
                    } else {
                        String result = selectedSeats.stream().collect(Collectors.joining(","));
                        allSelectedSeats.setText(result);
                    }
                    Log.d("Selected Seats", selectedSeats.toString());
                });
            }
        }

        addReservation.setOnClickListener(view -> {
            List<String> updatedSeats = new ArrayList<>(movieSeats);
            updatedSeats.addAll(selectedSeats);

            Log.d("movie", updatedSeats.toString());
            database.movieDAO().updateMovieSeats(movieID, updatedSeats);

            Reservation reservation = new Reservation(0, generateRandomNumber(), selectedSeats, new Date(), currentUser.getUid(), movieID);
            database.reservationDAO().createReservation(reservation);

            Log.d("movie", database.reservationDAO().getAllReservations().toString());
            Toast.makeText(SeatReservation.this, "Reservation added successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private static int generateRandomNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}