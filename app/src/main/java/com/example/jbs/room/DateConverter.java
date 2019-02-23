package com.example.jbs.room;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }
    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
