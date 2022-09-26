package com.harryWorld.weatherGPS.weather.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;


import com.harryWorld.weatherGPS.weather.constant.DateUtil;
import com.harryWorld.weatherGPS.weather.repository.Repository;
import com.harryWorld.weatherGPS.weather.repository.WeatherRepository;
import com.harryWorld.weatherGPS.weather.utils.Hourly;
import com.harryWorld.weatherGPS.weather.utils.Resource;
import com.harryWorld.weatherGPS.weather.utils.Weather;

import java.util.ArrayList;
import java.util.List;


public class HourlyViewModel extends AndroidViewModel {
    private static final String TAG = "HourlyViewModel";
    private Repository repository;
    private WeatherRepository weatherRepository;
    private LiveData<List<Hourly>> retrieveHourlyData;
    private MutableLiveData<List<Weather>> hourlyData;
    public MutableLiveData<HourlyInsertMode> hourlyInsertMode = new MutableLiveData<>();

    private int timeStamp = (int) System.currentTimeMillis()/1000;

    public HourlyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        weatherRepository = new WeatherRepository();
        retrieveHourlyData = repository.getHourlyData();
    }


    public LiveData<List<Hourly>> getRetrieveHourly(){
       return retrieveHourlyData;
    }

    public LiveData<Resource<List<Long>>> insertHourly(List<Weather> weather){
        ArrayList<Hourly> hourlyList = new ArrayList<>();
        setInsert(hourlyList,weather);
      //  Log.d(TAG, "insertHourly: checking hourlyList "+hourlyList.size());
           return LiveDataReactiveStreams.fromPublisher(
                   repository.insertHourlyDao(hourlyList)
        );
    }

    public LiveData<Resource<List<Weather>>> getHourlyCoordinates(double longitude, double latitude){
        return  LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherHourlyKo(latitude,longitude,10)
        );

    }
    public LiveData<Resource<List<Weather>>> getHourlyCity(String cityName){
        return  LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherHourlyCi(cityName,10)
        );
    }

    private void setData(List<Hourly> hourlyList, List<Weather> getWeather, int position){
        Hourly hourly = new Hourly();
        List<Integer> sunrise = new ArrayList<>();
      //  Log.d(TAG, "setData: checking "+getWeather.get(position).getSunrise_ts());
        sunrise.add(getWeather.get(position).getSunrise_ts());

        List<Integer> sunset = new ArrayList<>();
        sunset.add(getWeather.get(position).getSunset_ts());

        List<Integer> moonrise = new ArrayList<>();
        moonrise.add(getWeather.get(position).getMoonset_ts());

        List<Integer> moonset = new ArrayList<>();
        moonset.add(getWeather.get(position).getMoonset_ts());

        hourly.setHourlyPressure(getWeather.get(position).getPressure());
        hourly.setHourlySnow(getWeather.get(position).getSnow());
        hourly.setHourlyWindSpeed(getWeather.get(position).getWindSpeed());
        hourly.setHourlyWindDirection(getWeather.get(position).getWindDirection());
        hourly.setHourlyTemp(getWeather.get(position).getTemp());
//        hourly.setMin_temp_hourly(getWeather.get(position).getMin_temp());
  //      hourly.setMax_temp_hourly(getWeather.get(position).getMax_temp());
        hourly.setHourlyClouds(getWeather.get(position).getClouds());
        hourly.setHourlyDescription(getWeather.get(position).getDescription().getWeather());
        hourly.setHourlyUVIndex(getWeather.get(position).getUvIndex());
        if (getWeather.get(position).getFeelsLike() != null) {
            hourly.setHourlyFeelsLike(getWeather.get(position).getFeelsLike());
        }
        //hourly.setTimeStamp(timeStamp);
//        hourly.setApp_max_temp_hourly(getWeather.get(position).getApp_max_temp());
  //      hourly.setApp_min_temp_hourly(getWeather.get(position).getApp_min_temp());
        hourly.setHourlyRainFall(getWeather.get(position).getPrecip());
        hourly.setHourlyPop(getWeather.get(position).getPop());
      //  Log.d(TAG, "setData: getting hourly pop "+getWeather.get(position).getPop());
//        hourly.setMoon_phase_hourly(getWeather.get(position).getMoon_phase());
//        hourly.setMoon_phase_lunation_hourly(getWeather.get(position).getMoon_phase_lunation());
        hourly.setHourlyVisibility(getWeather.get(position).getVisibility());
        hourly.setSunrise_ts_hourly(DateUtil.getDateFormat(sunrise));
        hourly.setSunset_ts_hourly(DateUtil.getDateFormat(sunset));
        hourly.setMoonrise_ts_hourly(DateUtil.getDateFormat(moonrise));
        hourly.setMoonset_ts_hourly(DateUtil.getDateFormat(moonset));
        hourly.setHourlyTime(getWeather.get(position).getTimestamp_local());
        hourly.setHourlyHumidity(getWeather.get(position).getHumidity());
        hourly.setHourlySeaLevelPressure(getWeather.get(position).getSeaLevelPressure());
        hourlyList.add(hourly);
       // Log.d(TAG, "setData: checking hourly time inserted "+ hourly.getHourlyTime());
    }
    private void setInsert(List<Hourly> hourlyList, List<Weather> weather1){
        setData(hourlyList,weather1,0);
        setData(hourlyList,weather1,1);
        setData(hourlyList,weather1,2);
        setData(hourlyList,weather1,3);
        setData(hourlyList,weather1,4);
        setData(hourlyList,weather1,5);
        setData(hourlyList,weather1,6);
        setData(hourlyList,weather1,7);
        setData(hourlyList,weather1,8);
        setData(hourlyList,weather1,9);
        setData(hourlyList,weather1,10);
        setData(hourlyList,weather1,11);
        setData(hourlyList,weather1,12);
        setData(hourlyList,weather1,13);
        setData(hourlyList,weather1,14);
        setData(hourlyList,weather1,15);
        setData(hourlyList,weather1,16);
        setData(hourlyList,weather1,17);
        setData(hourlyList,weather1,18);
        setData(hourlyList,weather1,19);
        setData(hourlyList,weather1,20);
        setData(hourlyList,weather1,21);
        setData(hourlyList,weather1,22);
        setData(hourlyList,weather1,23);

    }

    public LiveData<Resource<Integer>> updateHourly(List<Weather> weatherList, List<Hourly> hourlyList){
        hourlyList.clear();
        setInsert(hourlyList,weatherList);
       // Log.d(TAG, "updateHourly: checking hourlyList update "+hourlyList.size());
        return LiveDataReactiveStreams.fromPublisher(
                repository.updatingHourly(hourlyList)
        );
    }

    public LiveData<Resource<Integer>> deleteHourly(List<Hourly> hourlyList){
        return LiveDataReactiveStreams.fromPublisher(repository.deletingHourly(hourlyList));
    }
    public void setHourlyData(List<Weather> hourlyList){
        if (this.hourlyData == null){
            this.hourlyData = new MutableLiveData<>();
        }
        this.hourlyData.setValue(hourlyList);
    }

    public MutableLiveData<List<Weather>> getHourlyData(){
        if (hourlyData == null){
            hourlyData = new MutableLiveData<>();
        }
        return hourlyData;
    }
    public MutableLiveData<HourlyInsertMode> getHourlyInsertMode(){
        return hourlyInsertMode;
    }
    public void setHourlyInsertMode(MutableLiveData<HourlyInsertMode> hourlyInsertMode){
        this.hourlyInsertMode = hourlyInsertMode;
    }
    public enum HourlyInsertMode {AVAILABLE,ABORT,ERROR}
}