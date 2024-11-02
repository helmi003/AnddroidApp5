package com.example.movieapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.ActorMovieJoin;

import java.util.List;

@Dao
public interface ActorMovieJoinDao {
    @Insert
    void insert(ActorMovieJoin actorMovieJoin);

    @Query("SELECT * FROM actors INNER JOIN ActorMovieJoin ON actors.id = ActorMovieJoin.actorId WHERE ActorMovieJoin.movieId = :movieId")
    List<Actor> getActorsForMovie(int movieId);
}
