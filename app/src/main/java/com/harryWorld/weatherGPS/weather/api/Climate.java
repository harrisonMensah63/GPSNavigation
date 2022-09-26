package com.harryWorld.weatherGPS.weather.api;

import android.os.Parcel;
import android.os.Parcelable;

public class Climate implements Parcelable {
    private ClimateLocation location;
    private ClimateCurrent current;
    private ClimateForecast forecast;

    public Climate() {
    }

    public Climate(Parcel in) {
        location = in.readParcelable(ClimateLocation.class.getClassLoader());
        current = in.readParcelable(ClimateCurrent.class.getClassLoader());
        forecast = in.readParcelable(ClimateForecast.class.getClassLoader());
    }

    public static final Creator<Climate> CREATOR = new Creator<Climate>() {
        @Override
        public Climate createFromParcel(Parcel in) {
            return new Climate(in);
        }

        @Override
        public Climate[] newArray(int size) {
            return new Climate[size];
        }
    };

    public ClimateLocation getLocation() {
        return location;
    }

    public void setLocation(ClimateLocation location) {
        this.location = location;
    }

    public ClimateCurrent getCurrent() {
        return current;
    }

    public void setCurrent(ClimateCurrent current) {
        this.current = current;
    }

    public ClimateForecast getForecast() {
        return forecast;
    }

    public void setForecast(ClimateForecast forecast) {
        this.forecast = forecast;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(location, i);
        parcel.writeParcelable(current, i);
        parcel.writeParcelable(forecast, i);
    }
}
