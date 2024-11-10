package com.example.movieapp.converters;

import androidx.room.TypeConverter;

import com.example.movieapp.Models.Room;
import com.example.movieapp.Models.Showtime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class RoomShowtimeConverter {
    @TypeConverter
    public String fromRoomShowtimeMap(Map<Room, List<Showtime>> showtimesMap) {
        if (showtimesMap == null) return null;
        Gson gson = new Gson();
        return gson.toJson(showtimesMap);
    }

    @TypeConverter
    public Map<Room, List<Showtime>> toRoomShowtimeMap(String showtimeString) {
        if (showtimeString == null) return null;
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<Room, List<Showtime>>>() {}.getType();
        return gson.fromJson(showtimeString, mapType);
    }
}

