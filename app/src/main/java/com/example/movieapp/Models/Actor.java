package com.example.movieapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Map;

@Entity(tableName = "actors")
public class Actor {
    @PrimaryKey(autoGenerate = true)
    private int id; // Unique ID for Room
    private String name;
    private String bio;
    private String profileImageUrl;

    // Constructors, getters, and setters
    public Actor(String name, String bio, String profileImageUrl) {
        this.name = name;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public String getProfileImageUrl() { return profileImageUrl; }

    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
}
