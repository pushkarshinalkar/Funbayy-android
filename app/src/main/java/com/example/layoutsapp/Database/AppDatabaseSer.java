package com.example.layoutsapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.layoutsapp.Database.UserSer.class},version = 1)
public abstract class AppDatabaseSer extends RoomDatabase {

    public abstract com.example.layoutsapp.Database.UserDaoSer userDaoSer();

    private static AppDatabaseSer INSTANCE;

    public static AppDatabaseSer getDbInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseSer.class, "DB_NAME_SER")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}
