package com.example.movieapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"actorId", "movieId"},
        foreignKeys = {
                @ForeignKey(entity = Actor.class, parentColumns = "id", childColumns = "actorId"),
                @ForeignKey(entity = Movie.class, parentColumns = "id", childColumns = "movieId")
        })
public class ActorMovieJoin {
    public int actorId;
    public int movieId;

    public ActorMovieJoin(int actorId, int movieId) {
        this.actorId = actorId;
        this.movieId = movieId;
    }
}

