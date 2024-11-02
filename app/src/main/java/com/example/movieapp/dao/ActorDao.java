package com.example.movieapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.Models.Actor;

import java.util.List;

@Dao
public interface ActorDao {
    @Insert
    void insert(Actor actor);

    @Update
    void update(Actor actor);

    @Delete
    void delete(Actor actor);

    @Query("SELECT * FROM actors")
    List<Actor> getAllActors();

    @Query("SELECT * FROM actors WHERE id = :id")
    Actor getActorById(int id);
}
