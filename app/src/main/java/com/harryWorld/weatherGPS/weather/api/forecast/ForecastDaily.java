package com.harryWorld.weatherGPS.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.harryWorld.weatherGPS.weather.api.ClimateCondition;

public class ForecastDaily implements Parcelable {
    @SerializedName("maxtemp_c")
    @Expose
    private double max_temp;

    @SerializedName("mintemp_c")
    @Expose
    private double min_temp;

    @SerializedName("maxwind_kph")
    @Expose
    private double windSpeed;

    @SerializedName("totalprecip_mm")
    @Expose
    private double rainFall;

    @SerializedName("daily_chance_of_rain")
    @Expose
    private int probability;

    @SerializedName("avgvis_km")
    @Expose
    private double visibility;

    @SerializedName("avghumidity")
    @Expose
    private double humidity;

    @SerializedName("daily_chance_of_snow")
    @Expose
    private int snowProbability;

    @SerializedName("daily_will_it_rain")
    @Expose
    private int will_it_rain;

    @SerializedName("daily_will_it_snow")
    @Expose
    private double will_it_snow;

    @SerializedName("uv")
    @Expose
    private double UVIndex;

    private ClimateCondition condition;

    protected ForecastDaily(Parcel in) {
        max_temp = in.readDouble();
        min_temp = in.readDouble();
        windSpeed = in.readDouble();
        rainFall = in.readDouble();
        probability = in.readInt();
        visibility = in.readDouble();
        humidity = in.readDouble();
        snowProbability = in.readInt();
        will_it_rain = in.readInt();
        will_it_snow = in.readDouble();
        UVIndex = in.readDouble();
        condition = in.readParcelable(ClimateCondition.class.getClassLoader());
    }

    public static final Creator<ForecastDaily> CREATOR = new Creator<ForecastDaily>() {
        @Override
        public ForecastDaily createFromParcel(Parcel in) {
            return new ForecastDaily(in);
        }

        @Override
        public ForecastDaily[] newArray(int size) {
            return new ForecastDaily[size];
        }
    };

    public double getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(double max_temp) {
        this.max_temp = max_temp;
    }

    public double getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(double min_temp) {
        this.min_temp = min_temp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getRainFall() {
        return rainFall;
    }

    public void setRainFall(double rainFall) {
        this.rainFall = rainFall;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
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

    public double getUVIndex() {
        return UVIndex;
    }

    public void setUVIndex(double UVIndex) {
        this.UVIndex = UVIndex;
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
        parcel.writeDouble(max_temp);
        parcel.writeDouble(min_temp);
        parcel.writeDouble(windSpeed);
        parcel.writeDouble(rainFall);
        parcel.writeInt(probability);
        parcel.writeDouble(visibility);
        parcel.writeDouble(humidity);
        parcel.writeInt(snowProbability);
        parcel.writeInt(will_it_rain);
        parcel.writeDouble(will_it_snow);
        parcel.writeDouble(UVIndex);
        parcel.writeParcelable(condition, i);
    }
}






