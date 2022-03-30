package com.harryWorld.navigationGPS.weather.request;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.harryWorld.navigationGPS.weather.constant.Constant.GEOCODE_URL;

public class GeocoderGenerator {
    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build();
    private static Retrofit retrofit =
            new Retrofit.Builder()
                    // .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GEOCODE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

    public static WeatherRequest request =
            retrofit.create(WeatherRequest.class);

    public WeatherRequest getRequest(){
        return request;
    }
}
