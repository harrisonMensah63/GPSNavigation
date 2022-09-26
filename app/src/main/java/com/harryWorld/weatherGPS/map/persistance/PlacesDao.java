package com.harryWorld.weatherGPS.map.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.harryWorld.weatherGPS.map.utils.Places;
import com.harryWorld.weatherGPS.schedule.Alarm;
import com.harryWorld.weatherGPS.schedule.Schedule;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PlacesDao {
    @Insert
    Single<Long> insertPlaces(Places places);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertSchedule(Schedule schedule);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertAlarm(Alarm alarm);

    @Update
    Single<Integer> updatePlaces(Places places);
    @Update
    Single<Integer> updateSchedule(Schedule schedule);
    @Update
    Single<Integer> updateAlarm(Alarm alarm);

    @Delete
    Single<Integer> deletePlaces(Places places);
    @Delete
    Single<Integer> deleteSchedule(Schedule schedule);
    @Delete
    Single<Integer> deleteALarm(Alarm alarm);

    @Query("SELECT * FROM Places")
    LiveData<List<Places>> retrievePlaces();
    @Query("SELECT * FROM schedule")
    LiveData<List<Schedule>> retrieveSchedule();
    @Query("SELECT * FROM alarm")
    LiveData<List<Alarm>> retrieveAlarm();
}
