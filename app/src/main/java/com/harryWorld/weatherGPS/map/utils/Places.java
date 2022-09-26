package com.harryWorld.weatherGPS.map.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Places implements Parcelable {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private double latitude;
    private double longitude;
    private String placeName;
    private String cityName;
    private String Title;
    private String content;
    private Double Temp;
    private int rain;
    private Double humidity;
    private double wind;
    private int errorReturn;
    private String errorMessage;


    @Ignore
    public Places(int id, double latitude, double longitude, String placeName,
                  String title, String content, Double temp, int rain, Double humidity,
                  double wind, int errorReturn, String errorMessage, String cityname) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.cityName = cityname;
        Title = title;
        this.content = content;
        Temp = temp;
        this.rain = rain;
        this.humidity = humidity;
        this.wind = wind;
        this.errorReturn = errorReturn;
        this.errorMessage = errorMessage;
    }

    public Places() {
    }

    protected Places(Parcel in) {
        id = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        placeName = in.readString();
        cityName = in.readString();
        Title = in.readString();
        content = in.readString();
        if (in.readByte() == 0) {
            Temp = null;
        } else {
            Temp = in.readDouble();
        }
        rain = in.readInt();
        if (in.readByte() == 0) {
            humidity = null;
        } else {
            humidity = in.readDouble();
        }
        wind = in.readDouble();
        errorReturn = in.readInt();
        errorMessage = in.readString();
    }

    public static final Creator<Places> CREATOR = new Creator<Places>() {
        @Override
        public Places createFromParcel(Parcel in) {
            return new Places(in);
        }

        @Override
        public Places[] newArray(int size) {
            return new Places[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getTemp() {
        return Temp;
    }

    public void setTemp(Double temp) {
        Temp = temp;
    }

    public int getRain() {
        return rain;
    }

    public void setRain(int rain) {
        this.rain = rain;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public int getErrorReturn() {
        return errorReturn;
    }

    public void setErrorReturn(int errorReturn) {
        this.errorReturn = errorReturn;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(placeName);
        dest.writeString(cityName);
        dest.writeString(Title);
        dest.writeString(content);
        if (Temp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Temp);
        }
        dest.writeInt(rain);
        if (humidity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(humidity);
        }
        dest.writeDouble(wind);
        dest.writeInt(errorReturn);
        dest.writeString(errorMessage);
    }
}
