package com.example.movieapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.movieapp.Models.Movie;

import java.util.Date;
import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    long insertMovie(Movie movie);
    @Query("SELECT * FROM movies WHERE title = :title LIMIT 1")
    Movie getMovieByTitle(String title);
    @Query("SELECT * FROM movies")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movies WHERE releaseDate >= :today ORDER BY releaseDate ASC")
    List<Movie> getUpcomingMovies(Date today);
    @Query("SELECT * FROM movies WHERE id = :movieId")
    Movie getMovieById(int movieId);
}
