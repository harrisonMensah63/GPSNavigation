package com.harryWorld.weatherGPS.map.request;

import com.harryWorld.weatherGPS.map.utils.Coordinate;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

class CarDeserializer implements JsonDeserializer<Coordinate> {

        @Override
        public Coordinate deserialize(


                JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray obj = json.getAsJsonArray();
           // String type = obj.get("Type").getAsJsonObject();

            return new Coordinate();
        }
    }

    class CarSerializer implements JsonSerializer<Coordinate> {

        @Override
        public JsonElement serialize(Coordinate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonArray();

           // return obj;
        }
    }