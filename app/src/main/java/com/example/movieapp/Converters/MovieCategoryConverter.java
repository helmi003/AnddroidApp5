package com.example.movieapp.converters;
import androidx.room.TypeConverter;

import com.example.movieapp.Models.MovieCategory;

public class MovieCategoryConverter {

    @TypeConverter
    public static MovieCategory fromString(String value) {
        return value == null ? null : MovieCategory.valueOf(value);
    }

    @TypeConverter
    public static String fromMovieCategory(MovieCategory movieCategory) {
        return movieCategory == null ? null : movieCategory.name();
    }
}
