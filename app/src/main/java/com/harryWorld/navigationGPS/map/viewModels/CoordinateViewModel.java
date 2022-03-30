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
import com.harryWorld.navigationGPS.weather.utils.Resource;

import java.util.List;

public class CoordinateViewModel extends AndroidViewModel {
    private CoordinatesRepository repository;
    private String name;
    private boolean slideUp;


    public CoordinateViewModel(@NonNull Application application) {
        super(application);
        repository = CoordinatesRepository.getInstance(application);
    }


    public LiveData<Resource<List<Coordinate>>> getCoordinates(String cityName){
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

    public boolean isSlideUp() {
        return slideUp;
    }

    public void setSlideUp(boolean slideUp) {
        this.slideUp = slideUp;
    }
}
