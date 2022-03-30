package com.harryWorld.navigationGPS.weather.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.harryWorld.navigationGPS.weather.utils.Current;
import com.harryWorld.navigationGPS.weather.utils.Daily;
import com.harryWorld.navigationGPS.weather.utils.Hourly;



import java.util.List;

import io.reactivex.Single;



@Dao
public interface CurrentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertCurrent(Current current);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertDaily(List<Daily> daily);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertHourly(List<Hourly> hourly);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateCurrent(Current current);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateDaily(List<Daily> daily);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateHourly(List<Hourly> hourly);

    @Delete()
    Single<Integer> deleteCurrent(Current current);

    @Delete()
    Single<Integer> deleteDaily(List<Daily> daily);

    @Delete()
    Single<Integer> deleteHourly(List<Hourly> hourly);

    @Query("SELECT * FROM current")
    LiveData<List<Current>> getCurrent();
    @Query("SELECT * FROM daily")
    LiveData<List<Daily>> getDaily();
    @Query("SELECT * FROM hourly")
    LiveData<List<Hourly>> getHourly();

}
