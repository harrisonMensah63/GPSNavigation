package com.harryWorld.weatherGPS.weather.utils;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Weather {

    @SerializedName("country_code")
    @Expose
    private String countryCode;

    @SerializedName("lat")
    @Expose
    private Double latitude;

    @SerializedName("lon")
    @Expose
    private Double longitude;

    @SerializedName("solar_rad")
    @Expose
    private double solarRadius;

    @SerializedName("pres")
    @Expose
    private Double pressure;

    @SerializedName("snow")
    @Expose
    private Double snow;

    @SerializedName("slp")
    @Expose
    private Double seaLevelPressure;

    @SerializedName("wind_spd")
    @Expose
    private Double windSpeed;

    @SerializedName("wind_cdir")
    @Expose
    private String windDirection;


    @SerializedName("min_temp")
    @Expose
    private Double min_temp;

    @SerializedName("max_temp")
    @Expose
    private Double max_temp;

    @SerializedName("temp")
    @Expose
    private Double temp;

    @SerializedName("city_name")
    @Expose
    private String cityName;

    @SerializedName("rh")
    @Expose
    private Double humidity;

    @SerializedName("clouds")
    @Expose
    private String clouds;

    @SerializedName("pod")
    @Expose
    private char partOfDay;

    @SerializedName("weather")
    @Expose
    private Weather1 description;

    @SerializedName("uv")
    @Expose
    private Double uvIndex;

    @SerializedName("app_temp")
    @Expose
    private Double feelsLike;
    @SerializedName("vis")
    @Expose
    private Double visibility;

    @SerializedName("sunrise")
    @Expose
    private String sunrise;
    @SerializedName("sunset")
    @Expose
    private String sunset;

    @SerializedName("ob_time")
    @Expose
    private String weatherTime;

    @SerializedName("valid_date")
    @Expose
    private String validDate;
    private int timeStamp;

    @SerializedName("moon_phase")
    @Expose
    private Double moon_phase;
    @SerializedName("moon_phase_lunation")
    @Expose
    private Double moon_phase_lunation;
    @SerializedName("moonrise_ts")
    @Expose
    private int moonrise_ts;
    @SerializedName("moonset_ts")
    @Expose
    private int moonset_ts;
    @SerializedName("sunrise_ts")
    @Expose
    private int sunrise_ts;

    @SerializedName("sunset_ts")
    @Expose
    private int sunset_ts;

    @SerializedName("app_max_temp")
    @Expose
    private Double app_max_temp;
    @SerializedName("app_min_temp")
    @Expose
    private Double app_min_temp;
    @SerializedName("precip")
    @Expose
    private Double precip;
    @SerializedName("pop")
    @Expose
    private int pop;

    private String timestamp_local;

    private int errorReturn;
    private String ErrorMessage;

    @Ignore
    private String error;


    public Weather() {

    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public double getSolarRadius() {
        return solarRadius;
    }

    public void setSolarRadius(double solarRadius) {
        this.solarRadius = solarRadius;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getSnow() {
        return snow;
    }

    public void setSnow(Double snow) {
        this.snow = snow;
    }

    public Double getSeaLevelPressure() {
        return seaLevelPressure;
    }

    public void setSeaLevelPressure(Double seaLevelPressure) {
        this.seaLevelPressure = seaLevelPressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Double getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(Double min_temp) {
        this.min_temp = min_temp;
    }

    public Double getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(Double max_temp) {
        this.max_temp = max_temp;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public char getPartOfDay() {
        return partOfDay;
    }

    public void setPartOfDay(char partOfDay) {
        this.partOfDay = partOfDay;
    }

    public Weather1 getDescription() {
        return description;
    }

    public void setDescription(Weather1 description) {
        this.description = description;
    }

    public Double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(Double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

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

    public String getWeatherTime() {
        return weatherTime;
    }

    public void setWeatherTime(String weatherTime) {
        this.weatherTime = weatherTime;
    }

    public Double getMoon_phase() {
        return moon_phase;
    }

    public void setMoon_phase(Double moon_phase) {
        this.moon_phase = moon_phase;
    }

    public Double getMoon_phase_lunation() {
        return moon_phase_lunation;
    }

    public void setMoon_phase_lunation(Double moon_phase_lunation) {
        this.moon_phase_lunation = moon_phase_lunation;
    }

    public int getMoonrise_ts() {
        return moonrise_ts;
    }

    public void setMoonrise_ts(int moonrise_ts) {
        this.moonrise_ts = moonrise_ts;
    }

    public int getMoonset_ts() {
        return moonset_ts;
    }

    public void setMoonset_ts(int moonset_ts) {
        this.moonset_ts = moonset_ts;
    }

    public int getSunrise_ts() {
        return sunrise_ts;
    }

    public void setSunrise_ts(int sunrise_ts) {
        this.sunrise_ts = sunrise_ts;
    }

    public int getSunset_ts() {
        return sunset_ts;
    }

    public void setSunset_ts(int sunset_ts) {
        this.sunset_ts = sunset_ts;
    }

    public Double getApp_max_temp() {
        return app_max_temp;
    }

    public void setApp_max_temp(Double app_max_temp) {
        this.app_max_temp = app_max_temp;
    }

    public Double getApp_min_temp() {
        return app_min_temp;
    }

    public void setApp_min_temp(Double app_min_temp) {
        this.app_min_temp = app_min_temp;
    }

    public Double getPrecip() {
        return precip;
    }

    public void setPrecip(Double precip) {
        this.precip = precip;
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getTimestamp_local() {
        return timestamp_local;
    }

    public void setTimestamp_local(String timestamp_local) {
        this.timestamp_local = timestamp_local;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getErrorReturn() {
        return errorReturn;
    }

    public void setErrorReturn(int errorReturn) {
        this.errorReturn = errorReturn;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

}
