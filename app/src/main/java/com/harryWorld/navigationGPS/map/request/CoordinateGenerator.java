package com.harryWorld.navigationGPS.map.request;


import com.harryWorld.navigationGPS.map.utils.Coordinate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.harryWorld.navigationGPS.map.constant.Constant.COORDINATES_URL;


public class CoordinateGenerator {
    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Coordinate.class, new CarSerializer())
            .registerTypeAdapter(Coordinate.class,new CarDeserializer())
            .create();

    private static final Retrofit retrofit =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(COORDINATES_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


    private static final MapRequest request =
            retrofit.create(MapRequest.class);

    public static MapRequest getRequest(){
        return request;
    }
}
