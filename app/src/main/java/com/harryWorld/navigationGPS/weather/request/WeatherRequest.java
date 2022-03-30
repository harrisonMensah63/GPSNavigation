package com.harryWorld.navigationGPS.weather.request;

import com.harryWorld.navigationGPS.map.retrofit.Direction;
import com.harryWorld.navigationGPS.map.retrofit.GO;
import com.harryWorld.navigationGPS.map.retrofit.Geocode;
import com.harryWorld.navigationGPS.weather.api.Climate;
import com.harryWorld.navigationGPS.weather.utils.Alert;
import com.harryWorld.navigationGPS.weather.utils.Resource;
import com.harryWorld.navigationGPS.weather.utils.Weather;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherRequest {
    @GET("/maps/api/directions/json")
    Call<Direction> getDirectionApi(
            @Query("destination") String destination,
            @Query("origin") String origin,
            @Query("key") String apiKey
    );
    //current weather
    @GET("current")
    Flowable<Resource<List<Weather>>> getWeatherCurrentKo(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("lang") String language,
            @Query("key") String apiKey
    );
    @GET("current")
    Flowable<Resource<List<Weather>>> getWeatherCurrentCi(
            @Query("city") String city,
            @Query("lang") String language,
            @Query("key") String apiKey
    );

    //daily weather
    @GET("forecast/daily")
    Flowable<Resource<List<Weather>>> getWeatherDailyKo(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("lang") String language,
            @Query("key") String apiKey
    );
    @GET("forecast/daily")
    Flowable<Resource<List<Weather>>> getWeatherDailyCi(
            @Query("city") String city,
            @Query("lang") String language,
            @Query("key") String apiKey
    );

    //hourky weather
    @GET("forecast/hourly")
    Flowable<Resource<List<Weather>>> getWeatherHourlyKo(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("lang") String language,
            @Query("hours") int hours,
            @Query("key") String apiKey
    );
    @GET("forecast/hourly")
    Flowable<Resource<List<Weather>>> getWeatherHourlyCi(
            @Query("city") String city,
            @Query("hours") int hours,
            @Query("lang") String language,
            @Query("key") String apiKey
    );

    @GET("forecast.json")
    Call<Climate> getClimate(
            @Query("key") String apiKey,
            @Query("q") String address,
            @Query("lang") String language
    );
    @GET("geocode/{query}/")
    Call<GO> getGeocoder(
            @Path("query") String query,
            @Query("key") String apiKey,
            @Query("language") String language
    );
    @GET("reverseGeocode/{query}/")
    Call<GO> getReverseGeocoder(
            @Path("query") String query,
            @Query("key") String apiKey,
            @Query("language") String language
    );
    @GET("geocode/{query}")
    Flowable<Resource<GO>> getGeocode(
            @Path("query") String query,
            @Query("key") String apiKey,
            @Query("language") String language
            );
    @GET("reverse")
    Flowable<Resource<List<Geocode>>> getReverseGeocode(
            @Query("apiKey") String apiKey,
            @Query("language") String language,
            @Query("text") String query
    );
    //weather alerts
    @GET("alert")
    Flowable<Resource<List<Alert>>> getWeatherAlertKo(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("key") String apiKey
    );
    @GET("alert")
    Flowable<Resource<List<Alert>>> getWeatherAlertCi(
            @Query("city") String city,
            @Query("key") String apiKey
    );
}
