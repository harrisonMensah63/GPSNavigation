package com.harryWorld.navigationGPS.weather.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import com.harryWorld.navigationGPS.weather.repository.Repository;
import com.harryWorld.navigationGPS.weather.repository.WeatherRepository;
import com.harryWorld.navigationGPS.weather.utils.Current;
import com.harryWorld.navigationGPS.weather.utils.Resource;
import com.harryWorld.navigationGPS.weather.utils.Weather;


import java.util.List;

public class CurrentViewModel extends AndroidViewModel {
    private static final String TAG = "CurrentViewModel";
    private Repository repository;
    private WeatherRepository weatherRepository;

    private MutableLiveData<Weather> currentData;
    private MutableLiveData<Boolean> displayWidgets;
    LiveData<List<Current>> retrieveData;
    MutableLiveData<String> retrieveString;
    public MutableLiveData<CurrentInsertMode> currentInsertMode = new MutableLiveData<>();
    long l =System.currentTimeMillis()/1000;
    private int timeStamp = (int) l;

    private double longitude, latitude;

    public CurrentViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = new WeatherRepository();
        repository = new Repository(application);
        retrieveData = repository.getCurrentData();
        Log.d(TAG, "CurrentViewModel: getTimeStamp "+timeStamp);
    }

    public LiveData<List<Current>> getRetrieveData(){
        return retrieveData;
    }

    public LiveData<Resource<List<Weather>>> getCurrentCoordinates(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherCurrentKo(latitude, longitude)
        );
    }


    public LiveData<Resource<Integer>> insertCurrent(Weather weather, Current current) {
        setInsert(weather, current);
      //  Log.d(TAG, "insertWeather: testing current"+" "+current.getCityName());
       return LiveDataReactiveStreams.fromPublisher(
               repository.insertCurrentDao(current)
       );
    }

    public enum weatherSource {coordinates,city}
    private void setInsert(Weather getWeather, Current current) {
            current.setCityName(getWeather.getCityName());
       // Log.d(TAG, "setInsert: getting cityName"+" "+current.getCityName());
            current.setCurrentPressure(getWeather.getPressure());
            current.setCurrentWindSpeed(getWeather.getWindSpeed());
            current.setCurrentWindDirection(getWeather.getWindDirection());
            current.setCurrentTemp(getWeather.getTemp());
//            current.setMin_temp_current(getWeather.getMin_temp());
//            current.setMax_temp_current(getWeather.getMax_temp());
            current.setCurrentClouds(getWeather.getClouds());
            current.setCurrentDescription(getWeather.getDescription());
            current.setCurrentUvIndex(getWeather.getUvIndex());
            current.setCurrentFeelsLike(getWeather.getFeelsLike());
            current.setTimeStamp(timeStamp);
            current.setCurrentVisibility(getWeather.getVisibility());
            current.setCurrentSunrise(getWeather.getSunrise());
            current.setCurrentSunset(getWeather.getSunset());
            current.setCurrentTime(getWeather.getWeatherTime());
    }



    public LiveData<Resource<Integer>> updateCurrent(Weather weather, Current current){
        setInsert(weather,current);
        return LiveDataReactiveStreams.fromPublisher(
                repository.updatingCurrent(current)
        );
    }

    public LiveData<Resource<Integer>> deletingCurrent(Current current){
        return LiveDataReactiveStreams.fromPublisher(
                repository.deletingCurrent(current)
        );
    }
    public void setCurrentData(Weather currentData){
        if (this.currentData == null){
            this.currentData = new MutableLiveData<>();
        }
        this.currentData.setValue(currentData);
    }

    public void setRetrieveString(String retrieveString){
        if (this.retrieveString == null){
            this.retrieveString = new MutableLiveData<>();
        }
        this.retrieveString.setValue(retrieveString);
    }
    public void setDisplayWidgets(boolean b){
        if (displayWidgets == null){
            displayWidgets = new MutableLiveData<>();
        }
        displayWidgets.setValue(b);
    }
    public MutableLiveData<Weather> getCurrentData(){
        if (currentData == null){
            currentData = new MutableLiveData<>();
        }
        return currentData;
    }
    public MutableLiveData<String> getRetrieveString(){
        if (retrieveString == null){
            retrieveString = new MutableLiveData<>();
        }
        return retrieveString;
    }

    public MutableLiveData<Boolean> getDisplayWidgets() {
        if (displayWidgets == null){
            displayWidgets = new MutableLiveData<>();
        }
        return displayWidgets;
    }
    public LiveData<CurrentInsertMode> getCurrentInsertMode(){
        return currentInsertMode;
    }
    public void setCurrentInsertMode(MutableLiveData<CurrentInsertMode> currentInsertMode){
        this.currentInsertMode = currentInsertMode;
    }
    public enum CurrentInsertMode{AVAILABLE,ABORT,ERROR}
}