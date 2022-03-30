package com.harryWorld.navigationGPS.weather.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClimateLocation implements Parcelable {
    private String name;
    private String region;
    private String country;

    @SerializedName("lat")
    @Expose
    private double latitude;

    @SerializedName("lon")
    @Expose
    private double longitude;

    protected ClimateLocation(Parcel in) {
        name = in.readString();
        region = in.readString();
        country = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<ClimateLocation> CREATOR = new Creator<ClimateLocation>() {
        @Override
        public ClimateLocation createFromParcel(Parcel in) {
            return new ClimateLocation(in);
        }

        @Override
        public ClimateLocation[] newArray(int size) {
            return new ClimateLocation[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(region);
        parcel.writeString(country);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}
