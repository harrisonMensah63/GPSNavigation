package com.harryWorld.navigationGPS.map.request;

import com.harryWorld.navigationGPS.map.utils.Coordinate;
import com.harryWorld.navigationGPS.weather.utils.Resource;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapRequest {

    @GET("search.php")
    Flowable<Resource<List<Coordinate>>> getCoordinate(
            @Query("q") String name,
            @Query("polygon_geojson") int one,
            @Query("format") String jasing
    );
}
