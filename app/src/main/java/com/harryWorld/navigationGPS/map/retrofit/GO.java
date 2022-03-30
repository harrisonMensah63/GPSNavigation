package com.harryWorld.navigationGPS.map.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GO {
    @SerializedName("results")
    @Expose
    private List<Geocode> geocode;

    @SerializedName("addresses")
    @Expose
    private List<ReverseGeocode> reverseGeocode;

    private String errorMessage;
    private int errorReturn;

    public List<Geocode> getGeocode() {
        return geocode;
    }

    public void setGeocode(List<Geocode> geocode) {
        this.geocode = geocode;
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

    public List<ReverseGeocode> getReverseGeocode() {
        return reverseGeocode;
    }

    public void setReverseGeocode(List<ReverseGeocode> reverseGeocode) {
        this.reverseGeocode = reverseGeocode;
    }
}
