package com.example.layoutsapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<com.example.layoutsapp.Database.User> getAllUsers();

    @Insert
    void insertUser(com.example.layoutsapp.Database.User... users);

    @Delete
    void delete(com.example.layoutsapp.Database.User user);

    @Query("DELETE FROM User WHERE movie_name = :movName")
    abstract void  deleteByUserId(String movName);

    @Query("SELECT EXISTS(SELECT * FROM User WHERE movie_id= :movieID)")
    boolean isMovieLiked(String movieID);

}