package com.harryWorld.weatherGPS.weather.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.harryWorld.weatherGPS.weather.constant.Converters;
import com.harryWorld.weatherGPS.weather.utils.Current;
import com.harryWorld.weatherGPS.weather.utils.Daily;
import com.harryWorld.weatherGPS.weather.utils.Hourly;


@Database(entities = {Current.class, Daily.class, Hourly.class},version = 16)
//@TypeConverters({ConvertingDaily.class, ConvertingHourly.class})
@TypeConverters(Converters.class)
public abstract class NoteDataBase extends RoomDatabase {
    public static final String DATABASE_NAME="name_database";

    private static NoteDataBase instance;
   public static NoteDataBase getInstance(final Context context){
       if (instance == null) {
           return Room.databaseBuilder(
                   context.getApplicationContext(),
                   NoteDataBase.class,
                   DATABASE_NAME
           )
                   .fallbackToDestructiveMigration()
                   .build();
       }
       return instance;
    }
    public abstract CurrentDao getCurrentDao();
}