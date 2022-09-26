package com.harryWorld.weatherGPS.weather.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.harryWorld.weatherGPS.weather.persistance.CurrentDao;
import com.harryWorld.weatherGPS.weather.persistance.NoteDataBase;
import com.harryWorld.weatherGPS.weather.utils.Alert;
import com.harryWorld.weatherGPS.weather.utils.Current;
import com.harryWorld.weatherGPS.weather.utils.Daily;
import com.harryWorld.weatherGPS.weather.utils.Hourly;
import com.harryWorld.weatherGPS.weather.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Repository {
    private CurrentDao currentDao;
    private Current current1;
    private Daily daily;
    private Hourly hourly;
    Alert alert;
    private NoteDataBase noteDataBase;

    public Repository(Context context) {
         current1 = new Current();
         alert = new Alert();

        if (noteDataBase == null){
            noteDataBase = NoteDataBase.getInstance(context);
        }
         currentDao = noteDataBase.getCurrentDao();
        current1 = new Current();
        daily = new Daily();
        hourly = new Hourly();
    }


///////////////////////////////////////////////////////////////////////////////////
// Usage


    public LiveData<List<Current>> getCurrentData(){
        return currentDao.getCurrent();
    }
    public LiveData<List<Daily>> getDailyData(){
        return currentDao.getDaily();
    }
    public LiveData<List<Hourly>> getHourlyData(){
        return currentDao.getHourly();
    }

    public Flowable<Resource<Integer>> insertCurrentDao(Current current) {
       return currentDao.insertCurrent(current)
               .map(new Function<Long, Integer>() {
                   @Override
                   public Integer apply(Long aLong) throws Exception {
                       return aLong.intValue();
                   }
               })
      .onErrorReturn(new Function<Throwable, Integer>() {
          @Override
          public Integer apply(Throwable throwable) throws Exception {
              return -1;
          }
      })
               .map(new Function<Integer, Resource<Integer>>() {
                   @Override
                   public Resource<Integer> apply(Integer integer) throws Exception {
                       if (integer == -1) {
                           return Resource.error(integer,"ERROR INSERTING DATA");
                       }
                       return Resource.success(integer," INSERT SUCCESS");
                   }
               })
               .toFlowable()
               .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<List<Long>>> insertDailyDao(List<Daily> daily) {
        return currentDao.insertDaily(daily)
                .onErrorReturn(new Function<Throwable, List<Long>>() {
                    @Override
                    public List<Long> apply(Throwable throwable) throws Exception {
                        List<Long> longs = new ArrayList<>();
                        longs.add(-1L);
                        return longs;
                    }
                })
                .map(new Function<List<Long>, Resource<List<Long>>>() {
                    @Override
                    public Resource<List<Long>> apply(List<Long> longs) throws Exception {
                        if (longs.get(0)> 0){
                            return Resource.success(longs,null);
                        }
                        else {
                         return    Resource.error(longs,null);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }
    public Flowable<Resource<List<Long>>> insertHourlyDao(List<Hourly> hourly) {
        return currentDao.insertHourly(hourly)
                .onErrorReturn(new Function<Throwable, List<Long>>() {
                    @Override
                    public List<Long> apply(Throwable throwable) throws Exception {
                        List<Long> longs = new ArrayList<>();
                        longs.add(-1L);
                        return longs;
                    }
                })
                .map(new Function<List<Long>, Resource<List<Long>>>() {
                    @Override
                    public Resource<List<Long>> apply(List<Long> longs) throws Exception {
                        if (longs.get(0)> 0){
                            return Resource.success(longs,null);
                        }
                        else {
                            return    Resource.error(longs,null);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }



    public Flowable<Resource<Integer>> updatingCurrent(Current current){
        return currentDao.updateCurrent(current)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        current1.setErrorMessage(throwable.getMessage());
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @Override
                    public Resource<Integer> apply(Integer integer) throws Exception {
                        if (integer != null){
                            return Resource.success(integer,"INSERT_SUCCESS");
                        }
                        return Resource.error(integer,current1.getErrorMessage());
                    }
                })
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<Integer>> updatingDaily(List<Daily> daily1){
        return currentDao.updateDaily(daily1)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        hourly.setErrorMessage(throwable.getMessage());
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @Override
                    public Resource<Integer> apply(Integer integer) throws Exception {
                        if (integer != null){
                            return Resource.success(integer,"INSERT_SUCCESS");
                        }
                        return Resource.error(integer,hourly.getErrorMessage());
                    }
                })
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<Integer>> updatingHourly(List<Hourly> hourly1){
        return currentDao.updateHourly(hourly1)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        hourly.setErrorMessage(throwable.getMessage());
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @Override
                    public Resource<Integer> apply(Integer integer) throws Exception {
                        if (integer != null){
                            return Resource.success(integer,"INSERT_SUCCESS");
                        }
                        return Resource.error(integer,hourly.getErrorMessage());
                    }
                })
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }



    public Flowable<Resource<Integer>> deletingCurrent(Current current){
        return currentDao.deleteCurrent(current)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        current1.setErrorMessage(throwable.getMessage());
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @Override
                    public Resource<Integer> apply(Integer integer) throws Exception {
                        if (integer != null){
                            return Resource.success(integer,"INSERT_SUCCESS");
                        }
                        return Resource.error(integer,current1.getErrorMessage());
                    }
                })
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<Integer>> deletingDaily(List<Daily> daily1){
        return currentDao.deleteDaily(daily1)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        daily.setErrorMessage(throwable.getMessage());
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @Override
                    public Resource<Integer> apply(Integer integer) throws Exception {
                        if (integer != null){
                            return Resource.success(integer,"INSERT_SUCCESS");
                        }
                        return Resource.error(integer,daily.getErrorMessage());
                    }
                })
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<Integer>> deletingHourly(List<Hourly> hourly1){
        return currentDao.deleteHourly(hourly1)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        hourly.setErrorMessage(throwable.getMessage());
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @Override
                    public Resource<Integer> apply(Integer integer) throws Exception {
                        if (integer != null){
                            return Resource.success(integer,"INSERT_SUCCESS");
                        }
                        return Resource.error(integer,hourly.getErrorMessage());
                    }
                })
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }




}
