package com.harryWorld.navigationGPS.weather.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather1 {

    @SerializedName("description")
    @Expose
    private String weather;

    public Weather1() {
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
