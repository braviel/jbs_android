package com.example.jbs.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import androidx.room.TypeConverters;

@Database(entities = {Profile.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class LocalDatabase extends RoomDatabase {
    private static volatile LocalDatabase INSTANCE;
    // -- DAO --
    public abstract ProfileDao profileDao();

}
