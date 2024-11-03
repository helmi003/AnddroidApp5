package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.movieapp.entities.Season;
import java.util.List;

@Dao
public interface SeasonDAO {
    @Insert
    void createSeason(Season season);
    @Delete
    void deleteSeason(Season season);
    @Update
    void updateSeason(Season season);
    @Query("SELECT * FROM season")
    List<Season> getAllSeasons();
    @Query("SELECT * FROM season where serieId = :id")
    List<Season> getAllSeasonsBySerie(int id);
    @Query("SELECT * FROM season where id = :id")
    Season getSeasonById(int id);
}