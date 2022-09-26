package com.harryWorld.weatherGPS.map.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geocode implements Parcelable {

    double latitude;
    double longitude;
    @SerializedName("municipality")
    @Expose
    String name;
    @SerializedName("address")
    @Expose
    Address address;
    @SerializedName("position")
    @Expose
    Position position;
    @SerializedName("poi")
    @Expose
    Poi poi;
    String region;
    String county;
    String country;
    String region_code;
    String errorMessage;
    String continent;
    String label;
    int errorReturn;

    public Geocode(){}
    public Geocode(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
        region = in.readString();
        county = in.readString();
        country = in.readString();
        region_code = in.readString();
        errorMessage = in.readString();
        continent = in.readString();
        label = in.readString();
        errorReturn = in.readInt();
    }

    public static final Creator<Geocode> CREATOR = new Creator<Geocode>() {
        @Override
        public Geocode createFromParcel(Parcel in) {
            return new Geocode(in);
        }

        @Override
        public Geocode[] newArray(int size) {
            return new Geocode[size];
        }
    };

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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getErrorReturn() {
        return errorReturn;
    }

    public void setErrorReturn(int errorReturn) {
        this.errorReturn = errorReturn;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Poi getPoi() {
        return poi;
    }

    public void setPoi(Poi poi) {
        this.poi = poi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(name);
        dest.writeString(region);
        dest.writeString(county);
        dest.writeString(country);
        dest.writeString(region_code);
        dest.writeString(errorMessage);
        dest.writeString(continent);
        dest.writeString(label);
        dest.writeInt(errorReturn);
    }
}
