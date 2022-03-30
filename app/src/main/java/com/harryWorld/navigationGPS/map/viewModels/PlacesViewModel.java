package com.harryWorld.navigationGPS.map.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.harryWorld.navigationGPS.map.repository.PlacesRepository;
import com.harryWorld.navigationGPS.map.utils.Places;
import com.harryWorld.navigationGPS.schedule.Alarm;
import com.harryWorld.navigationGPS.schedule.Schedule;
import com.harryWorld.navigationGPS.weather.repository.WeatherRepository;
import com.harryWorld.navigationGPS.weather.utils.Resource;
import com.harryWorld.navigationGPS.weather.utils.Weather;

import java.util.List;

public class PlacesViewModel extends AndroidViewModel {
    private static final String TAG = "PlacesViewModel";
    private final PlacesRepository repository;
    public LiveData<List<Places>> getPlaces;
    public LiveData<List<Schedule>> getSchedule;
    public LiveData<List<Alarm>> getAlarm;
    private WeatherRepository weatherRepository;
    private MediatorLiveData<Resource<List<Schedule>>> insertPlaces = new MediatorLiveData<>();

    public PlacesViewModel(@NonNull Application application) {
        super(application);
        repository= PlacesRepository.getRepository(application);
        weatherRepository = WeatherRepository.getWeatherRepository();
        getPlaces = repository.gettingPLaces();
        getAlarm = repository.gettingAlarm();
        getSchedule = repository.gettingSchedule();
    }

    public LiveData<Resource<List<Weather>>> setWeatherRepository(double latitude, double longitude){
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherCurrentKo(latitude,longitude)
        );
    }
    public LiveData<Resource<Long>> gettingInsert(Places places){
        return LiveDataReactiveStreams.fromPublisher(
                repository.insertingPlaces(places)
        );
    }

    public LiveData<Resource<Long>> insertingSchedule(Schedule schedule){
        return LiveDataReactiveStreams.fromPublisher(
                repository.insertingSchedule(schedule)
        );
    }

    public LiveData<Resource<Long>> insertingAlarm(Alarm alarm){
        return LiveDataReactiveStreams.fromPublisher(
                repository.insertingAlarm(alarm)
        );
    }
    public LiveData<Resource<Integer>> updatingPlaces(Places places){
        return LiveDataReactiveStreams.fromPublisher(
                repository.updatingPlaces(places)
        );
    }
    public LiveData<Resource<Integer>> updatingSchedule(Schedule schedule){
        return LiveDataReactiveStreams.fromPublisher(
                repository.updatingPlaces(schedule)
        );
    }

    public LiveData<Resource<Integer>> updatingAlarm(Alarm alarm){
        return LiveDataReactiveStreams.fromPublisher(
                repository.updatingAlarm(alarm)
        );
    }
    public LiveData<Resource<Integer>> deletingPlaces(Places places){
        return LiveDataReactiveStreams.fromPublisher(
                repository.deletingPlaces(places)
        );
    }
    public LiveData<Resource<Integer>> deletingSchedule(Schedule schedule){
        return LiveDataReactiveStreams.fromPublisher(
                repository.deletingSchedule(schedule)
        );
    }
    public LiveData<Resource<Integer>> deletingAlarm(Alarm alarm){
        return LiveDataReactiveStreams.fromPublisher(
                repository.deletingAlarm(alarm)
        );
    }
}
