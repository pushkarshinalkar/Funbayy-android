package com.example.layoutsapp.ModelClasses;

import android.content.Context;

public class Favourites_ModelClass {

    private String MovieName;


    public Favourites_ModelClass(String movName)
    {
        this.MovieName = movName;
    }

    public String getMovieText() {
        return MovieName;
    }

}
