package com.example.movieapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.entities.Feedback;

import java.util.List;

@Dao
public interface FeedbackDao {

    @Insert
    void insert(Feedback feedback);

    @Update
    void updateFeedback(Feedback feedback);

    @Delete
    void deleteFeedback(Feedback feedback);


    @Query("SELECT * FROM feedback")
    LiveData<List<Feedback>> getAllFeedbacks();

    // Retourner un LiveData pour observer les changements d'un feedback spécifique
    @Query("SELECT * FROM feedback WHERE id = :id")
    LiveData<Feedback> getFeedbackById(int id);
}

