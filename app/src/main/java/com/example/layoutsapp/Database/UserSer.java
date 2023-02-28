package com.example.layoutsapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserSer {

    @PrimaryKey(autoGenerate = true)
    public int uidSer;
    // Favourite Series User

    @ColumnInfo(name = "series_name")
    public String seriesName;

    @ColumnInfo(name = "noOfSeasons")
    public String noOfSeasons;

    @ColumnInfo(name = "series_id")
    public String seriesID;

    @ColumnInfo(name = "img_link_series_small")
    public String imageLinkSeriesSmall;
}
