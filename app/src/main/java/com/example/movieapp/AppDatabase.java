package com.example.movieapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;
import android.content.Context;

import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.ActorMovieJoin;
import com.example.movieapp.Models.MovieCategoryJoin;
import com.example.movieapp.dao.ActorDao;
import com.example.movieapp.dao.MovieDao;
import com.example.movieapp.dao.ActorMovieJoinDao;
import com.example.movieapp.dao.MovieCategoryJoinDao;

// Include all your entities here
@Database(entities = {Actor.class, Movie.class, ActorMovieJoin.class, MovieCategoryJoin.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    // DAOs for each entity
    public abstract ActorDao actorDao();
    public abstract MovieDao movieDao();
    public abstract ActorMovieJoinDao actorMovieJoinDao();
    public abstract MovieCategoryJoinDao movieCategoryJoinDao();

    // Singleton pattern to avoid multiple instances of the database
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration() // To handle schema changes
                    .build();
        }
        return instance;
    }
}
