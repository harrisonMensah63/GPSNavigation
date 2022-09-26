package com.harryWorld.weatherGPS.map.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GO implements Parcelable {
    @SerializedName("results")
    @Expose
    private List<Geocode> geocode;

    @SerializedName("addresses")
    @Expose
    private List<ReverseGeocode> reverseGeocode;

    private String errorMessage;
    private int errorReturn;

    public GO() {
    }

    public GO(Parcel in) {
        geocode = in.createTypedArrayList(Geocode.CREATOR);
        errorMessage = in.readString();
        errorReturn = in.readInt();
    }

    public static final Creator<GO> CREATOR = new Creator<GO>() {
        @Override
        public GO createFromParcel(Parcel in) {
            return new GO(in);
        }

        @Override
        public GO[] newArray(int size) {
            return new GO[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(geocode);
        dest.writeString(errorMessage);
        dest.writeInt(errorReturn);
    }
}
