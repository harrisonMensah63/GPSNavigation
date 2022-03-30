package com.harryWorld.navigationGPS.map.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.harryWorld.navigationGPS.map.persistance.PlacesDao;
import com.harryWorld.navigationGPS.map.persistance.PlacesDatabase;
import com.harryWorld.navigationGPS.map.utils.Places;
import com.harryWorld.navigationGPS.schedule.Alarm;
import com.harryWorld.navigationGPS.schedule.Schedule;
import com.harryWorld.navigationGPS.weather.utils.Resource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PlacesRepository {

    private static final String INSERT_ERROR="there was an error inserting places";
    private static final String UPDATE_ERROR="there was an error updating places";

    private final PlacesDao placesDao;
    private static PlacesRepository repository;
    Places places;
    Schedule schedule;
    Alarm alarm;

    public PlacesRepository(Context context) {
        placesDao = PlacesDatabase.getInstance(context).placesDao();
        places = new Places();
        schedule = new Schedule();
        alarm = new Alarm();
    }

    public static PlacesRepository getRepository(Context context){
        if (repository == null){
            repository = new PlacesRepository(context);
        }
        return repository;
    }
    public Flowable<Resource<Long>> insertingPlaces(Places places){
        return placesDao.insertPlaces(places)
                .onErrorReturn(new Function<Throwable, Long>() {
                    @NonNull
                    @Override
                    public Long apply(@NonNull Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        places.setErrorMessage(name);
                        return -1L;
                    }
                })
                .map(new Function<Long, Resource<Long>>() {
                    @NonNull
                    @Override
                    public Resource<Long> apply(@NonNull Long aLong) throws Exception {
                        if (aLong != -1L){
                            return Resource.success(aLong,null);
                        }
                        else {
                            return Resource.error(aLong,places.getErrorMessage());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }

    public Flowable<Resource<Long>> insertingSchedule(Schedule schedule){
        return placesDao.insertSchedule(schedule)
                .onErrorReturn(new Function<Throwable, Long>() {
                    @NonNull
                    @Override
                    public Long apply(@NonNull Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        PlacesRepository.this.schedule.setErrorMessage(name);
                        return -1L;
                    }
                })
                .map(new Function<Long, Resource<Long>>() {
                    @NonNull
                    @Override
                    public Resource<Long> apply(@NonNull Long aLong) throws Exception {
                        if (aLong != -1L){
                            return Resource.success(aLong,null);
                        }
                        else {
                            return Resource.error(aLong, PlacesRepository.this.schedule.getErrorMessage());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }
    public Flowable<Resource<Long>> insertingAlarm(Alarm alarm){
        return placesDao.insertAlarm(alarm)
                .onErrorReturn(new Function<Throwable, Long>() {
                    @NonNull
                    @Override
                    public Long apply(@NonNull Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                       alarm.setErrorMessage(name);
                        return -1L;
                    }
                })
                .map(new Function<Long, Resource<Long>>() {
                    @NonNull
                    @Override
                    public Resource<Long> apply(@NonNull Long aLong) throws Exception {
                        if (aLong != -1L){
                            return Resource.success(aLong,null);
                        }
                        else {
                            return Resource.error(aLong, alarm.getErrorMessage());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }

    public Flowable<Resource<Integer>> updatingPlaces(Places places){
        return placesDao.updatePlaces(places)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @NonNull
                    @Override
                    public Resource<Integer> apply(@NonNull Integer integer) throws Exception {
                        if (integer != -1){
                            return Resource.success(integer,null);
                        }
                        else {
                            return Resource.error(integer,UPDATE_ERROR);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }
    public Flowable<Resource<Integer>> updatingPlaces(Schedule schedule1){
        return placesDao.updateSchedule(schedule1)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @NonNull
                    @Override
                    public Resource<Integer> apply(@NonNull Integer integer) throws Exception {
                        if (integer != -1){
                            return Resource.success(integer,null);
                        }
                        else {
                            return Resource.error(integer,UPDATE_ERROR);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }
    public Flowable<Resource<Integer>> updatingAlarm(Alarm alarm){
        return placesDao.updateAlarm(alarm)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        alarm.setErrorMessage(name);
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @NonNull
                    @Override
                    public Resource<Integer> apply(@NonNull Integer integer) throws Exception {
                        if (integer != -1){
                            return Resource.success(integer,alarm.getErrorMessage());
                        }
                        else {
                            return Resource.error(integer,alarm.getErrorMessage());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }

    public Flowable<Resource<Integer>> deletingPlaces(Places places){
        return placesDao.deletePlaces(places)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @NonNull
                    @Override
                    public Resource<Integer> apply(@NonNull Integer integer) throws Exception {
                        if (integer != -1){
                            return Resource.success(integer,null);
                        }
                        else {
                            return Resource.error(integer,UPDATE_ERROR);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }
    public Flowable<Resource<Integer>> deletingSchedule(Schedule schedule1){
        return placesDao.deleteSchedule(schedule1)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @NonNull
                    @Override
                    public Resource<Integer> apply(@NonNull Integer integer) throws Exception {
                        if (integer != -1){
                            return Resource.success(integer,null);
                        }
                        else {
                            return Resource.error(integer,UPDATE_ERROR);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }
    public Flowable<Resource<Integer>> deletingAlarm(Alarm alarm){
        return placesDao.deleteALarm(alarm)
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        alarm.setErrorMessage(name);
                        return -1;
                    }
                })
                .map(new Function<Integer, Resource<Integer>>() {
                    @NonNull
                    @Override
                    public Resource<Integer> apply(@NonNull Integer integer) throws Exception {
                        if (integer != -1){
                            return Resource.success(integer,alarm.getErrorMessage());
                        }
                        else {
                            return Resource.error(integer,alarm.getErrorMessage());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }

    public LiveData<List<Places>> gettingPLaces(){
        return placesDao.retrievePlaces();
    }

    public LiveData<List<Schedule>> gettingSchedule(){
        return placesDao.retrieveSchedule();
    }
    public LiveData<List<Alarm>> gettingAlarm(){
        return placesDao.retrieveAlarm();
    }


}


















