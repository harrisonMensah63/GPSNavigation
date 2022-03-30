package com.harryWorld.navigationGPS.weather.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClimateCondition implements Parcelable {
    @SerializedName("text")
    @Expose
    private String description;

    protected ClimateCondition(Parcel in) {
        description = in.readString();
    }

    public static final Creator<ClimateCondition> CREATOR = new Creator<ClimateCondition>() {
        @Override
        public ClimateCondition createFromParcel(Parcel in) {
            return new ClimateCondition(in);
        }

        @Override
        public ClimateCondition[] newArray(int size) {
            return new ClimateCondition[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
    }
}
