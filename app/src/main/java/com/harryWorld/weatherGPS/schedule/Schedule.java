package com.harryWorld.weatherGPS.schedule;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.harryWorld.weatherGPS.weather.constant.AlarmConverter;

@Entity
public class Schedule implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(AlarmConverter.class)
    Alarm alarm;
    private boolean enableArrivalTime;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private String fromDestination;
    private String finalDestination;
    private String dayOfTheWeek;
    private String date;
    private String time;
    private String name;
    private String errorMessage;

    private double fromLatitudeValue;
    private double fromLongitudeValue;
    private double finalLatitudeValue;
    private double finalLongitudeValue;

    @Ignore
    public Schedule(int id, Alarm alarm, boolean enableArrivalTime, int hour,
                    int minute, int day, int month, int year, String fromDestination,
                    String finalDestination, String dayOfTheWeek, String date,
                    String time, String name, String errorMessage, double fromLatitudeValue,
                    double fromLongitudeValue, double finalLatitudeValue, double finalLongitudeValue) {
        this.id = id;
        this.alarm = alarm;
        this.enableArrivalTime = enableArrivalTime;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.fromDestination = fromDestination;
        this.finalDestination = finalDestination;
        this.dayOfTheWeek = dayOfTheWeek;
        this.date = date;
        this.time = time;
        this.name = name;
        this.errorMessage = errorMessage;
        this.fromLatitudeValue = fromLatitudeValue;
        this.fromLongitudeValue = fromLongitudeValue;
        this.finalLatitudeValue = finalLatitudeValue;
        this.finalLongitudeValue = finalLongitudeValue;
    }

    public Schedule(){}

    protected Schedule(Parcel in) {
        id = in.readInt();
        enableArrivalTime = in.readByte() != 0;
        hour = in.readInt();
        minute = in.readInt();
        day = in.readInt();
        month = in.readInt();
        year = in.readInt();
        fromDestination = in.readString();
        finalDestination = in.readString();
        dayOfTheWeek = in.readString();
        date = in.readString();
        time = in.readString();
        name = in.readString();
        errorMessage = in.readString();
        fromLatitudeValue = in.readDouble();
        fromLongitudeValue = in.readDouble();
        finalLatitudeValue = in.readDouble();
        finalLongitudeValue = in.readDouble();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public boolean isEnableArrivalTime() {
        return enableArrivalTime;
    }

    public void setEnableArrivalTime(boolean enableArrivalTime) {
        this.enableArrivalTime = enableArrivalTime;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination = fromDestination;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public double getFromLatitudeValue() {
        return fromLatitudeValue;
    }

    public void setFromLatitudeValue(double fromLatitudeValue) {
        this.fromLatitudeValue = fromLatitudeValue;
    }

    public double getFromLongitudeValue() {
        return fromLongitudeValue;
    }

    public void setFromLongitudeValue(double fromLongitudeValue) {
        this.fromLongitudeValue = fromLongitudeValue;
    }

    public double getFinalLatitudeValue() {
        return finalLatitudeValue;
    }

    public void setFinalLatitudeValue(double finalLatitudeValue) {
        this.finalLatitudeValue = finalLatitudeValue;
    }

    public double getFinalLongitudeValue() {
        return finalLongitudeValue;
    }

    public void setFinalLongitudeValue(double finalLongitudeValue) {
        this.finalLongitudeValue = finalLongitudeValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (enableArrivalTime ? 1 : 0));
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeInt(day);
        dest.writeInt(month);
        dest.writeInt(year);
        dest.writeString(fromDestination);
        dest.writeString(finalDestination);
        dest.writeString(dayOfTheWeek);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(name);
        dest.writeString(errorMessage);
        dest.writeDouble(fromLatitudeValue);
        dest.writeDouble(fromLongitudeValue);
        dest.writeDouble(finalLatitudeValue);
        dest.writeDouble(finalLongitudeValue);
    }
}
