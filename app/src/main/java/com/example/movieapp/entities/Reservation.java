package com.example.movieapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity(tableName = "reservation",
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
        })
public class Reservation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int code;
    @ColumnInfo(name = "places")
    private List<String> seats;
    @ColumnInfo(name = "created_date")
    private Date createdDate = new Date();
    @ColumnInfo(name = "userId")
    private String userId;
    @ColumnInfo(name = "movieId")
    private int movieId;

    public Reservation(int id, int code, List<String> seats, Date createdDate, String userId, int movieId) {
        this.id = id;
        this.code = code;
        this.seats = seats;
        this.createdDate = createdDate;
        this.userId = userId;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
        return "Reservation{" +
                "id=" + id +
                ", code=" + code +
                ", seats=" + seats +
                ", createdDate=" + createdDate +
                ", userId=" + userId +
                ", movieId=" + movieId +
                '}';
    }
}