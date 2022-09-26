package com.harryWorld.weatherGPS.weather.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.harryWorld.weatherGPS.weather.api.forecast.ForecastDay;

import java.util.List;

public class ClimateForecast implements Parcelable {
    private List<ForecastDay> forecastday;

    protected ClimateForecast(Parcel in) {
        forecastday = in.createTypedArrayList(ForecastDay.CREATOR);
    }

    public static final Creator<ClimateForecast> CREATOR = new Creator<ClimateForecast>() {
        @Override
        public ClimateForecast createFromParcel(Parcel in) {
            return new ClimateForecast(in);
        }

        @Override
        public ClimateForecast[] newArray(int size) {
            return new ClimateForecast[size];
        }
    };

    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<ForecastDay> forecastday) {
        this.forecastday = forecastday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(forecastday);
    }
}
