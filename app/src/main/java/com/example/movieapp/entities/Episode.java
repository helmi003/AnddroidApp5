package com.example.movieapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "episode",
        foreignKeys = @ForeignKey(entity = Serie.class,
                parentColumns = "id",
                childColumns = "seasonId",
                onDelete = ForeignKey.CASCADE))
public class Episode implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo()
    private int number;
    @ColumnInfo()
    private String image;
    @ColumnInfo()
    private String video;
    @ColumnInfo(name = "seasonId")
    private int seasonId;

    public Episode(int id, int number, String image, String video, int seasonId) {
        this.id = id;
        this.number = number;
        this.image = image;
        this.video = video;
        this.seasonId = seasonId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", number=" + number +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                ", seasonId=" + seasonId +
                '}';
    }
}
