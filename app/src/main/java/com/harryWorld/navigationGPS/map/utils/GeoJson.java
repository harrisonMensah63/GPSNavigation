package com.harryWorld.navigationGPS.map.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoJson {
    @SerializedName("coordinates")
    @Expose
    private double[] coordinates;

    public GeoJson() {
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
