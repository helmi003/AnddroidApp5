package com.example.movieapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "movie_category_join",
        foreignKeys = {
                @ForeignKey(entity = Movie.class, parentColumns = "id", childColumns = "movieId", onDelete = ForeignKey.CASCADE)
        })
public class MovieCategoryJoin {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieId;
    private String categoryId; // Store category as String, not an int

    // Default constructor, getters, and setters
    public MovieCategoryJoin(int movieId, String categoryId) {
        this.movieId = movieId;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
