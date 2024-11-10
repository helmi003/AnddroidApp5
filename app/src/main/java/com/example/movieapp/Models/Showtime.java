package com.example.movieapp.Models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "showtimes",
        foreignKeys = {
                @ForeignKey(entity = Movie.class, parentColumns = "id", childColumns = "movieId")
        })
public class Showtime {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieId;
    private String roomName;
    private String showtime; // Show time as a String (e.g., "HH:mm")

    public Showtime() {
    }

    public Showtime(int movieId, String roomName, String showtime) {
        this.movieId = movieId;
        this.roomName = roomName;
        this.showtime = showtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }
}


