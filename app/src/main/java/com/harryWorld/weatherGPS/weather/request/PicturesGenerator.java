package com.harryWorld.weatherGPS.weather.request;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.harryWorld.weatherGPS.weather.constant.Constant.PICTURES_URL;

public class PicturesGenerator {
    private final static Retrofit retrofit =
            new Retrofit.Builder()
                    // .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(PICTURES_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

    public static WeatherRequest request =
            retrofit.create(WeatherRequest.class);

    public WeatherRequest getRequest(){
        return request;
    }
}
