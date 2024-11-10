package com.example.movieapp.Converters;

import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromList(List<Integer> list) {
        return list == null ? null : list.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @TypeConverter
    public static List<Integer> toList(String data) {
        return data == null || data.isEmpty() ? null : Arrays.stream(data.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    // Converter for List<String> (categories)
    @TypeConverter
    public static String fromCategoryList(List<String> list) {
        return list == null ? null : String.join(",", list);
    }

    @TypeConverter
    public static List<String> toCategoryList(String data) {
        return data == null || data.isEmpty() ? null : Arrays.asList(data.split(","));
    }
}
