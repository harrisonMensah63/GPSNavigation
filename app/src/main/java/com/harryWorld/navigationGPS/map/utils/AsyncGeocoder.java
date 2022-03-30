package com.harryWorld.navigationGPS.map.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AsyncGeocoder {

    private static final String TAG = "AsyncGeocoder";
private final Geocoder geocoder;

public AsyncGeocoder(Context context) {
    geocoder = new Geocoder(context);
}

public Disposable reverseGeocode(double lat, double lng, Callback callback) {
    return Observable.fromCallable(() -> {
        try {
            return geocoder.getFromLocation(lat, lng, 1);
        } catch (Exception e) {
            Log.d(TAG, "reverseGeocode: throwable "+ new Gson().toJson(e));
            e.printStackTrace();
        }
        return false;
    }).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(result -> {
                //Use result for something
               // log.d(TAG, new Gson().toJson(result));
                Log.d(TAG, "reverseGeocode: throwable "+ new Gson().toJson(result));

                callback.success((Address) ((ArrayList) result).get(0));
            }, throwable -> Log.d(TAG, "reverseGeocode:throwable"+ new Gson().toJson(throwable)));
}

public interface Callback {
    void success(Address address);

    void failure(Throwable e);
}
}