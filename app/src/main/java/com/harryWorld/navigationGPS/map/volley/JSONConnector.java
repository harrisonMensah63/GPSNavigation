package com.harryWorld.navigationGPS.map.volley;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.map.retrofit.DirectionsJSONParser;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.harryWorld.navigationGPS.map.viewModels.VolleyViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class JSONConnector {
    private static final String TAG = "JSONConnector";
    private static JSONConnector instance;
    private static Context mContext;
    private String language;

    public static JSONConnector getInstance(Context context){
        mContext = context;
        if (instance == null){
            instance = new JSONConnector();
        }
        return instance;
    }

    public List<LatLng> coordinatesList = new ArrayList<>();
   public List<LatLng> categoriesList = new ArrayList<>();

    public List<String> instructions = new ArrayList<>();
    public List<String> shortInstructions = new ArrayList<>();
    public List<List<HashMap<String, String>>> routeList = new ArrayList<>();
    public ArrayList<LatLng> points = null;
    public PolylineOptions lineOptions;
    public List<String> durationMatrix = new ArrayList<>();
    public MutableLiveData<PolylineOptions> liveOptions = new MutableLiveData<>();
    public MutableLiveData<List<LatLng>> liveTraffic = new MutableLiveData<>();
    public MutableLiveData<List<String>> liveDistanceMatrix = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private void gettingLanguage(){
        switch (Locale.getDefault().getLanguage()){
            case "de":{
                language = "de";
            }
            break;
            case "es":{
                language = "es";
            }
            break;
            case "ar":{
                language = "ar";
            }
            break;
            case "zh":{
                language = "zh";
            }
            break;
            case "pt":{
                language = "pt";
            }
            break;
            case "fr":{
                language = "fr";
            }
            break;
            default:{
                language = "en";
            }
            break;
        }
    }

    public JSONConnector() {
        gettingLanguage();
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception on download", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask", "DownloadTask : " + data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            StepsParser stepsParser = new StepsParser();
            InstructionParser instructionParser = new InstructionParser();
            ShortParser shortParser = new ShortParser();
            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            stepsParser.execute(result);
            instructionParser.execute(result);
            shortParser.execute(result);
            parserTask.execute(result);
        }
    }
    private class DownloadPolyline extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask", "DownloadTask : " + data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            PolylineParser polylineParser = new PolylineParser();
            PolylineTrafficParser polylineTrafficParser = new PolylineTrafficParser();
            polylineParser.execute(result);
            polylineTrafficParser.execute(result);

        }
    }
    private class DownloadMatrix extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask", "DownloadTask : " + data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            DistanceMatrixParser d = new DistanceMatrixParser();
            DurationMatrixParser du = new DurationMatrixParser();

            du.execute(result);
            d.execute(result);
        }
    }

    private class InstructionParser extends AsyncTask<String, Integer, List<String>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<String> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<String> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                StepsJSON parser = new StepsJSON();

                // Starts parsing data
                routes = parser.parseInstruction(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<String> result) {
            if (instructions.size() > 0) {
                instructions.clear();
            }
            if (result != null && result.size()>0) {
                instructions.addAll(result);
            }
        }
    }

    private class ShortParser extends AsyncTask<String, Integer, List<String>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<String> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<String> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                StepsJSON parser = new StepsJSON();

                // Starts parsing data
                routes = parser.parseShort(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<String> result) {
            if (shortInstructions.size() > 0) {
                shortInstructions.clear();
            }
            if (result != null && result.size()>0) {
                shortInstructions.addAll(result);
            }
            Log.d(TAG, "onPostExecute: short one "+shortInstructions.size());
        }
    }

    private class StepsParser extends AsyncTask<String, Integer, List<LatLng>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<LatLng> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<LatLng> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                StepsJSON parser = new StepsJSON();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<LatLng> result) {
            if (coordinatesList.size() > 0) {
                coordinatesList.clear();
            }

            if (result != null) {
                coordinatesList.addAll(result);
            }
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;


            try {
                jObject = new JSONObject(jsonData[0]);
                com.harryWorld.navigationGPS.map.retrofit.DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: lets see error "+e.getMessage());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            if (result != null) {
                routeList.addAll(result);
            }
            // Traversing through all the routes
            if (result != null) {
                Log.d(TAG, "onChanged: points value entered");

                if (result.size() > 0) {

                    for (int i = 0; i < result.size(); i++) {
                        points = new ArrayList<LatLng>();
                        if (lineOptions == null) {
                            lineOptions = new PolylineOptions();
                        }

                        // Fetching i-th route
                        List<HashMap<String, String>> path = result.get(i);
                        HashMap<String, String> go = path.get(0);
                        // Fetching all the points in i-th route
                        for (int j = 0; j < path.size(); j++) {
                            HashMap<String, String> point = path.get(j);

                            double lat = Double.parseDouble(point.get("lat"));
                            double lng = Double.parseDouble(point.get("lng"));

                            LatLng position = new LatLng(lat, lng);

                            points.add(position);
                        }
                        Log.d(TAG, "onPostExecute: lets check size "+points.size());
                        lineOptions.addAll(points);
                        lineOptions.width(14);
                        Log.d(TAG, "onPostExecute: let");
                        if (liveOptions == null){
                            liveOptions = new MutableLiveData<>();
                        }
                        if (liveOptions.getValue() != null){
                            liveOptions.setValue(null);
                        }
                        liveOptions.setValue(lineOptions);
                        //Adding all the points in the route to LineOptions


                    }
                }
                else{
                    loading.setValue(false);
                    Toast.makeText(mContext, mContext.getString(R.string.no_route_found),Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onPostExecute: but was null");
                }
            }
            else{
                loading.setValue(false);
                Toast.makeText(mContext,mContext.getString(R.string.check_network),Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void traffic(String location, String type){
        String url = categoriesURL(location, type);
        DownloadPolyline downloadPolyline = new DownloadPolyline();

        // Start downloading json data from Google Directions API
        downloadPolyline.execute(url);
    }
    public void getMatrixDistance(String origin, String destination){
        String url = distanceMatrix(destination, origin);
        DownloadMatrix downloadPolyline = new DownloadMatrix();

        // Start downloading json data from Google Directions API
        downloadPolyline.execute(url);
    }
    private class PolylineParser extends AsyncTask<String, Integer, List<LatLng>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<LatLng> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<LatLng> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                TrafficJSON parser = new TrafficJSON();

                // Starts parsing data
                routes = parser.parseDuration(jObject);
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: lets see error "+e.getMessage());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<LatLng> result) {
            if (categoriesList.size() >0){
                categoriesList.clear();
            }
            if (result != null){
                if (result.size() > 0){
                    categoriesList.addAll(result);
                    liveTraffic.setValue(null);
                    liveTraffic.setValue(categoriesList);
                }
                else {
                    loading.setValue(false);
                    Toast.makeText(mContext,mContext.getString(R.string.no_route_found),Toast.LENGTH_SHORT).show();

                }
            }
            else{
                loading.setValue(false);
                Toast.makeText(mContext,mContext.getString(R.string.check_network),Toast.LENGTH_SHORT).show();
            }

        }
    }
    private class PolylineTrafficParser extends AsyncTask<String, Integer, List<String>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<String> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<String> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                TrafficJSON parser = new TrafficJSON();

                // Starts parsing data
                routes = parser.parseInTraffic(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.d(TAG, "doInBackground: lets see "+routes.get(0));
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<String> result) {
            // Log.d(TAG, "onPostExecute: time to go "+result.get(0));
          //  bigLoading.setVisibility(View.GONE);
//            if (result != null && result.size() > 0){
//                Log.d(TAG, "onPostExecute: yes now");
//                if (categoriesCord.size()>0){
//                    Log.d(TAG, "onPostExecute: wooow");
//                    for (int i = 0; i<categoriesCord.size();i++) {
//                        categoriesMarker = mMap.addMarker(new MarkerOptions()
//                                .position(categoriesCord.get(i)));
//                        if (result.get(i).trim().equals("true")){
//                            //open
//                            categoriesMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.open));
//                        }
//                        else{
//                            //close
//                            categoriesMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.close));
//                        }
//                                }
//                }
//                }
        }
    }
    private class DistanceMatrixParser extends AsyncTask<String, Integer, List<String>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<String> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<String> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                MatrixJSON parser = new MatrixJSON();

                // Starts parsing data
                routes = parser.parseDistance(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.d(TAG, "doInBackground: lets see "+routes.get(0));
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<String> result) {
            if (result != null && result.size() > 0){
                Log.d(TAG, "onPostExecute: checking distance size "+result.size());
                liveDistanceMatrix.setValue(null);
                liveDistanceMatrix.setValue(result);
            }
            else {
                Log.d(TAG, "onPostExecute: checking distance size null ");

            }
        }
    }
    private class DurationMatrixParser extends AsyncTask<String, Integer, List<String>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<String> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<String> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                MatrixJSON parser = new MatrixJSON();

                // Starts parsing data
                routes = parser.parseDuration(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.d(TAG, "doInBackground: lets see "+routes.get(0));
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<String> result) {
            if (result != null && result.size() > 0){
                Log.d(TAG, "onPostExecute: checking distance size "+result.size());
                durationMatrix.clear();
                durationMatrix.addAll(result);
            }
        }
    }


    public void drawRoute(LatLng origin, LatLng destination, String mode) {
        // Getting URL to the Google Directions API
        String url = getUrl(origin, destination, mode);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    public String getUrl(LatLng origin, LatLng dest, String mode) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = "mode="+mode+"&"+str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?"+"departure_time=now&" + parameters+"&language=" +language+ "&key=" + "AIzaSyDC1ZA4pYnpaktOV2bNRXZ3j-5CZ_3iM9o";

        return url;
    }
    public String categoriesURL(String location, String type) {

        String output = "json";
        return  "https://maps.googleapis.com/maps/api/place/nearbysearch/" + output + "?"+"location=" + location +"&"+"radius=1500&"+"type="+type+"&language=" +language+ "&key=" + "AIzaSyDC1ZA4pYnpaktOV2bNRXZ3j-5CZ_3iM9o";
    }
    public String distanceMatrix(String destination, String origin) {
        return  "https://maps.googleapis.com/maps/api/distancematrix/" +
                "json?departure_time=now&best_guess=optimistic&" +
                "destinations="+destination+"&origins=enc:"+origin+":"+"&language=" +language+"&key=" + "AIzaSyDC1ZA4pYnpaktOV2bNRXZ3j-5CZ_3iM9o";
    }
}
