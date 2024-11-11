package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.entities.Reservation;
import com.example.movieapp.entities.User;

import java.util.List;
@Dao
public interface ReservationDAO {
    @Insert
    void createReservation(Reservation reservation);
    @Delete
    void deleteReservation(Reservation reservation);
    @Update
    void updateReservation(Reservation reservation);
    @Query("SELECT * FROM reservation ORDER BY id DESC")
    List<Reservation> getAllReservations();
    @Query("SELECT * FROM reservation WHERE userId = :id ORDER BY id DESC")
    List<Reservation> getAllReservationsByUser(String id);
    @Query("SELECT * FROM reservation where id = :id")
    Reservation getReservationById(int id);
}
