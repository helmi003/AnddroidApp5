package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

import com.example.movieapp.Converters.Converters;
import com.example.movieapp.Converters.MovieCategoryConverter;
import com.example.movieapp.Models.Actor;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.ActorMovieJoin;
import com.example.movieapp.Models.MovieCategoryJoin;
import com.example.movieapp.dao.ActorDao;
import com.example.movieapp.dao.MovieDao;
import com.example.movieapp.dao.ActorMovieJoinDao;
import com.example.movieapp.dao.MovieCategoryJoinDao;

// Include all your entities here
@Database(entities = {Actor.class, Movie.class, ActorMovieJoin.class, MovieCategoryJoin.class}, version = 4, exportSchema = false)
@TypeConverters({Converters.class, MovieCategoryConverter.class}) // Register the converters here
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ActorDao actorDao();
    public abstract MovieDao movieDao();
    public abstract ActorMovieJoinDao actorMovieJoinDao();
    public abstract MovieCategoryJoinDao movieCategoryJoinDao();

    // Define your migration with the SQL commands needed for version 2 to 3 changes
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // For example, if you added a new column:
            // database.execSQL("ALTER TABLE Actor ADD COLUMN age INTEGER DEFAULT 0 NOT NULL");
        }
    };

    // Singleton pattern to avoid multiple instances of the database
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .addMigrations(MIGRATION_2_3) // Register the migration here
                    .fallbackToDestructiveMigration() // Optional: fallback for other migrations
                    .build();
        }
        return instance;
    }
}
