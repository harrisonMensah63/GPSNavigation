package com.harryWorld.weatherGPS.weather.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.harryWorld.weatherGPS.weather.constant.Converters;

@Entity
public class Daily implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Double dailyHumidity;

    private double dailyPressure;

    private double dailySnow;

    private double dailySeaLevelPressure;

    private double dailyWindSpeed;

    private String dailyWindDirection;

    private double min_temp_daily;

    private double max_temp_daily;

    private double dailyTemp;

    private String dailyTime;

    private double dailyUVIndex;

    private double dailyFeelsLike;

    @TypeConverters(Converters.class)
    private Weather1 dailyDescription;

    private double moon_phase_daily;

    private double moon_phase_lunation_daily;

    private String moonrise_ts_daily;

    private String moonset_ts_daily;

    private String sunrise_ts_daily;

    private String sunset_ts_daily;

    private double app_max_temp_daily;

    private double app_min_temp_daily;

    private double DailyRainFall;

    private int dailyPop;

    private String dailyClouds;

    private double dailyVisibility;

    @Ignore
    private String errorMessage;
    @Ignore
    private int errorReturn;

    public Daily() {
    }


    protected Daily(Parcel in) {
        id = in.readInt();
        dailyHumidity = in.readDouble();
        dailyPressure = in.readDouble();
        dailySnow = in.readDouble();
        dailySeaLevelPressure = in.readDouble();
        dailyWindSpeed = in.readDouble();
        dailyWindDirection = in.readString();
        min_temp_daily = in.readDouble();
        max_temp_daily = in.readDouble();
        dailyTemp = in.readDouble();
        dailyTime = in.readString();
        dailyUVIndex = in.readDouble();
        dailyFeelsLike = in.readDouble();
        moon_phase_daily = in.readDouble();
        moon_phase_lunation_daily = in.readDouble();
        moonrise_ts_daily = in.readString();
        moonset_ts_daily = in.readString();
        sunrise_ts_daily = in.readString();
        sunset_ts_daily = in.readString();
        app_max_temp_daily = in.readDouble();
        app_min_temp_daily = in.readDouble();
        DailyRainFall = in.readDouble();
        dailyPop = in.readInt();
        dailyClouds = in.readString();
        dailyVisibility = in.readDouble();
        errorMessage = in.readString();
        errorReturn = in.readInt();
    }

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel in) {
            return new Daily(in);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getDailyHumidity() {
        return dailyHumidity;
    }

    public void setDailyHumidity(Double dailyHumidity) {
        this.dailyHumidity = dailyHumidity;
    }

    public double getDailyPressure() {
        return dailyPressure;
    }

    public void setDailyPressure(double dailyPressure) {
        this.dailyPressure = dailyPressure;
    }

    public double getDailySnow() {
        return dailySnow;
    }

    public void setDailySnow(double dailySnow) {
        this.dailySnow = dailySnow;
    }

    public double getDailySeaLevelPressure() {
        return dailySeaLevelPressure;
    }

    public void setDailySeaLevelPressure(double dailySeaLevelPressure) {
        this.dailySeaLevelPressure = dailySeaLevelPressure;
    }

    public double getDailyWindSpeed() {
        return dailyWindSpeed;
    }

    public void setDailyWindSpeed(double dailyWindSpeed) {
        this.dailyWindSpeed = dailyWindSpeed;
    }

    public String getDailyWindDirection() {
        return dailyWindDirection;
    }

    public void setDailyWindDirection(String dailyWindDirection) {
        this.dailyWindDirection = dailyWindDirection;
    }

    public double getMin_temp_daily() {
        return min_temp_daily;
    }

    public void setMin_temp_daily(double min_temp_daily) {
        this.min_temp_daily = min_temp_daily;
    }

    public double getMax_temp_daily() {
        return max_temp_daily;
    }

    public void setMax_temp_daily(double max_temp_daily) {
        this.max_temp_daily = max_temp_daily;
    }

    public double getDailyTemp() {
        return dailyTemp;
    }

    public void setDailyTemp(double dailyTemp) {
        this.dailyTemp = dailyTemp;
    }

    public String getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(String dailyTime) {
        this.dailyTime = dailyTime;
    }

    public double getDailyUVIndex() {
        return dailyUVIndex;
    }

    public void setDailyUVIndex(double dailyUVIndex) {
        this.dailyUVIndex = dailyUVIndex;
    }

    public double getDailyFeelsLike() {
        return dailyFeelsLike;
    }

    public void setDailyFeelsLike(double dailyFeelsLike) {
        this.dailyFeelsLike = dailyFeelsLike;
    }

    public Weather1 getDailyDescription() {
        return dailyDescription;
    }

    public void setDailyDescription(Weather1 dailyDescription) {
        this.dailyDescription = dailyDescription;
    }

    public double getMoon_phase_daily() {
        return moon_phase_daily;
    }

    public void setMoon_phase_daily(double moon_phase_daily) {
        this.moon_phase_daily = moon_phase_daily;
    }

    public double getMoon_phase_lunation_daily() {
        return moon_phase_lunation_daily;
    }

    public void setMoon_phase_lunation_daily(double moon_phase_lunation_daily) {
        this.moon_phase_lunation_daily = moon_phase_lunation_daily;
    }

    public String getMoonrise_ts_daily() {
        return moonrise_ts_daily;
    }

    public void setMoonrise_ts_daily(String moonrise_ts_daily) {
        this.moonrise_ts_daily = moonrise_ts_daily;
    }

    public String getMoonset_ts_daily() {
        return moonset_ts_daily;
    }

    public void setMoonset_ts_daily(String moonset_ts_daily) {
        this.moonset_ts_daily = moonset_ts_daily;
    }

    public String getSunrise_ts_daily() {
        return sunrise_ts_daily;
    }

    public void setSunrise_ts_daily(String sunrise_ts_daily) {
        this.sunrise_ts_daily = sunrise_ts_daily;
    }

    public String getSunset_ts_daily() {
        return sunset_ts_daily;
    }

    public void setSunset_ts_daily(String sunset_ts_daily) {
        this.sunset_ts_daily = sunset_ts_daily;
    }

    public double getApp_max_temp_daily() {
        return app_max_temp_daily;
    }

    public void setApp_max_temp_daily(double app_max_temp_daily) {
        this.app_max_temp_daily = app_max_temp_daily;
    }

    public double getApp_min_temp_daily() {
        return app_min_temp_daily;
    }

    public void setApp_min_temp_daily(double app_min_temp_daily) {
        this.app_min_temp_daily = app_min_temp_daily;
    }

    public double getDailyRainFall() {
        return DailyRainFall;
    }

    public void setDailyRainFall(double dailyRainFall) {
        DailyRainFall = dailyRainFall;
    }

    public int getDailyPop() {
        return dailyPop;
    }

    public void setDailyPop(int dailyPop) {
        this.dailyPop = dailyPop;
    }

    public String getDailyClouds() {
        return dailyClouds;
    }

    public void setDailyClouds(String dailyClouds) {
        this.dailyClouds = dailyClouds;
    }

    public double getDailyVisibility() {
        return dailyVisibility;
    }

    public void setDailyVisibility(double dailyVisibility) {
        this.dailyVisibility = dailyVisibility;
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
        dest.writeDouble(dailyHumidity);
        dest.writeDouble(dailyPressure);
        dest.writeDouble(dailySnow);
        dest.writeDouble(dailySeaLevelPressure);
        dest.writeDouble(dailyWindSpeed);
        dest.writeString(dailyWindDirection);
        dest.writeDouble(min_temp_daily);
        dest.writeDouble(max_temp_daily);
        dest.writeDouble(dailyTemp);
        dest.writeString(dailyTime);
        dest.writeDouble(dailyUVIndex);
        dest.writeDouble(dailyFeelsLike);
        dest.writeDouble(moon_phase_daily);
        dest.writeDouble(moon_phase_lunation_daily);
        dest.writeString(moonrise_ts_daily);
        dest.writeString(moonset_ts_daily);
        dest.writeString(sunrise_ts_daily);
        dest.writeString(sunset_ts_daily);
        dest.writeDouble(app_max_temp_daily);
        dest.writeDouble(app_min_temp_daily);
        dest.writeDouble(DailyRainFall);
        dest.writeInt(dailyPop);
        dest.writeString(dailyClouds);
        dest.writeDouble(dailyVisibility);
        dest.writeString(errorMessage);
        dest.writeInt(errorReturn);
    }
}
