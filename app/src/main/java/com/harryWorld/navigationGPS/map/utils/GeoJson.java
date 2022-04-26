package com.harryWorld.navigationGPS.map.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoJson {
    @SerializedName("coordinates")
    @Expose
    private List<List<Double>> coordinates;

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }
}
