package com.harryWorld.weatherGPS.map.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Coordinate {
    @SerializedName("geojson")
    @Expose
    private List<GeoJson> geojson;
    private String errorMessage;
    private int errorName;

    public List<GeoJson> getGeojson() {
        return geojson;
    }

    public void setGeojson(List<GeoJson> geojson) {
        this.geojson = geojson;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorName() {
        return errorName;
    }

    public void setErrorName(int errorName) {
        this.errorName = errorName;
    }

}
