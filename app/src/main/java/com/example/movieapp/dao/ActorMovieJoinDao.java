package com.example.movieapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.ActorMovieJoin;

import java.util.List;

@Dao
public interface ActorMovieJoinDao {
    @Insert
    void insert(ActorMovieJoin actorMovieJoin);

    @Query("SELECT * FROM actor_movie_join WHERE movieId = :movieId")
    List<ActorMovieJoin> getActorsForMovieOld(int movieId);
    @Transaction
    @Query("SELECT * FROM actors INNER JOIN actor_movie_join ON actors.id = actor_movie_join.actorId WHERE actor_movie_join.movieId = :movieId")
    List<Actor> getActorsForMovie(int movieId);
    @Query("SELECT * FROM actor_movie_join WHERE actorId = :actorId")
    List<ActorMovieJoin> getMoviesForActor(int actorId);}
