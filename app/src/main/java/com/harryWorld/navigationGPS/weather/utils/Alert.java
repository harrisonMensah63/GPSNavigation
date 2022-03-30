package com.harryWorld.navigationGPS.weather.utils;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity
public class Alert {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("city_name")
    @Expose
    private String cityName;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("severity")
    @Expose
    private String severity;

//    @SerializedName("regions")
//    @Expose
//    private List<String> regions;

    @Ignore
    private String error;
    @Ignore
    private int inError;

    public Alert() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getInError() {
        return inError;
    }

    public void setInError(int inError) {
        this.inError = inError;
    }

    public String getSeverity() {
        return severity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
