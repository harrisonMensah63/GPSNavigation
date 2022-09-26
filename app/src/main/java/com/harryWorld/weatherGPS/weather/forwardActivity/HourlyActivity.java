package com.harryWorld.weatherGPS.weather.forwardActivity;


import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.weather.api.forecast.ForecastHourly;
import com.harryWorld.weatherGPS.weather.utils.Hourly;
import com.harryWorld.weatherGPS.weather.viewModels.CurrentViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class HourlyActivity extends AppCompatActivity {
    private static final String TAG = "HourlyActivity";

    private TextView temp, feelsLike, pressure, visibility, uVIndex, timeStamp,
    cloud, humidity, probability, rain, snow, snowDepth, windSpeed, windDirection, windGust;
    private Hourly mHourly;
    private boolean isDay;
    private SharedPreferences sharedPreferences;

    AdView adView;
    private CurrentViewModel currentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_dark));
        currentViewModel = new ViewModelProvider(this).get(CurrentViewModel.class);
        instantiate();
        ForecastHourly forecastHourly = getIntent().getParcelableExtra("hourlyData");
        settingProperties(forecastHourly);

        if (forecastHourly.getIs_day() == 1){
            gettingLightMode();
        }
        else {
            gettingDarkMode();
        }
        MobileAds.initialize(this, initializationStatus -> {

        });

        adView = findViewById(R.id.hourly_adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    private void settingProperties(ForecastHourly mHourly){
       // try {

            temp.setText(Math.round(mHourly.getTemp()) + "°C");
            feelsLike.setText((Math.round(mHourly.getFeelLike()) + "°"));
            pressure.setText(Math.round(mHourly.getPressure()) + "mb");
            visibility.setText(Math.round(mHourly.getVisibility()) + "Km");
            uVIndex.setText(String.valueOf(Math.round(mHourly.getUvIndex())));
            timeStamp.setText(mHourly.getTime().substring(11,16));
            cloud.setText(mHourly.getCloud() + "%");
            humidity.setText(Math.round(mHourly.getHumidity()) + "%");
            probability.setText(Math.round(mHourly.getProbability()) + "%");
            rain.setText(Math.round(mHourly.getRainFall()) + "mm");
            snow.setText(mHourly.getSnowProbability() + "%");
            //snowDepth.setText(Math.round(mHourly.getHourlySnowDepth()));
            windSpeed.setText(Math.round(mHourly.getWindSpeed()) + "kph");
            windDirection.setText(mHourly.getWindDirection());
            windGust.setText(Math.round(mHourly.getWindGust())+"kph");

    }
    private void instantiate(){
        temp = findViewById(R.id.hourly_detail_temp);
        feelsLike = findViewById(R.id.hourly_detail_feelsLike_text);
        pressure = findViewById(R.id.hourly_detail_pressure_text);
        visibility = findViewById(R.id.hourly_detail_visibility_text);
        timeStamp = findViewById(R.id.hourly_time_details);
        uVIndex = findViewById(R.id.hourly_detail_uVIndex_text);
        cloud = findViewById(R.id.hourly_detail_cloud_text);
        humidity = findViewById(R.id.hourly_detail_humidity_text);
        rain = findViewById(R.id.hourly_rain_details_text);
        snow = findViewById(R.id.hourly_snow_details_text);
        snowDepth = findViewById(R.id.hourly_depth_details_text);
        windSpeed = findViewById(R.id.hourly_speed_details_text);
        windDirection = findViewById(R.id.hourly_direction_details_text);
        windGust = findViewById(R.id.hourly_gust_details_text);
        probability = findViewById(R.id.hourly_detail_probability_text);


    }
    private void gettingLightMode(){
        Log.d(TAG, "gettingLightMode: light mode activated");
        LinearLayout hourlyLinear = findViewById(R.id.linear_hourly);
        RelativeLayout hourlyLayout = findViewById(R.id.relative_top);

        Drawable linear = ContextCompat.getDrawable(this,R.drawable.next_);
        hourlyLinear.setBackground(linear);

        Drawable main = ContextCompat.getDrawable(this,R.color.tab_light);
        hourlyLayout.setBackground(main);
    }
    private void gettingDarkMode(){
        Log.d(TAG, "gettingDarkMode: dark mode activated");
        LinearLayout hourlyLinear = findViewById(R.id.linear_hourly);
        RelativeLayout hourlyLayout = findViewById(R.id.relative_top);

        Drawable linear = ContextCompat.getDrawable(this,R.drawable.case_);
        hourlyLinear.setBackground(linear);

        Drawable main = ContextCompat.getDrawable(this,R.color.tab_dark);
        hourlyLayout.setBackground(main);
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }
    @Override
    public void onResume() {
        Log.d(TAG, "onResume: onResume called");
        super.onResume();
        //gettingCurrentWeather();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {

        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}