package com.harryWorld.weatherGPS.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;

public class Astro implements Parcelable {
    private String sunrise;
    private String sunset;

    protected Astro(Parcel in) {
        sunrise = in.readString();
        sunset = in.readString();
    }

    public static final Creator<Astro> CREATOR = new Creator<Astro>() {
        @Override
        public Astro createFromParcel(Parcel in) {
            return new Astro(in);
        }

        @Override
        public Astro[] newArray(int size) {
            return new Astro[size];
        }
    };

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sunrise);
        parcel.writeString(sunset);
    }
}
