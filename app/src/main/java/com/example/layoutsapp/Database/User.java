package com.example.layoutsapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    // Favourite Movies User
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "movie_name")
    public String movieName;

    @ColumnInfo(name = "release_year")
    public String releaseYear;

    @ColumnInfo(name = "movie_id")
    public String movieID;

    @ColumnInfo(name = "img_link_movie_small")
    public String imageLinkMovieSmall;



}