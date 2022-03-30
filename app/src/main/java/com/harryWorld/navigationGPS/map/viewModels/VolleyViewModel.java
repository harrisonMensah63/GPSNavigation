package com.harryWorld.navigationGPS.map.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.harryWorld.navigationGPS.map.volley.JSONConnector;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class VolleyViewModel extends AndroidViewModel {
    public JSONConnector connector;
    private MutableLiveData<mapState> mapState = new MutableLiveData<>();

    public VolleyViewModel(@NonNull @io.reactivex.annotations.NonNull Application application) {
        super(application);
        connector = JSONConnector.getInstance(application);
    }


    public List<String> getInstructions(){
        return connector.instructions;
    }
    public List<String> getShortInstructions(){
        return connector.shortInstructions;
    }
    public List<LatLng> stepsCoordinates(){
        return connector.coordinatesList;
    }
    public List<LatLng> categoriesCoordinates(){
        return connector.categoriesList;
    }
    public List<LatLng> mainCoordinates(){
        return connector.points;
    }
    public PolylineOptions mainPolyline(){
        return connector.lineOptions;
    }
    public void drawRoute(LatLng origin, LatLng destination, String mode) {
        if (connector.liveOptions.getValue() == null) {
        connector.loading.setValue(true);
            connector.drawRoute(origin, destination, mode);
        }
    }
    public void traffic(String location, String type){
        connector.traffic(location, type);
    }
    public void getMatrix(String origin, String destination){
        connector.getMatrixDistance(origin, destination);
    }
    public LiveData<PolylineOptions> getPolylineOptions(){
        return connector.liveOptions;
    }
    public LiveData<List<LatLng>> getLiveCategories(){
        return connector.liveTraffic;
    }
    public LiveData<List<String>> getLiveMatrixDistance(){
        return connector.liveDistanceMatrix;
    }
    public List<String> getMatrixDuration(){
        return connector.durationMatrix;
    }
    public MutableLiveData<mapState> getMapSate(){
        return mapState;
    }
    public enum mapState{START,NAVIGATE,RECENTER,OVERVIEW}
}
