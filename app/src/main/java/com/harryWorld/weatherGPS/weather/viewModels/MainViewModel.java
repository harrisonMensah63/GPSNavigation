package com.harryWorld.weatherGPS.weather.viewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;


import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.retrofit.GO;
import com.harryWorld.weatherGPS.map.retrofit.Geocode;
import com.harryWorld.weatherGPS.map.volley.JSONConnector;
import com.harryWorld.weatherGPS.weather.api.Climate;
import com.harryWorld.weatherGPS.weather.repository.Repository;
import com.harryWorld.weatherGPS.weather.repository.WeatherRepository;
import com.harryWorld.weatherGPS.weather.utils.Current;
import com.harryWorld.weatherGPS.weather.utils.Resource;
import com.harryWorld.weatherGPS.weather.utils.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";

    private final MutableLiveData<Weather> currentWether = new MutableLiveData<>();
    private Repository repository;
    private WeatherRepository weatherRepository;
    private Context mContext;
    private MutableLiveData<Climate> retrieveClimate;
    private MutableLiveData<Boolean> isConnected  = new MutableLiveData<>();
    public JSONConnector connector;

    private LiveData<List<Current>> retrieveData;
    private MutableLiveData<Climate> climateData = new MutableLiveData<>();

    private int timeStamp = (int) System.currentTimeMillis() / 1000;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mContext = application;
        repository = new Repository(application);
        weatherRepository = WeatherRepository.getWeatherRepository();
        retrieveData = repository.getCurrentData();
        connector = JSONConnector.getInstance(application);
    }

    public LiveData<List<Current>> getRetrieveData() {
        return retrieveData;
    }

    public LiveData<Resource<List<Weather>>> getCurrentCoordinates(double latitude, double longitude) {
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherCurrentKo(latitude,longitude)
        );
    }

    public LiveData<Resource<List<Weather>>> getCurrentCity(String cityName) {
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherCurrentCi(cityName)
        );
    }

    public LiveData<Resource<List<Weather>>> getDailyCoordinates(double latitude, double longitude) {
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherDailyKo(latitude,longitude)
        );
    }

    public LiveData<Resource<List<Weather>>> getDailyCity(String cityName) {
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherDailyCi(cityName)
        );
    }

    public LiveData<Resource<List<Weather>>> getHourlyCoordinates(double latitude, double longitude) {

        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherHourlyKo(latitude,longitude, 24)
        );
    }

    private LiveData<Resource<List<Weather>>> getHourlyCity(String cityName) {
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherHourlyCi(cityName, 24)
        );
    }

    public void setCurrentWether(Weather weather) {
        currentWether.setValue(weather);
    }
    public LiveData<Weather> getCurrentWeather() {
        return currentWether;
    }

    public LiveData<Resource<GO>> getGeocode(String address){
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getGeocode(address)
        );
    }

    public LiveData<Resource<List<Geocode>>> getReverseGeocode(String address){
        return LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getReverseGeocode(address)
        );
    }
//    public Observable<List<Direction>> getDirection(String origin, String destination){
//        return weatherRepository.getDirection(origin, destination);
//    }

    public LiveData<Climate> gettingClimateInfo(String address){
        weatherRepository.getClimate(address).enqueue(new Callback<Climate>() {
            @Override
            public void onResponse(Call<Climate> call, Response<Climate> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        connector.loading.setValue(false);
                        climateData.setValue(response.body());
                    }
                    else{
                        connector.loading.setValue(false);
                        Log.d(TAG, "onResponse: response body was null ");
                        Toast.makeText(mContext,mContext.getString(R.string.check_network),Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    connector.loading.setValue(false);
                    Log.d(TAG, "onResponse: climate response was not successful");
                    Toast.makeText(mContext,mContext.getString(R.string.check_network),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Climate> call, Throwable t) {
                connector.loading.setValue(false);
                Log.d(TAG, "onFailure: climate response failed "+t.getMessage());
                String network ="";
                network = String.valueOf(R.string.networkConnection);
                try {
                    //Toast.makeText(mContext, network, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.d(TAG, "onFailure: toast exception "+e.getMessage());
                }

            }
        });
        return climateData;
    }

    public MutableLiveData<Climate> getRetrieveClimate() {
            return retrieveClimate;
    }

    public void setRetrieveClimate(Climate retrieveClimate) {
        if (this.retrieveClimate == null){
            this.retrieveClimate = new MutableLiveData<>();
        }
        this.retrieveClimate.setValue(retrieveClimate);
    }
    public LiveData<Boolean> getConnected(){
        return isConnected;
    }
    public void setConnection(Boolean connection){
        isConnected.setValue(connection);
    }
}
