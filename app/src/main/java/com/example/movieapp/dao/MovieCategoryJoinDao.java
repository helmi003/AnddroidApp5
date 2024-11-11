package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movieapp.Models.MovieCategoryJoin;
import com.example.movieapp.Models.MovieCategory;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MovieCategoryJoinDao {

    @Insert
    void insert(MovieCategoryJoin movieCategoryJoin);

    @Query("SELECT * FROM movie_category_join WHERE movieId = :movieId")
    List<MovieCategoryJoin> getCategoriesForMovie(int movieId);


    @Query("SELECT EXISTS(SELECT 1 FROM movie_category_join WHERE movieId = :movieId AND categoryId = :categoryId)")
    boolean isCategoryAttachedToMovie(int movieId, String categoryId);

    default List<MovieCategory> getCategoriesAsEnums(List<MovieCategoryJoin> joins) {
        List<MovieCategory> categories = new ArrayList<>();
        for (MovieCategoryJoin join : joins) {
            MovieCategory category = MovieCategory.fromString(join.getCategoryId());
            if (category != null) {
                categories.add(category);
            }
        }
        return categories;
    }
}
