package com.example.movieapp.converters;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromStringList(List<String> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<String> toStringList(String data) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(data, listType);
    }
}
