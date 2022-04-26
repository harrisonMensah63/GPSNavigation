package com.harryWorld.navigationGPS.map.viewModels;

import android.app.Application;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.harryWorld.navigationGPS.map.repository.CoordinatesRepository;
import com.harryWorld.navigationGPS.map.utils.Coordinate;
import com.harryWorld.navigationGPS.map.utils.direction.Directions;
import com.harryWorld.navigationGPS.weather.repository.WeatherRepository;
import com.harryWorld.navigationGPS.weather.utils.Resource;

import java.util.List;

import io.reactivex.Flowable;

public class CoordinateViewModel extends AndroidViewModel {
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
                WeatherRepository.getWeatherRepository().getOpenDirections(mode, start, end, application);
    }
    public LiveData<Directions> getDirectionsLive(){
       return weatherRepository.liveDirections;
    }
}
