package com.harryWorld.weatherGPS.map.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.harryWorld.weatherGPS.map.utils.Places;
import com.harryWorld.weatherGPS.schedule.Alarm;
import com.harryWorld.weatherGPS.schedule.Schedule;

@Database(entities = {Places.class, Schedule.class, Alarm.class},version = 12)
public abstract class PlacesDatabase extends RoomDatabase {
    private static final String PLACES= "PlacesDatabase";

    private static PlacesDatabase instance;

    public  static PlacesDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PlacesDatabase.class,
                    PLACES
            )
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }

    public abstract PlacesDao placesDao();
}
