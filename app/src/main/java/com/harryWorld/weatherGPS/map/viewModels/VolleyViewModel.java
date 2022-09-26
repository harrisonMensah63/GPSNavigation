package com.harryWorld.weatherGPS.map.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.harryWorld.weatherGPS.map.volley.JSONConnector;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class VolleyViewModel extends AndroidViewModel {
    public JSONConnector connector;
    private MutableLiveData<mapState> mapState = new MutableLiveData<>();
    MutableLiveData<Boolean> showStart = new MutableLiveData<>();
    private MutableLiveData<List<String>> liveStepsDirection = new MutableLiveData<>();
    private MutableLiveData<String> liveDuration = new MutableLiveData<>();
    private MutableLiveData<String> liveDistace = new MutableLiveData<>();
    private MutableLiveData<List<String>> liveStepsName = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> liveWayPoint = new MutableLiveData<>();
    private MutableLiveData<String> cityViewModel = new MutableLiveData<>();
    MutableLiveData<Boolean> isChoose = new MutableLiveData<>();

    public VolleyViewModel(@NonNull @io.reactivex.annotations.NonNull Application application) {
        super(application);
        connector = JSONConnector.getInstance(application);
    }



    public void traffic(String location, String type){
        connector.traffic(location, type);
    }
    public LiveData<PolylineOptions> getPolylineOptions(){
        return connector.liveOptions;
    }

    public MutableLiveData<mapState> getMapSate(){
        return mapState;
    }

    public LiveData<List<String>> getLiveStepsDirection(){
        return liveStepsDirection;
    }
    public void setLiveStepsDirection(List<String> stepsDirection){
        liveStepsDirection.setValue(stepsDirection);
    }

    public void setLiveDuration(String duration) {
        this.liveDuration.setValue(duration);
    }

    public void setLiveDistace(String distace) {
        this.liveDistace.setValue(distace);
    }


    public void setLiveStepsName(List<String> stepsName) {
        this.liveStepsName.setValue(stepsName);
    }

    public void setLiveWayPoint(List<Integer> wayPoint) {
        this.liveWayPoint.setValue(wayPoint);
    }

    public LiveData<String> getLiveDistace() {
        return liveDistace;
    }

    public LiveData<String> getCityViewModel(){
        return cityViewModel;
    }
    public void setCityViewModel(String cityName){
        cityViewModel.setValue(cityName);
    }

    public LiveData<List<String>> getLiveStepsName() {
        return liveStepsName;
    }

    public LiveData<List<Integer>> getLiveWayPoint() {
        return liveWayPoint;
    }
    public LiveData<String> getLiveDuration(){
        return liveDuration;
    }
    public LiveData<Boolean> getShowStart(){
        return showStart;
    }
    public void setShowStart(Boolean isShowStarted){
        showStart.setValue(isShowStarted);
    }
    public LiveData<Boolean> getChoose(){
        return isChoose;
    }
    public void setChoose(Boolean aBoolean){
        isChoose.setValue(aBoolean);
    }
    public enum mapState{START,NAVIGATE,RECENTER,OVERVIEW}
}
