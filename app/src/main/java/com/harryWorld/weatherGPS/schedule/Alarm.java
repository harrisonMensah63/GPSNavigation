package com.harryWorld.weatherGPS.schedule;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private boolean alarm;
    private int errorReturn;
    private String errorMessage;
    private String alarmName;
    private String time;
    private boolean arrive;
    private double  firstLatitude;
    private double  firstLongitude;
    private double  secondLatitude;
    private double  secondLongitude;

    @Ignore
    public Alarm(int id, int hour, int minute, int day, int month,
                 int year, boolean alarm, int errorReturn, String errorMessage,
                 String alarmName, String time, boolean arrive,double firstLatitude,
                 double firstLongitude, double secondLatitude, double secondLongitude) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.alarm = alarm;
        this.errorReturn = errorReturn;
        this.errorMessage = errorMessage;
        this.alarmName = alarmName;
        this.time = time;
        this.arrive = arrive;
        this.firstLatitude = firstLatitude;
        this.firstLongitude = firstLongitude;
        this.secondLatitude = secondLatitude;
        this.secondLongitude = secondLongitude;
    }


    public Alarm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
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

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isArrive() {
        return arrive;
    }

    public void setArrive(boolean arrive) {
        this.arrive = arrive;
    }

    public double getFirstLatitude() {
        return firstLatitude;
    }

    public void setFirstLatitude(double firstLatitude) {
        this.firstLatitude = firstLatitude;
    }

    public double getFirstLongitude() {
        return firstLongitude;
    }

    public void setFirstLongitude(double firstLongitude) {
        this.firstLongitude = firstLongitude;
    }

    public double getSecondLatitude() {
        return secondLatitude;
    }

    public void setSecondLatitude(double secondLatitude) {
        this.secondLatitude = secondLatitude;
    }

    public double getSecondLongitude() {
        return secondLongitude;
    }

    public void setSecondLongitude(double secondLongitude) {
        this.secondLongitude = secondLongitude;
    }
}
