package com.harryWorld.navigationGPS.map.volley;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MatrixJSON {
    private static final String TAG = "StepsJSON";
    public List<String> locationList = new ArrayList<>();
    public List<String> trafficList = new ArrayList<>();

    public List<String> parseDistance(JSONObject jObject) {

        List<LatLng> steps = new ArrayList<>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;

        try {

            jRoutes = jObject.getJSONArray("rows");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("elements");

                for (int j = 0; j < jLegs.length(); j++) {
                    String duration =(String) ((JSONObject)((JSONObject)jLegs.get(j)).get("distance")).get("text");
                    String longitude =(String) ((JSONObject)((JSONObject)jLegs.get(j)).get("duration_in_traffic")).get("text");
                    locationList.add(duration);
                }
                    /** Traversing all legs */

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return locationList;
    }
    public List<String> parseDuration(JSONObject jObject) {

        List<LatLng> steps = new ArrayList<>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;

        try {

            jRoutes = jObject.getJSONArray("rows");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("elements");

                for (int j = 0; j < jLegs.length(); j++) {
                    String duration =(String) ((JSONObject)((JSONObject)jLegs.get(j)).get("duration_in_traffic")).get("text");
                    trafficList.add(duration);
                }
                /** Traversing all legs */

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return trafficList;
    }
}
