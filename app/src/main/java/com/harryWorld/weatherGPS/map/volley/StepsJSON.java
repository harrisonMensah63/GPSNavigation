package com.harryWorld.weatherGPS.map.volley;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StepsJSON {
    private static final String TAG = "StepsJSON";
    public List<String> instructions = new ArrayList<>();
    public List<String> shortInstructions = new ArrayList<>();

    public List<LatLng> parse(JSONObject jObject) {

        List<LatLng> steps = new ArrayList<>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                LatLng list;
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");

                /** Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String inst;
                        String shortIn;
                        double latitude = 0.0;
                        double longitude = 0.0;
                        latitude = (double) ((JSONObject) ((JSONObject) jSteps.get(k)).get("start_location")).get("lat");
                        longitude = (double) ((JSONObject) ((JSONObject) jSteps.get(k)).get("start_location")).get("lng");

                        list = new LatLng(latitude, longitude);
                        steps.add(list);
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return steps;
    }
    public List<String> parseInstruction(JSONObject jObject) {

        List<LatLng> steps = new ArrayList<>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                LatLng list;
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");

                /** Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String inst;
                        String shortIn;
                        inst = (String) (((JSONObject) jSteps.get(k)).get("html_instructions"));
                        instructions.add(inst);
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return instructions;
    }
    public List<String> parseShort(JSONObject jObject) {

        List<LatLng> steps = new ArrayList<>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                LatLng list;
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");

                    JSONObject  distanceJSON = (JSONObject) ((JSONObject) jLegs.get(i)).get("distance");
                    JSONObject  durationJSON = (JSONObject) ((JSONObject) jLegs.get(i)).get("duration");

                    String distance = (String) (( distanceJSON.get("text")));
                    String duration = (String) (( durationJSON.get("text")));
                    shortInstructions.add(distance);
                    shortInstructions.add(duration);
                        jSteps = ((JSONObject) jLegs.get(0)).getJSONArray("steps");
                        String  naviMode = (String) ((JSONObject) jSteps.get(0)).get("travel_mode");
                        shortInstructions.add(naviMode);



            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return shortInstructions;
    }

}