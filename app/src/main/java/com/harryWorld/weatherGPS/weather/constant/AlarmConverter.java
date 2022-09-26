package com.harryWorld.weatherGPS.weather.constant;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harryWorld.weatherGPS.schedule.Alarm;

import java.lang.reflect.Type;

public class AlarmConverter {
    @TypeConverter
    public static Alarm fromString(String value) {
        Type listType = new TypeToken<Alarm>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(Alarm list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
