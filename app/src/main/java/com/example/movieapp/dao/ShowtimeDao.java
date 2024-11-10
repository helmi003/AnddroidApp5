package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieapp.Models.Showtime;

import java.util.List;

@Dao
public interface ShowtimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShowtime(Showtime showtime);

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    List<Showtime> getShowtimesForMovie(int movieId);

    @Delete
    void deleteShowtime(Showtime showtime);
}

