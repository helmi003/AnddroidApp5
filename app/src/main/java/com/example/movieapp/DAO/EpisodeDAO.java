package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Season;

import java.util.List;
@Dao
public interface EpisodeDAO {
    @Insert
    void createEpisode(Episode episode);
    @Delete
    void deleteEpisode(Episode episode);
    @Update
    void updateEpisode(Episode episode);
    @Query("SELECT * FROM episode")
    List<Episode> getAllEpisodes();
    @Query("SELECT * FROM episode where id = :id")
    Episode getEpisodeById(int id);
    @Query("SELECT * FROM episode where seasonId = :id")
    List<Episode> getAllEpisodessBySeason(int id);
}
