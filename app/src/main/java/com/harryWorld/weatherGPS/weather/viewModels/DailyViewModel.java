package com.harryWorld.weatherGPS.weather.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import com.harryWorld.weatherGPS.weather.constant.DateUtil;
import com.harryWorld.weatherGPS.weather.repository.Repository;
import com.harryWorld.weatherGPS.weather.repository.WeatherRepository;
import com.harryWorld.weatherGPS.weather.utils.Daily;
import com.harryWorld.weatherGPS.weather.utils.Resource;
import com.harryWorld.weatherGPS.weather.utils.Weather;

import java.util.ArrayList;
import java.util.List;

public class DailyViewModel extends AndroidViewModel {
    private static final String TAG = "DailyViewModel";
    private Repository repository;
    private WeatherRepository weatherRepository;
    private LiveData<List<Daily>> retrieveDailyData;
    private MutableLiveData<List<Weather>> dailyData;
    public MutableLiveData<DailyInsertMode> dailyInsertMode = new MutableLiveData<>();

    private int timeStamp = (int) System.currentTimeMillis()/1000;

    public DailyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        weatherRepository = new WeatherRepository();
        retrieveDailyData = repository.getDailyData();
    }

    public LiveData<List<Daily>> getRetrieveDaily(){
        return retrieveDailyData;
    }

    public LiveData<Resource<List<Weather>>> getDailyCoordinates(double longitude, double latitude){
        return   LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherDailyKo(latitude,longitude)
        );
    }
    public LiveData<Resource<List<Weather>>> getDailyCity(String cityName){
        return   LiveDataReactiveStreams.fromPublisher(
                weatherRepository.getWeatherDailyCi(cityName)
        );
    }


        public LiveData<Resource<List<Long>>> insertDaily (List<Weather> weather) {
            ArrayList<Daily> dailyList = new ArrayList<>();
            setInsert(dailyList, weather);
        //    Log.d(TAG, "insertDailyWeather: getting number of daily list" + " " + dailyList.size());
            return LiveDataReactiveStreams.fromPublisher(repository.insertDailyDao(dailyList)
            );
        }

        private void setData(List<Daily> dailyList, List<Weather> weather,int position){
        Daily daily = new Daily();
            List<Integer> sunrise = new ArrayList<>();
            sunrise.clear();
            sunrise.add(weather.get(position).getSunrise_ts());

            List<Integer> sunset = new ArrayList<>();
            sunset.clear();
            sunset.add(weather.get(position).getSunset_ts());

            List<Integer> moonrise = new ArrayList<>();

            moonrise.add(weather.get(position).getMoonset_ts());

            List<Integer> moonset = new ArrayList<>();
            moonset.add(weather.get(position).getMoonset_ts());

            daily.setDailyPressure(weather.get(position).getPressure());
            daily.setDailySnow(weather.get(position).getSnow());
            daily.setDailyWindSpeed(weather.get(position).getWindSpeed());
            daily.setDailyWindDirection(weather.get(position).getWindDirection());
            daily.setDailyTemp(weather.get(position).getTemp());
            daily.setMin_temp_daily(weather.get(position).getMin_temp());
            daily.setMax_temp_daily(weather.get(position).getMax_temp());
            daily.setDailyClouds(weather.get(position).getClouds());
            daily.setDailyDescription(weather.get(position).getDescription());
            daily.setDailyUVIndex(weather.get(position).getUvIndex());
//                daily.setDailyFeelsLike(getWeather.getFeelsLike());
            //daily.setTimeStamp(timeStamp);
            daily.setApp_max_temp_daily(weather.get(position).getApp_max_temp());
            daily.setApp_min_temp_daily(weather.get(position).getApp_min_temp());
            daily.setDailyRainFall(weather.get(position).getPrecip());
            daily.setDailyPop(weather.get(position).getPop());
            daily.setMoon_phase_daily(weather.get(position).getMoon_phase());
            daily.setMoon_phase_lunation_daily(weather.get(position).getMoon_phase_lunation());
            daily.setDailyVisibility(weather.get(position).getVisibility());
            daily.setSunrise_ts_daily(DateUtil.getDailyDateFormat(sunrise));
            daily.setSunset_ts_daily(DateUtil.getDailyDateFormat(sunset));
            daily.setMoonrise_ts_daily(DateUtil.getDateFormat(moonrise));
            daily.setMoonset_ts_daily(DateUtil.getDateFormat(moonset));
            daily.setDailyTime(weather.get(position).getValidDate());
            daily.setDailyHumidity(weather.get(position).getHumidity());
            daily.setDailySeaLevelPressure(weather.get(position).getSeaLevelPressure());
            dailyList.add(daily);
            Log.d(TAG, "setInsert: daily data "+dailyList.get(position).getSunrise_ts_daily());
        }
        private void setInsert (List<Daily> dailyList, List<Weather> weather1){
            setData(dailyList,weather1,0);
            setData(dailyList,weather1,1);
            setData(dailyList,weather1,2);
            setData(dailyList,weather1,3);
            setData(dailyList,weather1,4);
            setData(dailyList,weather1,5);
            setData(dailyList,weather1,6);
            setData(dailyList,weather1,7);
            setData(dailyList,weather1,8);
            setData(dailyList,weather1,9);
            setData(dailyList,weather1,10);
            setData(dailyList,weather1,11);
            setData(dailyList,weather1,12);
            setData(dailyList,weather1,13);
            setData(dailyList,weather1,14);
            setData(dailyList,weather1,15);

        }


        public LiveData<Resource<Integer>> updateDaily (List<Weather>  weatherList, List<Daily> dailyList) {
        dailyList.clear();
            setInsert(dailyList, weatherList);
          //  Log.d(TAG, "updateDaily: checking daily update "+dailyList.get(0).getDailyTemp());
            return LiveDataReactiveStreams.fromPublisher(
                    repository.updatingDaily(dailyList)
            );
        }
        public LiveData<Resource<Integer>> deleteDaily(List<Daily> dailyList){
        return LiveDataReactiveStreams.fromPublisher(repository.deletingDaily(dailyList)
        );
        }
    public void setDailyData(List<Weather> dailyList){
        if (this.dailyData == null){
            this.dailyData = new MutableLiveData<>();
        }
        this.dailyData.setValue(dailyList);
    }

    public MutableLiveData<List<Weather>> getDailyData(){
        if (dailyData == null){
            dailyData = new MutableLiveData<>();
        }
        return dailyData;
    }
    public LiveData<DailyInsertMode> getDailyInsertMode(){
        return dailyInsertMode;
    }
    public void setDailyInsertMode(MutableLiveData<DailyInsertMode> dailyInsertMode){
        this.dailyInsertMode = dailyInsertMode;
    }
    public enum DailyInsertMode{AVAILABLE,ABORT,ERROR}
}