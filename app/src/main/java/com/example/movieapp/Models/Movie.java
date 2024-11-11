package com.example.movieapp.Models;

import android.net.ParseException;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "movie")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String imageUri;
    private String description;
    private List<String> seats;
    private Date releaseDate;
    private List<Integer> actorIds;
    private List<String> categories;

    // Default constructor
    public Movie() {
        this.seats = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.actorIds = new ArrayList<>();
    }

    // Updated constructor with field assignments
    public Movie(String title, String description, String releaseDateString, String imageUri) {
        this.title = title;
        this.description = description;
        this.imageUri = imageUri;

        // Convert the release date string to Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.releaseDate = dateFormat.parse(releaseDateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            this.releaseDate = null;  // Set to null if parsing fails
        }

        // Initialize lists to avoid null issues
        this.seats = new ArrayList<>();
        this.actorIds = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    // Getters and setters
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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

    public List<Integer> getActorIds() {
        return actorIds;
    }

    public void setActorIds(List<Integer> actorIds) {
        this.actorIds = actorIds;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
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
                ", imageUri='" + imageUri + '\'' +
                ", description='" + description + '\'' +
                ", seats=" + seats +
                ", releaseDate=" + releaseDate +
                ", actorIds=" + actorIds +
                ", categories=" + categories +
                '}';
    }
}
