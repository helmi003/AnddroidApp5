package com.example.movieapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
@Entity(tableName="serie")
public class Serie implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo()
    private String title;
    @ColumnInfo()
    private String description;
    @ColumnInfo()
    private Long stars;
    @ColumnInfo()
    private String imageUri;

    public Serie(int id, String title, String description, Long stars, String imageUri) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.stars = stars;
        this.imageUri = imageUri;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStars() {
        return stars;
    }

    public void setStars(Long stars) {
        this.stars = stars;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", stars=" + stars +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }
}
