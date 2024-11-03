package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.movieapp.entities.Serie;

import java.util.List;
@Dao
public interface SerieDAO {
    @Insert
    void createSerie(Serie serie);
    @Delete
    void deleteSerie(Serie serie);
    @Update
    void updateSerie(Serie serie);
    @Query("SELECT * FROM serie")
    List<Serie> getAllSeries();
    @Query("SELECT * FROM serie where id = :id")
    Serie getSerieById(int id);
}
