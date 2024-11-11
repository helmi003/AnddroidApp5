package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    void createMovie(Movie movie);
    @Query("SELECT * FROM movie WHERE id = :id")
    Movie getMovieById(int id);
    @Query("UPDATE movie SET seats = :seats WHERE id = :id")
    void updateMovieSeats(int id, List<String> seats);
}
