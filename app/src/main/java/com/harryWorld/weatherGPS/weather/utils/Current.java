package com.harryWorld.weatherGPS.weather.utils;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.harryWorld.weatherGPS.weather.constant.Converters;


@Entity(tableName = "current")
public class Current {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private double currentHumidity;

    private double CurrentPressure;

    private double currentWindSpeed;

    private String currentWindDirection;

    private double min_temp_current;

    private double max_temp_current;

    private double currentTemp;

    private String cityName;


    private String CurrentClouds;

    @TypeConverters(Converters.class)
    private Weather1 CurrentDescription;

    private double currentUvIndex;

    private double CurrentFeelsLike;

    @Ignore
    private String errorMessage;
    @Ignore
    private int errorReturn;

    private int timeStamp;

    private String currentSunrise;

    private String currentSunset;

    private double currentVisibility;

    private String currentTime;

    private String alertTitle;
    private String alertDescription;
    private String alertSeverity;


    public Current() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(double currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public double getCurrentPressure() {
        return CurrentPressure;
    }

    public void setCurrentPressure(double currentPressure) {
        CurrentPressure = currentPressure;
    }

    public double getCurrentWindSpeed() {
        return currentWindSpeed;
    }

    public void setCurrentWindSpeed(double currentWindSpeed) {
        this.currentWindSpeed = currentWindSpeed;
    }

    public String getCurrentWindDirection() {
        return currentWindDirection;
    }

    public void setCurrentWindDirection(String currentWindDirection) {
        this.currentWindDirection = currentWindDirection;
    }

    public double getMin_temp_current() {
        return min_temp_current;
    }

    public void setMin_temp_current(double min_temp_current) {
        this.min_temp_current = min_temp_current;
    }

    public double getMax_temp_current() {
        return max_temp_current;
    }

    public void setMax_temp_current(double max_temp_current) {
        this.max_temp_current = max_temp_current;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCurrentClouds() {
        return CurrentClouds;
    }

    public void setCurrentClouds(String currentClouds) {
        CurrentClouds = currentClouds;
    }

    public Weather1 getCurrentDescription() {
        return CurrentDescription;
    }

    public void setCurrentDescription(Weather1 currentDescription) {
        CurrentDescription = currentDescription;
    }

    public double getCurrentUvIndex() {
        return currentUvIndex;
    }

    public void setCurrentUvIndex(double currentUvIndex) {
        this.currentUvIndex = currentUvIndex;
    }

    public double getCurrentFeelsLike() {
        return CurrentFeelsLike;
    }

    public void setCurrentFeelsLike(double currentFeelsLike) {
        CurrentFeelsLike = currentFeelsLike;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorReturn() {
        return errorReturn;
    }

    public void setErrorReturn(int errorReturn) {
        this.errorReturn = errorReturn;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCurrentSunrise() {
        return currentSunrise;
    }

    public void setCurrentSunrise(String currentSunrise) {
        this.currentSunrise = currentSunrise;
    }

    public String getCurrentSunset() {
        return currentSunset;
    }

    public void setCurrentSunset(String currentSunset) {
        this.currentSunset = currentSunset;
    }

    public double getCurrentVisibility() {
        return currentVisibility;
    }

    public void setCurrentVisibility(double currentVisibility) {
        this.currentVisibility = currentVisibility;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getAlertDescription() {
        return alertDescription;
    }

    public void setAlertDescription(String alertDescription) {
        this.alertDescription = alertDescription;
    }

    public String getAlertSeverity() {
        return alertSeverity;
    }

    public void setAlertSeverity(String alertSeverity) {
        this.alertSeverity = alertSeverity;
    }
}