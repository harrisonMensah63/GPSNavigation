package com.harryWorld.navigationGPS.map.volley;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrafficJSON {
    private static final String TAG = "StepsJSON";
    public List<LatLng> locationList = new ArrayList<>();
    public List<String> trafficList = new ArrayList<>();

    public List<LatLng> parseDuration(JSONObject jObject) {

        List<LatLng> steps = new ArrayList<>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;

        try {

            jRoutes = jObject.getJSONArray("results");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                LatLng list;
                JSONObject go = ((JSONObject) jRoutes.get(i)).getJSONObject("geometry");

                /** Traversing all legs */
                    double latitude =(Double) ((JSONObject)((JSONObject) go).get("location")).get("lat");
                    double longitude =(Double) ((JSONObject)((JSONObject) go).get("location")).get("lng");
                    locationList.add(new LatLng(latitude,longitude));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return locationList;
    }
    public List<String> parseInTraffic(JSONObject jObject) {

        List<LatLng> steps = new ArrayList<>();
        JSONArray jRoutes = null;
        JSONObject jLegs = null;
        JSONArray jSteps = null;

        try {
            jRoutes = jObject.getJSONArray("results");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                LatLng list;
                String open =(String) ((JSONObject)((JSONObject) jLegs).get("opening_hours")).get("open_now");

                trafficList.add(open);
                /** Traversing all legs */

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return trafficList;
    }
}
