package com.example.movieapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.movieapp.DAO.EpisodeDAO;
import com.example.movieapp.DAO.MovieDAO;
import com.example.movieapp.DAO.ReservationDAO;
import com.example.movieapp.DAO.SeasonDAO;
import com.example.movieapp.DAO.SerieDAO;
import com.example.movieapp.DAO.UserDAO;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.ActorMovieJoin;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.MovieCategoryJoin;
import com.example.movieapp.converters.Converters;
import com.example.movieapp.converters.MovieCategoryConverter;
import com.example.movieapp.DAO.ActorDao;
import com.example.movieapp.DAO.ActorMovieJoinDao;
import com.example.movieapp.DAO.MovieCategoryJoinDao;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Reservation;
import com.example.movieapp.entities.Season;
import com.example.movieapp.entities.Serie;
import com.example.movieapp.entities.User;

@Database(
        entities = {
                User.class,
                Reservation.class,
                Movie.class,
                Serie.class,
                Season.class,
                Episode.class,
                Actor.class,
                ActorMovieJoin.class,
                MovieCategoryJoin.class
        },
        version = 6,
        exportSchema = false
)
@TypeConverters({Converters.class, MovieCategoryConverter.class})
public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase instance;

    public abstract UserDAO userDAO();
    public abstract SerieDAO serieDAO();
    public abstract SeasonDAO seasonDAO();
    public abstract EpisodeDAO episodeDAO();
    public abstract MovieDAO movieDAO();
    public abstract ReservationDAO reservationDAO();
    public abstract ActorDao actorDao();
    public abstract ActorMovieJoinDao actorMovieJoinDao();
    public abstract MovieCategoryJoinDao movieCategoryJoinDao();

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
