package com.example.movieapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SeatReservationActivity extends AppCompatActivity {

    private List<String> selectedSeats = new ArrayList<>();
    private static final int SEATS_IN_FIRST_ROW = 10;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_reservation);
        LinearLayout firstRowLayout = findViewById(R.id.firstRowLayout);
        for (int j = 1; j <= SEATS_IN_FIRST_ROW; j++) {
            String seatId = "A" + j;
            int buttonId = getResources().getIdentifier("A" + j, "id", getPackageName());
            ImageButton seatButton = firstRowLayout.findViewById(buttonId);
            if (seatButton != null) {
                seatButton.setTag(seatId);
                seatButton.setOnClickListener(view -> handleSeatSelection(seatButton, seatId));
            }
        }
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
    }

    private void handleSeatSelection(ImageButton seatButton, String seatId) {
        if (selectedSeats.contains(seatId)) {
            // Deselect the seat
            selectedSeats.remove(seatId);
            seatButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500"))); // Original color (orange)
        } else {
            // Select the seat
            selectedSeats.add(seatId);
            seatButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN)); // Selected color (green)
        }
        showSelectedSeats(); // Display selected seats
    }

    private void showSelectedSeats() {
        StringBuilder seats = new StringBuilder("Selected Seats: ");
        for (String seat : selectedSeats) {
            seats.append(seat).append(" ");
        }
        Toast.makeText(this, seats.toString(), Toast.LENGTH_SHORT).show();
    }
}
