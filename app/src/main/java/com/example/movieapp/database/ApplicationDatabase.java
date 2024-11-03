package com.example.movieapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.movieapp.DAO.EpisodeDAO;
import com.example.movieapp.DAO.SeasonDAO;
import com.example.movieapp.DAO.SerieDAO;
import com.example.movieapp.DAO.UserDAO;
import com.example.movieapp.converters.Converters;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Reservation;
import com.example.movieapp.entities.Role;
import com.example.movieapp.entities.Season;
import com.example.movieapp.entities.Serie;
import com.example.movieapp.entities.User;

@Database(entities = {Serie.class, User.class, Reservation.class, Season.class, Episode.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase instance;

    public abstract UserDAO userDAO();
    public abstract SerieDAO serieDAO();
    public abstract SeasonDAO seasonDAO();
    public abstract EpisodeDAO episodeDAO();

    public static ApplicationDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ApplicationDatabase.class, "movieDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
