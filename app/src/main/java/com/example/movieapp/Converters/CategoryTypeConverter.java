package com.example.movieapp.Converters;

import androidx.room.TypeConverter;
import com.example.movieapp.Models.MovieCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class CategoryTypeConverter {

    // Converter for List<MovieCategory>
    @TypeConverter
    public String fromCategoryList(List<MovieCategory> categories) {
        if (categories == null) return null;
        Gson gson = new Gson();
        return gson.toJson(categories);
    }

    @TypeConverter
    public List<MovieCategory> toCategoryList(String categoryString) {
        if (categoryString == null) return null;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<MovieCategory>>() {}.getType();
        return gson.fromJson(categoryString, listType);
    }

    // Converter for single MovieCategory
    @TypeConverter
    public String fromMovieCategory(MovieCategory category) {
        return category == null ? null : category.name();
    }

    @TypeConverter
    public MovieCategory toMovieCategory(String category) {
        return category == null ? null : MovieCategory.valueOf(category);
    }
}
