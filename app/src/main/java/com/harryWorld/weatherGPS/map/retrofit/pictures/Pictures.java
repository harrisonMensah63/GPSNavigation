package com.harryWorld.weatherGPS.map.retrofit.pictures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pictures {
    @SerializedName("results")
    @Expose
    List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
