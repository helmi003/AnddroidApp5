package com.example.movieapp.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "feedback",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Movie.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "userId"),
                @Index(value = "movieId"),

        }
)
public class Feedback implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String comment;
    private float rating;
    private String userId;
    private int movieId;


    // Constructor Room should use
    public Feedback(int id, String title, float rating, String comment, String userId , int movieId) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.comment = comment;
        this.userId = userId;
        this.movieId = movieId;
    }

    // Other constructors marked with @Ignore to prevent Room from using them


    @Ignore
    public Feedback(String title, float rating, String comment) {
        this.title = title;
        this.rating = rating;
        this.comment = comment;
    }

    @Ignore
    public Feedback() {
    }

    @Ignore
    public Feedback(String comment, float rating) {
        this.comment = comment;
        this.rating = rating;
    }

    @Ignore
    public Feedback(int id, String comment, float rating) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", userId='" + userId + '\'' +
                ", movieId=" + movieId +
                '}';
    }
}