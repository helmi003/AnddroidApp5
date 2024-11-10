package com.example.movieapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "actor_movie_join",
        primaryKeys = {"actorId", "movieId"},
        foreignKeys = {
                @ForeignKey(entity = Actor.class,
                        parentColumns = "id", // Use the correct primary key column name in Actor
                        childColumns = "actorId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Movie.class,
                        parentColumns = "id", // Use the correct primary key column name in Movie
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE)
        })
public class ActorMovieJoin {
    private int actorId;
    private int movieId;

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
