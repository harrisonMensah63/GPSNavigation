package com.harryWorld.weatherGPS.weather.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClimateCurrent implements Parcelable {

    @SerializedName("temp_c")
    @Expose
    private double temp;

    private int is_day;

    @SerializedName("wind_kph")
    @Expose
    private double windSpeed;

    @SerializedName("wind_dir")
    @Expose
    private String windDirection;

    @SerializedName("pressure_mb")
    @Expose
    private double pressure;

    @SerializedName("precip_mm")
    @Expose
    private double rainFall;

    private int humidity;
    private int cloud;

    @SerializedName("gust_kph")
    @Expose
    private double windGust;

    @SerializedName("vis_km")
    @Expose
    private double visibility;

    @SerializedName("feelslike_c")
    @Expose
    private double feelLike;
    @SerializedName("uv")
    @Expose
    private double uvIndex;

    @SerializedName("condition")
    @Expose
    private ClimateCondition condition;

    protected ClimateCurrent(Parcel in) {
        temp = in.readDouble();
        is_day = in.readInt();
        windSpeed = in.readDouble();
        windDirection = in.readString();
        pressure = in.readDouble();
        rainFall = in.readDouble();
        humidity = in.readInt();
        cloud = in.readInt();
        windGust = in.readDouble();
        visibility = in.readDouble();
        feelLike = in.readDouble();
        uvIndex = in.readDouble();
    }

    public static final Creator<ClimateCurrent> CREATOR = new Creator<ClimateCurrent>() {
        @Override
        public ClimateCurrent createFromParcel(Parcel in) {
            return new ClimateCurrent(in);
        }

        @Override
        public ClimateCurrent[] newArray(int size) {
            return new ClimateCurrent[size];
        }
    };

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getIs_day() {
        return is_day;
    }

    public void setIs_day(int is_day) {
        this.is_day = is_day;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getRainFall() {
        return rainFall;
    }

    public void setRainFall(double rainFall) {
        this.rainFall = rainFall;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public double getWindGust() {
        return windGust;
    }

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getFeelLike() {
        return feelLike;
    }

    public void setFeelLike(double feelLike) {
        this.feelLike = feelLike;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public ClimateCondition getCondition() {
        return condition;
    }

    public void setCondition(ClimateCondition condition) {
        this.condition = condition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(temp);
        parcel.writeInt(is_day);
        parcel.writeDouble(windSpeed);
        parcel.writeString(windDirection);
        parcel.writeDouble(pressure);
        parcel.writeDouble(rainFall);
        parcel.writeInt(humidity);
        parcel.writeInt(cloud);
        parcel.writeDouble(windGust);
        parcel.writeDouble(visibility);
        parcel.writeDouble(feelLike);
        parcel.writeDouble(uvIndex);
    }
}
