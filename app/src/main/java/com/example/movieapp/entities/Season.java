package com.example.movieapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "season",
        foreignKeys = @ForeignKey(entity = Serie.class,
                parentColumns = "id",
                childColumns = "serieId",
                onDelete = ForeignKey.CASCADE))
public class Season implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo()
    private int number;
    @ColumnInfo(name = "serieId")
    private int serieId;

    public Season(int id, int number, int serieId) {
        this.id = id;
        this.number = number;
        this.serieId = serieId;
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

    public int getSerieId() {
        return serieId;
    }

    public void setSerieId(int serieId) {
        this.serieId = serieId;
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", number=" + number +
                ", serieId=" + serieId +
                '}';
    }
}
