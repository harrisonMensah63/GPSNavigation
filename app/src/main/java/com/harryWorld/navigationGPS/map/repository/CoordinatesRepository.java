package com.harryWorld.navigationGPS.map.repository;

import android.content.Context;
import android.location.Geocoder;

import com.harryWorld.navigationGPS.map.request.CoordinateGenerator;
import com.harryWorld.navigationGPS.map.utils.Coordinate;
import com.harryWorld.navigationGPS.weather.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CoordinatesRepository {

    private CoordinateGenerator request;
    private Coordinate coordinate;
    private static CoordinatesRepository repository;
    private Geocoder geocoder;

    public static CoordinatesRepository getInstance(final  Context c){
        if (repository == null){
            repository = new CoordinatesRepository(c);
        }
        return repository;
    }

    public CoordinatesRepository(Context c) {
    coordinate = new Coordinate();
    geocoder = new Geocoder(c);
    }

    public Flowable<Resource<List<Coordinate>>> getCoordinates(String cityName){
       return   CoordinateGenerator.getRequest().getCoordinate(cityName,2,"json")
                 .map(new Function<Resource<List<Coordinate>>,List<Coordinate>>() {
                     @NonNull
                     @Override
                     public List<Coordinate> apply(@NonNull Resource<List<Coordinate>> listResource) throws Exception {

                         return listResource.data;
                     }
                 })
                 .onErrorReturn(new Function<Throwable, List<Coordinate>>() {
                     @NonNull
                     @Override
                     public List<Coordinate> apply(@NonNull Throwable throwable) throws Exception {
                         String name = throwable.getMessage();
                         List<Coordinate> list = new ArrayList<>();
                         if (coordinate != null) {
                            // weather.setCityName(name);
                             coordinate.setErrorName(-1);
                             coordinate.setErrorMessage(name);
                             list.add(coordinate);
                         }
                         return list;
                     }
                 })
                 .map(new Function<List<Coordinate>, Resource<List<Coordinate>>>() {
                     @NonNull
                     @Override
                     public Resource<List<Coordinate>> apply(@NonNull List<Coordinate> coordinates) throws Exception {
                         if (coordinates.get(0).getErrorName() == -1){
                             return Resource.error(coordinates,coordinates.get(0).getErrorMessage());
                         }
                         else  {
                             return Resource.success(coordinates,null);
                         }
                     }
                 })
                 .subscribeOn(Schedulers.io());
    }
}
