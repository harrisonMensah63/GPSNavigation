package com.harryWorld.weatherGPS.weather.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.harryWorld.weatherGPS.weather.repository.WeatherRepository;
import com.harryWorld.weatherGPS.weather.utils.Alert;
import com.harryWorld.weatherGPS.weather.utils.Resource;

import java.util.List;


public class AlertViewModel extends AndroidViewModel {
    private WeatherRepository repository;
    private double longitude;
    private double latitude;

    public AlertViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository();
    }


    public LiveData<Resource<List<Alert>>> getDailyCoordinates(double longitude, double latitude){
        this.latitude = latitude;
        this.longitude = longitude;
        return LiveDataReactiveStreams.fromPublisher(
                repository.getWeatherAlertKo(latitude,longitude)
        );
    }
}