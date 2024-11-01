package com.example.movieapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.List;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String image;
    private String description;
    private List<String> Seat;
    private Date releaseDate;

    public Movie(int id, String title, String image, String description, List<String> seat, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        Seat = seat;
        this.releaseDate = releaseDate;
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

    public List<String> getSeat() {
        return Seat;
    }

    public void setSeat(List<String> seat) {
        Seat = seat;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", Seat=" + Seat +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
