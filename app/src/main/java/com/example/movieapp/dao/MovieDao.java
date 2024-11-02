package com.example.movieapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movieapp.Models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    long insertMovie(Movie movie);

    @Transaction
    @Query("SELECT * FROM movies")
    List<Movie> getAllMoviesWithActorsAndCategories();

    // Add more complex queries if needed for joining multiple tables.
}

