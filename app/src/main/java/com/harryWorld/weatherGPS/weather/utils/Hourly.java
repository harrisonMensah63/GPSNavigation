package com.harryWorld.weatherGPS.weather.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.harryWorld.weatherGPS.weather.constant.Converters;


@Entity
public class Hourly implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Double hourlyHumidity;

    private double hourlyPressure;

    private double hourlySnow;

    private double hourlySeaLevelPressure;

    private double hourlyWindSpeed;

    private String hourlyWindDirection;

    private double min_temp_hourly;

    private double max_temp_hourly;

    private double hourlyTemp;

    private String hourlyTime;

    private double hourlyUVIndex;

    private double hourlyFeelsLike;

    @TypeConverters(Converters.class)
    private String hourlyDescription;

    private double moon_phase_hourly;

    private double moon_phase_lunation_hourly;

    private String moonrise_ts_hourly;

    private String moonset_ts_hourly;

    private String sunrise_ts_hourly;

    private String sunset_ts_hourly;

    private double app_max_temp_hourly;

    private double app_min_temp_hourly;

    private double hourlyRainFall;

    private int hourlyPop;

    private String hourlyClouds;

    private double hourlyVisibility;

    @Ignore
    private String errorMessage;
    @Ignore
    private int errorReturn;

    public Hourly() {
    }


    protected Hourly(Parcel in) {
        id = in.readInt();
        hourlyHumidity = in.readDouble();
        hourlyPressure = in.readDouble();
        hourlySnow = in.readDouble();
        hourlySeaLevelPressure = in.readDouble();
        hourlyWindSpeed = in.readDouble();
        hourlyWindDirection = in.readString();
        min_temp_hourly = in.readDouble();
        max_temp_hourly = in.readDouble();
        hourlyTemp = in.readDouble();
        hourlyTime = in.readString();
        hourlyUVIndex = in.readDouble();
        hourlyFeelsLike = in.readDouble();
        moon_phase_hourly = in.readDouble();
        moon_phase_lunation_hourly = in.readDouble();
        moonrise_ts_hourly = in.readString();
        moonset_ts_hourly = in.readString();
        sunrise_ts_hourly = in.readString();
        sunset_ts_hourly = in.readString();
        app_max_temp_hourly = in.readDouble();
        app_min_temp_hourly = in.readDouble();
        hourlyRainFall = in.readDouble();
        hourlyPop = in.readInt();
        hourlyClouds = in.readString();
        hourlyVisibility = in.readDouble();
        errorMessage = in.readString();
        errorReturn = in.readInt();
    }

    public static final Creator<Hourly> CREATOR = new Creator<Hourly>() {
        @Override
        public Hourly createFromParcel(Parcel in) {
            return new Hourly(in);
        }

        @Override
        public Hourly[] newArray(int size) {
            return new Hourly[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getHourlyHumidity() {
        return hourlyHumidity;
    }

    public void setHourlyHumidity(Double hourlyHumidity) {
        this.hourlyHumidity = hourlyHumidity;
    }

    public double getHourlyPressure() {
        return hourlyPressure;
    }

    public void setHourlyPressure(double hourlyPressure) {
        this.hourlyPressure = hourlyPressure;
    }

    public double getHourlySnow() {
        return hourlySnow;
    }

    public void setHourlySnow(double hourlySnow) {
        this.hourlySnow = hourlySnow;
    }

    public double getHourlySeaLevelPressure() {
        return hourlySeaLevelPressure;
    }

    public void setHourlySeaLevelPressure(double hourlySeaLevelPressure) {
        this.hourlySeaLevelPressure = hourlySeaLevelPressure;
    }

    public double getHourlyWindSpeed() {
        return hourlyWindSpeed;
    }

    public void setHourlyWindSpeed(double hourlyWindSpeed) {
        this.hourlyWindSpeed = hourlyWindSpeed;
    }

    public String getHourlyWindDirection() {
        return hourlyWindDirection;
    }

    public void setHourlyWindDirection(String hourlyWindDirection) {
        this.hourlyWindDirection = hourlyWindDirection;
    }

    public double getMin_temp_hourly() {
        return min_temp_hourly;
    }

    public void setMin_temp_hourly(double min_temp_hourly) {
        this.min_temp_hourly = min_temp_hourly;
    }

    public double getMax_temp_hourly() {
        return max_temp_hourly;
    }

    public void setMax_temp_hourly(double max_temp_hourly) {
        this.max_temp_hourly = max_temp_hourly;
    }

    public double getHourlyTemp() {
        return hourlyTemp;
    }

    public void setHourlyTemp(double hourlyTemp) {
        this.hourlyTemp = hourlyTemp;
    }

    public String getHourlyTime() {
        return hourlyTime;
    }

    public void setHourlyTime(String hourlyTime) {
        this.hourlyTime = hourlyTime;
    }

    public double getHourlyUVIndex() {
        return hourlyUVIndex;
    }

    public void setHourlyUVIndex(double hourlyUVIndex) {
        this.hourlyUVIndex = hourlyUVIndex;
    }

    public double getHourlyFeelsLike() {
        return hourlyFeelsLike;
    }

    public void setHourlyFeelsLike(double hourlyFeelsLike) {
        this.hourlyFeelsLike = hourlyFeelsLike;
    }

    public String getHourlyDescription() {
        return hourlyDescription;
    }

    public void setHourlyDescription(String hourlyDescription) {
        this.hourlyDescription = hourlyDescription;
    }

    public double getMoon_phase_hourly() {
        return moon_phase_hourly;
    }

    public void setMoon_phase_hourly(double moon_phase_hourly) {
        this.moon_phase_hourly = moon_phase_hourly;
    }

    public double getMoon_phase_lunation_hourly() {
        return moon_phase_lunation_hourly;
    }

    public void setMoon_phase_lunation_hourly(double moon_phase_lunation_hourly) {
        this.moon_phase_lunation_hourly = moon_phase_lunation_hourly;
    }

    public String getMoonrise_ts_hourly() {
        return moonrise_ts_hourly;
    }

    public void setMoonrise_ts_hourly(String moonrise_ts_hourly) {
        this.moonrise_ts_hourly = moonrise_ts_hourly;
    }

    public String getMoonset_ts_hourly() {
        return moonset_ts_hourly;
    }

    public void setMoonset_ts_hourly(String moonset_ts_hourly) {
        this.moonset_ts_hourly = moonset_ts_hourly;
    }

    public String getSunrise_ts_hourly() {
        return sunrise_ts_hourly;
    }

    public void setSunrise_ts_hourly(String sunrise_ts_hourly) {
        this.sunrise_ts_hourly = sunrise_ts_hourly;
    }

    public String getSunset_ts_hourly() {
        return sunset_ts_hourly;
    }

    public void setSunset_ts_hourly(String sunset_ts_hourly) {
        this.sunset_ts_hourly = sunset_ts_hourly;
    }

    public double getApp_max_temp_hourly() {
        return app_max_temp_hourly;
    }

    public void setApp_max_temp_hourly(double app_max_temp_hourly) {
        this.app_max_temp_hourly = app_max_temp_hourly;
    }

    public double getApp_min_temp_hourly() {
        return app_min_temp_hourly;
    }

    public void setApp_min_temp_hourly(double app_min_temp_hourly) {
        this.app_min_temp_hourly = app_min_temp_hourly;
    }

    public double getHourlyRainFall() {
        return hourlyRainFall;
    }

    public void setHourlyRainFall(double hourlyRainFall) {
        this.hourlyRainFall = hourlyRainFall;
    }

    public int getHourlyPop() {
        return hourlyPop;
    }

    public void setHourlyPop(int hourlyPop) {
        this.hourlyPop = hourlyPop;
    }

    public String getHourlyClouds() {
        return hourlyClouds;
    }

    public void setHourlyClouds(String hourlyClouds) {
        this.hourlyClouds = hourlyClouds;
    }

    public double getHourlyVisibility() {
        return hourlyVisibility;
    }

    public void setHourlyVisibility(double hourlyVisibility) {
        this.hourlyVisibility = hourlyVisibility;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(hourlyHumidity);
        dest.writeDouble(hourlyPressure);
        dest.writeDouble(hourlySnow);
        dest.writeDouble(hourlySeaLevelPressure);
        dest.writeDouble(hourlyWindSpeed);
        dest.writeString(hourlyWindDirection);
        dest.writeDouble(min_temp_hourly);
        dest.writeDouble(max_temp_hourly);
        dest.writeDouble(hourlyTemp);
        dest.writeString(hourlyTime);
        dest.writeDouble(hourlyUVIndex);
        dest.writeDouble(hourlyFeelsLike);
        dest.writeDouble(moon_phase_hourly);
        dest.writeDouble(moon_phase_lunation_hourly);
        dest.writeString(moonrise_ts_hourly);
        dest.writeString(moonset_ts_hourly);
        dest.writeString(sunrise_ts_hourly);
        dest.writeString(sunset_ts_hourly);
        dest.writeDouble(app_max_temp_hourly);
        dest.writeDouble(app_min_temp_hourly);
        dest.writeDouble(hourlyRainFall);
        dest.writeInt(hourlyPop);
        dest.writeString(hourlyClouds);
        dest.writeDouble(hourlyVisibility);
        dest.writeString(errorMessage);
        dest.writeInt(errorReturn);
    }
}
