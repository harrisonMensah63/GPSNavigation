package com.harryWorld.navigationGPS.map.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Coordinate {
    @SerializedName("place_id")
    @Expose
    private int placeId;
    private GeoJson geojson;
    private String errorMessage;
    private int errorName;

    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates;

    public Coordinate() {
         geojson = new GeoJson();
    }

    public GeoJson getGeojson() {
        return geojson;
    }

    public void setGeojson(GeoJson geojson) {
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

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
}
