package com.example.jbs.room;

import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProfileDao {
    @Insert(onConflict = REPLACE)
    void save(Profile profile);

    @Query("SELECT * FROM Profile WHERE ProfileUID = :profileUID")
    LiveData<Profile> load(String profileUID);

    @Query("SELECT * FROM Profile WHERE ProfileUID = :profileUID AND lastRefresh > :lastRefreshMax LIMIT 1")
    Profile hasProfile(String profileUID, Date lastRefreshMax);
}
