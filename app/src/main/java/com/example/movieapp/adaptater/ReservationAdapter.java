package com.example.movieapp.adaptater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Episodes;
import com.example.movieapp.QRCodeViewer;
import com.example.movieapp.R;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Movie;
import com.example.movieapp.entities.Reservation;
import com.example.movieapp.entities.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    private List<Reservation> reservationList;
    private Context context;
    private ApplicationDatabase database;
    private SimpleDateFormat dateFormat;

    public ReservationAdapter(Context context, List<Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
        this.database = ApplicationDatabase.getAppDatabase(context);
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    }

    @NonNull
    @Override
    public ReservationAdapter.ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationAdapter.ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        Movie movie = database.movieDAO().getMovieById(reservation.getMovieId());
        User user = database.userDAO().getUserById(reservation.getUserId());
        String seats = reservation.getSeats().stream()
                .collect(Collectors.joining(","));
        Date createdDate = reservation.getCreatedDate();
        String formattedDate = createdDate != null ? dateFormat.format(createdDate) : "No date";
        String code = reservation.getCode() != 0 ? String.valueOf(reservation.getCode()) : "N/A";
        String title = movie != null && movie.getTitle() != null ? movie.getTitle() : "--";
        String name = user != null && user.getUsername() != null ? user.getUsername() : "--";
        String phone = user != null && user.getPhone() != 0 ? String.valueOf(user.getPhone()) : "--";
        holder.reservationCodeTextView.setText(code);
        holder.reservationTitleTextView.setText(title);
        holder.reservationNameTextView.setText(name);
        holder.reservationNumberTextView.setText(phone);
        holder.reservationSeatsTextView.setText(seats != null && !seats.isEmpty() ? seats : "No seats");
        holder.reservationDateTextView.setText(formattedDate);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, QRCodeViewer.class);
            String qrcodeText = "Movie: " + title + " | Code: " + code + " | Seats: " + seats + " | Nom: " + name + " | Phone: " + phone;
            intent.putExtra("qrcodeText", qrcodeText);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView reservationCodeTextView;
        TextView reservationTitleTextView;
        TextView reservationNameTextView;
        TextView reservationNumberTextView;
        TextView reservationSeatsTextView;
        TextView reservationDateTextView;

        ReservationViewHolder(View itemView) {
            super(itemView);
            reservationCodeTextView = itemView.findViewById(R.id.code);
            reservationTitleTextView = itemView.findViewById(R.id.title);
            reservationNameTextView = itemView.findViewById(R.id.name);
            reservationNumberTextView = itemView.findViewById(R.id.number);
            reservationSeatsTextView = itemView.findViewById(R.id.seats);
            reservationDateTextView = itemView.findViewById(R.id.date);
        }
    }
}
