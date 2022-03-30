package com.harryWorld.navigationGPS.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ForecastDay implements Parcelable {
    private Astro astro;
    private List<ForecastHourly> hour;
    private ForecastDaily day;
    private String date;

    protected ForecastDay(Parcel in) {
        astro = in.readParcelable(Astro.class.getClassLoader());
        hour = in.createTypedArrayList(ForecastHourly.CREATOR);
        day = in.readParcelable(ForecastDaily.class.getClassLoader());
        date = in.readString();
    }

    public static final Creator<ForecastDay> CREATOR = new Creator<ForecastDay>() {
        @Override
        public ForecastDay createFromParcel(Parcel in) {
            return new ForecastDay(in);
        }

        @Override
        public ForecastDay[] newArray(int size) {
            return new ForecastDay[size];
        }
    };

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public List<ForecastHourly> getHour() {
        return hour;
    }

    public void setHour(List<ForecastHourly> hour) {
        this.hour = hour;
    }

    public ForecastDaily getDay() {
        return day;
    }

    public void setDay(ForecastDaily day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(astro, i);
        parcel.writeTypedList(hour);
        parcel.writeParcelable(day, i);
        parcel.writeString(date);
    }
}
