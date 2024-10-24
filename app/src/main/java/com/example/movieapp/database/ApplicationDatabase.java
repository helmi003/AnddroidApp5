package com.example.movieapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieapp.DAO.UserDAO;
import com.example.movieapp.entities.User;

@Database(entities = {User.class}, version = 1,exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase instance;
    public abstract UserDAO userDAO();

    public static ApplicationDatabase getAppDatabase (Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ApplicationDatabase.class,"movieDB")
                            .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
