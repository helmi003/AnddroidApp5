package com.example.movieapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void createUser(User user);
    @Delete
    void deleteUser(User user);
    @Update
    void updateUser(User user);
    @Query("SELECT * FROM user")
    List<User> getAllUsers();
    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    User getUserById(String id);

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("UPDATE user SET is_blocked = :isBlocked WHERE id = :id")
    void toggleBlockUser(String id, boolean isBlocked);
}
