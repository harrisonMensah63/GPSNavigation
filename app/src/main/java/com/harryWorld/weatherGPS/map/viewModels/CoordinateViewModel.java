package com.harryWorld.weatherGPS.map.viewModels;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.harryWorld.weatherGPS.map.repository.CoordinatesRepository;
import com.harryWorld.weatherGPS.map.utils.Coordinate;
import com.harryWorld.weatherGPS.map.utils.direction.Directions;
import com.harryWorld.weatherGPS.weather.repository.WeatherRepository;
import com.harryWorld.weatherGPS.weather.utils.Resource;

import java.util.List;

public class CoordinateViewModel extends AndroidViewModel {
    private static final String TAG = "CoordinateViewModel";
    private CoordinatesRepository repository;
    private String name;
    private boolean slideUp;
    private Application application;
    public WeatherRepository weatherRepository;


    public CoordinateViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = CoordinatesRepository.getInstance(application);
        weatherRepository = WeatherRepository.getWeatherRepository();
    }


    public LiveData<Resource<List<List<Coordinate>>>> getCoordinates(String cityName){
        return LiveDataReactiveStreams.fromPublisher(
                repository.getCoordinates(cityName)
        );
    }
    public void slideUp(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        }, 500);

    }

    public void slideDown1(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        }, 500);
    }

    public void getOpenDirections(String mode, String start, String end){
        Log.d(TAG, "getOpenDirections: start "+start);
        Log.d(TAG, "getOpenDirections: end "+end);
                WeatherRepository.getWeatherRepository().getOpenDirections(mode, start, end, application);
    }
    public LiveData<Directions> getDirectionsLive(){
       return weatherRepository.liveDirections;
    }
}
