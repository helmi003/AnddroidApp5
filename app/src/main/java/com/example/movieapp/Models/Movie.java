package com.example.movieapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.movieapp.Converters.ActorTypeConverter;
import com.example.movieapp.Converters.CategoryTypeConverter;
import com.example.movieapp.Converters.DateConverter;
import com.example.movieapp.Converters.RoomShowtimeConverter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String image;
    private String description;
    @TypeConverters(DateConverter.class)
    private Date releaseDate;

    // Relationships
    @TypeConverters(ActorTypeConverter.class)
    private List<Actor> actors;

    @TypeConverters(CategoryTypeConverter.class)
    private List<MovieCategory> categories;

    @TypeConverters(RoomShowtimeConverter.class)
    private Map<Room, List<Showtime>> showtimesMap;

    public Movie(int id, String title, String image, String description, Date releaseDate, List<Actor> actors, List<MovieCategory> categories, Map<Room, List<Showtime>> showtimesMap) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.releaseDate = releaseDate;
        this.actors = actors;
        this.categories = categories;
        this.showtimesMap = showtimesMap;
    }

    public Movie() {

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<MovieCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MovieCategory> categories) {
        this.categories = categories;
    }

    public Map<Room, List<Showtime>> getShowtimesMap() {
        return showtimesMap;
    }

    public void setShowtimesMap(Map<Room, List<Showtime>> showtimesMap) {
        this.showtimesMap = showtimesMap;
    }
}
