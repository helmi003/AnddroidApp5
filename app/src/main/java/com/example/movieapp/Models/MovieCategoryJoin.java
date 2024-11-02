package com.example.movieapp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {"movieId", "category"},
        foreignKeys = {
                @ForeignKey(entity = Movie.class, parentColumns = "id", childColumns = "movieId")
        }
)
public class MovieCategoryJoin {
    public int movieId;

    @NonNull
    public String category; // Store category as a String

    // Empty constructor for Room
    public MovieCategoryJoin() {
        // Required empty constructor for Room
    }

    // Parameterized constructor matching the fields
    public MovieCategoryJoin(int movieId, @NonNull String category) {
        this.movieId = movieId;
        this.category = category; // Accept a String directly
    }
}
