package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.movieapp.Models.Movie;
import java.util.Date;
import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    long insertMovie(Movie movie);
    @Query("SELECT * FROM movie WHERE title = :title LIMIT 1")
    Movie getMovieByTitle(String title);
    @Query("SELECT * FROM movie")
    List<Movie> getAllMovies();
    @Query("SELECT * FROM movie WHERE releaseDate >= :today ORDER BY releaseDate ASC")
    List<Movie> getUpcomingMovies(Date today);
    @Query("SELECT * FROM movie WHERE id = :id")
    Movie getMovieById(int id);
    @Query("UPDATE movie SET seats = :seats WHERE id = :id")
    void updateMovieSeats(int id, List<String> seats);
}
