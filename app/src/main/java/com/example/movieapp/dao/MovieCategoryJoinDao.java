package com.example.movieapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movieapp.Models.MovieCategory;
import com.example.movieapp.Models.MovieCategoryJoin;

import java.util.List;

@Dao
public interface MovieCategoryJoinDao {
    @Insert
    void insert(MovieCategoryJoin movieCategoryJoin);

    @Query("SELECT * FROM MovieCategoryJoin WHERE movieId = :movieId")
    List<MovieCategory> getCategoriesForMovie(int movieId);
}
