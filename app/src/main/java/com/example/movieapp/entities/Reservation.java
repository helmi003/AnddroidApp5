package com.example.movieapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "reservation",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        ))
public class Reservation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int code;
    @ColumnInfo(name = "places")
    private List<String> places;
    @ColumnInfo(name = "userId")
    private int userId;

    public Reservation(int id, int code, List<String> places, int userId) {
        this.id = id;
        this.code = code;
        this.places = places;
        this.userId = userId;
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

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", code=" + code +
                ", places=" + places +
                ", userId=" + userId +
                '}';
    }
}