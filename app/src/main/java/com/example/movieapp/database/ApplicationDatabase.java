package com.example.movieapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.movieapp.DAO.EpisodeDAO;
import com.example.movieapp.DAO.FeedbackDao;
import com.example.movieapp.DAO.MovieDAO;
import com.example.movieapp.DAO.ReservationDAO;
import com.example.movieapp.DAO.SeasonDAO;
import com.example.movieapp.DAO.SerieDAO;
import com.example.movieapp.DAO.UserDAO;
import com.example.movieapp.converters.Converters;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Feedback;
import com.example.movieapp.entities.Movie;
import com.example.movieapp.entities.Reservation;
import com.example.movieapp.entities.Role;
import com.example.movieapp.entities.Season;
import com.example.movieapp.entities.Serie;
import com.example.movieapp.entities.User;
import com.google.android.gms.common.Feature;

@Database(entities = {User.class, Reservation.class, Movie.class, Serie.class, Season.class, Episode.class , Feedback.class}, version = 7, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase instance;
    public abstract FeedbackDao feedbackDao();
    public abstract UserDAO userDAO();
    public abstract SerieDAO serieDAO();
    public abstract SeasonDAO seasonDAO();
    public abstract EpisodeDAO episodeDAO();
    public abstract MovieDAO movieDAO();
    public abstract ReservationDAO reservationDAO();

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
