package com.harryWorld.navigationGPS.weather.request;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.harryWorld.navigationGPS.weather.constant.Constant.DIRECTION;
import static com.harryWorld.navigationGPS.weather.constant.Constant.OPEN_DIRECTION;

public class DirectionGenerator {
    private static Retrofit retrofit =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(OPEN_DIRECTION)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

    public static WeatherRequest request =
            retrofit.create(WeatherRequest.class);

    public WeatherRequest getRequest(){
        return request;
    }
}
