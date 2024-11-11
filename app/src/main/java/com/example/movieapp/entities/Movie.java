package com.example.movieapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.movieapp.converters.Converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "movie")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private List<String> seats;

    public Movie() {
        this.seats = new ArrayList<>();
    }

    public Movie(int id, String title, List<String> seats) {
        this.id = id;
        this.title = title;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", Seats=" + seats +
                '}';
    }
}
