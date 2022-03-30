package com.harryWorld.navigationGPS.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.harryWorld.navigationGPS.weather.api.ClimateCondition;

public class ForecastHourly implements Parcelable {

    private String time;

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

    @SerializedName("chance_of_rain")
    @Expose
    private int probability;

    @SerializedName("chance_of_snow")
    @Expose
    private int snowProbability;

    @SerializedName("will_it_rain")
    @Expose
    private int will_it_rain;

    @SerializedName("will_it_snow")
    @Expose
    private double will_it_snow;
    @SerializedName("uv")
    @Expose
    private double uvIndex;

    private ClimateCondition condition;

    protected ForecastHourly(Parcel in) {
        time = in.readString();
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
        probability = in.readInt();
        snowProbability = in.readInt();
        will_it_rain = in.readInt();
        will_it_snow = in.readDouble();
        uvIndex = in.readDouble();
        condition = in.readParcelable(ClimateCondition.class.getClassLoader());
    }

    public static final Creator<ForecastHourly> CREATOR = new Creator<ForecastHourly>() {
        @Override
        public ForecastHourly createFromParcel(Parcel in) {
            return new ForecastHourly(in);
        }

        @Override
        public ForecastHourly[] newArray(int size) {
            return new ForecastHourly[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getSnowProbability() {
        return snowProbability;
    }

    public void setSnowProbability(int snowProbability) {
        this.snowProbability = snowProbability;
    }

    public int getWill_it_rain() {
        return will_it_rain;
    }

    public void setWill_it_rain(int will_it_rain) {
        this.will_it_rain = will_it_rain;
    }

    public double getWill_it_snow() {
        return will_it_snow;
    }

    public void setWill_it_snow(double will_it_snow) {
        this.will_it_snow = will_it_snow;
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
        parcel.writeString(time);
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
        parcel.writeInt(probability);
        parcel.writeInt(snowProbability);
        parcel.writeInt(will_it_rain);
        parcel.writeDouble(will_it_snow);
        parcel.writeDouble(uvIndex);
        parcel.writeParcelable(condition, i);
    }
}
