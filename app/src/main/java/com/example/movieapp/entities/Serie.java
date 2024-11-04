package com.example.movieapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.movieapp.converters.Converters;

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

    public Serie(int id, String title, String description, Long stars) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.stars = stars;
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

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", stars=" + stars +
                '}';
    }
}
