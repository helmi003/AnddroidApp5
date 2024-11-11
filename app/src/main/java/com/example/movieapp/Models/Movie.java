package com.example.movieapp.Models;

import android.net.ParseException;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.movieapp.Converters.Converters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String imageUri;
    private String description;

    @TypeConverters({Converters.class})  // Use the Date converter
    private Date releaseDate;

    @TypeConverters({Converters.class})  // Use the converter for List<Integer>
    private List<Integer> actorIds;

    @TypeConverters({Converters.class})  // Use the converter for List<String> or MovieCategory
    private List<String> categories;

    public Movie(String title, String description, String releaseDateString,String imageUri, List<Integer> actorIds, List<String> categories) {
        this.title = title;
        this.description = description;
        this.actorIds = actorIds;
        this.categories = categories;
        this.imageUri = imageUri;

        // Convert the release date String to Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Use the format that matches your input
        try {
            this.releaseDate = dateFormat.parse(releaseDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            this.releaseDate = null; // Set to null or handle it appropriately
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public Movie(String title, String imageUri, String description, Date releaseDate, List<Integer> actorIds, List<String> categories) {
        this.title = title;
        this.imageUri = imageUri;
        this.description = description;
        this.releaseDate = releaseDate;
        this.actorIds = actorIds;
        this.categories = categories;
    }

    public Movie(String title, String description, String releaseDate, String imagePath) {
    }

    // Getters and Setters
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
}
