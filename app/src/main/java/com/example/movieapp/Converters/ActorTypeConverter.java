package com.example.movieapp.converters;

import androidx.room.TypeConverter;

import com.example.movieapp.Models.Actor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ActorTypeConverter {
    @TypeConverter
    public String fromActorList(List<Actor> actors) {
        if (actors == null) return null;
        Gson gson = new Gson();
        return gson.toJson(actors);
    }

    @TypeConverter
    public List<Actor> toActorList(String actorString) {
        if (actorString == null) return null;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Actor>>() {}.getType();
        return gson.fromJson(actorString, listType);
    }
}

