package com.example.layoutsapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDaoSer {


    @Query("SELECT * FROM userser")
    List<UserSer> getAllUsers();

    @Insert
    void insertUser(com.example.layoutsapp.Database.UserSer... users);

    @Delete
    void delete(com.example.layoutsapp.Database.UserSer user);

    @Query("DELETE FROM UserSer WHERE series_name = :serName")
    abstract void  deleteBySeriesUserId(String serName);

    @Query("SELECT EXISTS(SELECT * FROM UserSer WHERE series_id= :seriesID)")
    boolean isSeriesLiked(String seriesID);
}
