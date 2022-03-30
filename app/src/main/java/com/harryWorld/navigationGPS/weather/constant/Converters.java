package com.harryWorld.navigationGPS.weather.constant;

import androidx.room.TypeConverter;

import com.harryWorld.navigationGPS.weather.utils.Weather1;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converters {
        @TypeConverter
        public static Weather1 fromString(String value) {
            Type listType = new TypeToken<Weather1>() {}.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String fromArrayList(Weather1 list) {
            Gson gson = new Gson();
            String json = gson.toJson(list);
            return json;
        }
    }
