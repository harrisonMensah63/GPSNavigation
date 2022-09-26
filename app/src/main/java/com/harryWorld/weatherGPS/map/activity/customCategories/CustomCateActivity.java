package com.harryWorld.weatherGPS.map.activity.customCategories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.retrofit.GO;
import com.harryWorld.weatherGPS.map.retrofit.pictures.Pictures;
import com.harryWorld.weatherGPS.weather.constant.Decoration;
import com.harryWorld.weatherGPS.weather.repository.WeatherRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.harryWorld.weatherGPS.map.constant.Constant.CUSTOM_CATE;
import static com.harryWorld.weatherGPS.map.constant.Constant.CUSTOM_CATE_A;
import static com.harryWorld.weatherGPS.map.constant.Constant.CUSTOM_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.CUSTOM_LONGITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.LATITUDE_CATE_MAIN;
import static com.harryWorld.weatherGPS.map.constant.Constant.LATITUDE_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.Constant.LONGITUDE_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.Constant.STRING_CATE_SUB;

public class CustomCateActivity extends AppCompatActivity implements OnItemClicked {
    private static final String TAG = "CustomCateActivity";
    TextView message;
    ProgressBar loading;
    private RecyclerView recyclerView;
    CustomCateAdapter adapter;
    private RelativeLayout relativeLayout;
    private boolean isActive;
    private float latitude, longitude;
    Toast toast;
    GO myGo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cate);

        isActive = true;
        loading = findViewById(R.id.progress_bar);
        message = findViewById(R.id.text_message);
        recyclerView = findViewById(R.id.main_recyclerview);
        relativeLayout = findViewById(R.id.show_on_map);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        latitude = preferences.getFloat(CUSTOM_LATITUDE, 0.0f);
        longitude = preferences.getFloat(CUSTOM_LONGITUDE, 0.0f);
        Log.d(TAG, "onCreate: latitude "+latitude);
        Log.d(TAG, "onCreate: longitude "+longitude);
        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_light));
        setRecyclerView();

        if (getIntent().hasExtra(CUSTOM_CATE)){
            String address = getIntent().getStringExtra(CUSTOM_CATE);
            String name = getIntent().getStringExtra(CUSTOM_CATE_A);
            retrievePictures(address);
            adapter.setAddress(name);
            setCategories(address);
        }
        relativeLayout.setOnClickListener(v -> {
            //  if (getIntent().hasExtra(LATITUDE_CATE_MAIN)){
            List<LatLng> ls = new ArrayList<>();
            Intent output = new Intent();
            for (int i= 0; i<myGo.getGeocode().size();i++){
                ls.add(new LatLng(myGo.getGeocode().get(i).getPosition().getLatitude(),myGo.getGeocode().get(i).getPosition().getLongitude()));
            }
            output.putExtra(LATITUDE_CATE_MAIN, (Serializable) ls);
            setResult(CustomCateActivity.RESULT_OK, output);
            finish();
//                }
//                else if (getIntent().hasExtra(LATITUDE_CATE_SUB)){
//                    Intent output = new Intent();
//                    output.putExtra(LATITUDE_CATE_SUB, myGo);
//                    setResult(RESULT_OK, output);
//                    finish();
//                }
        });

    }

    private void setRecyclerView(){
         adapter = new CustomCateAdapter(this, this);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.addItemDecoration(new Decoration(10));
         recyclerView.setAdapter(adapter);
    }
    private void retrievePictures(String query){
        WeatherRepository.getWeatherRepository().getPictures(query)
                .enqueue(new Callback<Pictures>() {
                    @Override
                    public void onResponse(Call<Pictures> call, Response<Pictures> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                adapter.setResults(response.body().getResults());
                                Log.d(TAG, "onResponse: checking response for piccccccccc "+response.body().getResults().get(0).getLink().getPic());
                            }
                            else{
                                Log.d(TAG, "onResponse: pic response was null");
                            }
                        }
                        else {
                            Log.d(TAG, "onResponse: pic was not seccessful");
                        }
                    }

                    @Override
                    public void onFailure(Call<Pictures> call, Throwable t) {
                        Log.d(TAG, "onFailure: there was an error with pic "+t.getMessage());
                    }
                });
    }
    private void setCategories(String address){
        WeatherRepository.getWeatherRepository().getCategories(address,latitude,longitude)
                .enqueue(new Callback<GO>() {
                    @Override
                    public void onResponse(Call<GO> call, Response<GO> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                if (response.body().getGeocode().size() > 0){
                                    loading.setVisibility(View.GONE);
                                adapter.setGeocodeList(response.body().getGeocode());
                                if (response.body().getGeocode().size() > 1) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                }
                                myGo = response.body();
                                //Log.d(TAG, "onResponse: lets check categories______"+response.body().getGeocode().size());
                                     }
                                else{
                                    finish();
                                    Toast.makeText(CustomCateActivity.this, getString(R.string.no)+" "+address+" "+getString(R.string.location_around), Toast.LENGTH_SHORT).show();
                                }
                            }
                            loading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<GO> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Log.d(TAG, "onFailure: there was network failure");
                        Toast.makeText(CustomCateActivity.this,getString(R.string.check_network),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    public void onPhoneViewClicked(int position) {
        String number = myGo.getGeocode().get(position).getPoi().getPhone();
        if (number != null){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
            startActivity(intent);
        }
        else{
            if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                // Toast isn't shown //
                toast = null;
                toast = Toast.makeText(CustomCateActivity.this, "number unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public void onWebsiteViewClick(int position) {
        String url=myGo.getGeocode().get(position).getPoi().getUrl();
        if (url != null) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        else{
            if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                toast =null;
              toast =  Toast.makeText(CustomCateActivity.this, "website unavailable", Toast.LENGTH_SHORT);
              toast.show();
            }
        }
    }

    @Override
    public void onViewClick(int position) {
//        if (getIntent().hasExtra(LATITUDE_CATE_MAIN)){
//            double lan = myGo.getGeocode().get(position).getPosition().getLatitude();
//            double lon = myGo.getGeocode().get(position).getPosition().getLongitude();
//            Intent output = new Intent();
//            output.putExtra(LATITUDE_CATE_MAIN, lan);
//            output.putExtra(LONGITUDE_CATE_MAIN, lon);
//            setResult(RESULT_OK, output);
//            finish();
//        }
//        else if (getIntent().hasExtra(LATITUDE_CATE_SUB)){
            double lan = myGo.getGeocode().get(position).getPosition().getLatitude();
            double lon = myGo.getGeocode().get(position).getPosition().getLongitude();
            String name = myGo.getGeocode().get(position).getAddress().getCounty();
            Intent output = new Intent();
            output.putExtra(LATITUDE_CATE_SUB, lan);
            output.putExtra(LONGITUDE_CATE_SUB, lon);
            output.putExtra(STRING_CATE_SUB, name);

            setResult(RESULT_OK, output);
            finish();
     //   }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        isActive = true;
    }
}
