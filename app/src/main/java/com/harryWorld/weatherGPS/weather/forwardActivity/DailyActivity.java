package com.harryWorld.weatherGPS.weather.forwardActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.weather.constant.DateUtil;
import com.harryWorld.weatherGPS.weather.utils.Daily;
import com.harryWorld.weatherGPS.weather.viewModels.CurrentViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DecimalFormat;

import static com.harryWorld.weatherGPS.weather.constant.Constant.BOOLEAN_CHECK_DAILY;

public class DailyActivity extends AppCompatActivity {
    private static final String TAG = "DailyActivity";
    private Daily mDaily;
    AdView adView;
    private CurrentViewModel currentViewModel;
    private boolean isDay;
    private SharedPreferences sharedPreferences;

    private TextView maxTemp, minTemp, maxFeelsLike, minFeelsLike, humidity, dateDetails,
    pressure, uVIndex, visibility, cloud, speed, direction, gust, rain, snow, chance, sunrise, sunset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_dark));
        currentViewModel = new ViewModelProvider(this).get(CurrentViewModel.class);
        instantiate();
        gettingIntent();
        settingSharedPreference();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        adView = findViewById(R.id.daily_adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    private void settingProperties(){
        setDateDetails();
        maxTemp.setText(Math.round(mDaily.getMax_temp_daily())+"°");
        minTemp.setText(Math.round(mDaily.getMin_temp_daily())+"°");
        maxFeelsLike.setText(Math.round(mDaily.getApp_max_temp_daily())+"°");
        minFeelsLike.setText(Math.round(mDaily.getApp_min_temp_daily())+"°");
        //description.setText(mDaily.getDailyDescription().getWeather());
        humidity.setText(Math.round(mDaily.getDailyHumidity())+"%");
        pressure.setText(Math.round(mDaily.getDailyPressure())+"mb");
        uVIndex.setText(Math.round(mDaily.getDailyUVIndex())+"");
        visibility.setText(Math.round(mDaily.getDailyVisibility())+"Km");
        cloud.setText(mDaily.getDailyClouds()+"%");
        speed.setText(Math.round(mDaily.getDailyWindSpeed())+"m/s");
        direction.setText(mDaily.getDailyWindDirection()+"");
        //gust.setText(mDaily.getDailyWindGust()+"mm");
        rain.setText(Math.round(mDaily.getDailyRainFall())+"mm");
        snow.setText(Math.round(mDaily.getDailySnow())+"mm");
        chance.setText(Math.round(mDaily.getDailyPop())+"%");
        setSunriseAndSet();
    }
    private void instantiate(){
        dateDetails = findViewById(R.id.date_detalis);
        maxTemp = findViewById(R.id.daily_detail_temp_max);
        minTemp = findViewById(R.id.daily_detail_temp_min);
        maxFeelsLike = findViewById(R.id.daily_detail_feel_max);
        minFeelsLike = findViewById(R.id.daily_detail_feel_min);
        humidity = findViewById(R.id.daily_detail_humidity_text);
        pressure = findViewById(R.id.daily_detail_pressure_text);
        uVIndex = findViewById(R.id.daily_detail_index_text);
        visibility = findViewById(R.id.daily_detail_visibity_text);
        cloud = findViewById(R.id.daily_detail_cloud_text);
        speed = findViewById(R.id.daily_detail_speed_text);
        direction = findViewById(R.id.daily_detail_direction_text);
        gust = findViewById(R.id.daily_detail_gust_text);
        rain = findViewById(R.id.daily_detail_rain_text);
        snow = findViewById(R.id.daily_detail_snow_text);
        chance = findViewById(R.id.daily_detail_chance_text);
        sunrise = findViewById(R.id.daily_detail_sunrise_text);
        sunset = findViewById(R.id.daily_detail_sunset_text);
    }
    private void gettingIntent(){
        if (getIntent().hasExtra("dailyData")){
            mDaily = getIntent().getParcelableExtra("dailyData");
//
            settingProperties();
        }
        if (getIntent().hasExtra(BOOLEAN_CHECK_DAILY)){
            isDay = getIntent().getBooleanExtra(BOOLEAN_CHECK_DAILY,true);

                if (isDay){
                    gettingLightMode();
                    Log.d(TAG, "gettingIntent: light");
                }
                else {
                    Log.d(TAG, "gettingIntent: dark");
                    gettingDarkMode();
                }
        }
    }

    private void settingSharedPreference(){
        sharedPreferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", "value");
        editor.putBoolean("boolean",isDay);
        editor.apply();
        boolean getDay = sharedPreferences.getBoolean("boolean",true);

        if (getDay){
            gettingLightMode();
        }
        else {
            gettingDarkMode();
        }
    }
    private void gettingLightMode(){
        Log.d(TAG, "gettingLightMode: light mode activated");
        LinearLayout dailyLinear = findViewById(R.id.head_daily_activity);
        RelativeLayout dailyLayout = findViewById(R.id.daily_main_layout);

        Drawable linear = ContextCompat.getDrawable(this,R.drawable.next_);
        dailyLinear.setBackground(linear);

        Drawable main = ContextCompat.getDrawable(this,R.color.tab_light);
        dailyLayout.setBackground(main);
    }
    private void gettingDarkMode(){
        Log.d(TAG, "gettingDarkMode: dark mode activated");
        LinearLayout dailyLinear = findViewById(R.id.head_daily_activity);
        RelativeLayout dailyLayout = findViewById(R.id.daily_main_layout);

        Drawable linear = ContextCompat.getDrawable(this,R.drawable.case_);
        dailyLinear.setBackground(linear);

        Drawable main = ContextCompat.getDrawable(this,R.color.tab_dark);
        dailyLayout.setBackground(main);
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
        super.onResume();
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
    private void setDateDetails(){
        String month = mDaily.getDailyTime().substring(5,7);
        String day = mDaily.getDailyTime().substring(8,10);
        String year = mDaily.getDailyTime().substring(0,4);

        int day1 = Integer.parseInt(day);
        int month1 = Integer.parseInt(month);
        int year1 = Integer.parseInt(year);

        month = DateUtil.getMonthFromNumber(month);
        String date = month + " " + day;
        String gettingDaysOfWeek = DateUtil.getDay(day1,month1,year1);
        dateDetails.setText(gettingDaysOfWeek+","+date);
    }
    private void setSunriseAndSet(){
        String rise = mDaily.getSunrise_ts_daily();
        String set = mDaily.getSunset_ts_daily();
        int riseHour = Integer.parseInt(rise.substring(0,2));
        int riseMinute = Integer.parseInt(rise.substring(3));

        int setHour = Integer.parseInt(set.substring(0,2));
        int setMinute = Integer.parseInt(set.substring(3));

        riseHour += 8;
        setHour += 8;
        riseMinute -=16;
        setMinute -=20;

        if (riseHour >= 24){
            riseHour -=24;
        }
        if (riseMinute >= 60 ){
            riseMinute -=60;
        }
        if (riseMinute<0){
            riseMinute +=60;
        }
        if (setMinute < 0){
            setMinute += 60;
        }
        sunrise.setText(new DecimalFormat("00").format(riseHour)+":"+new DecimalFormat("00").format(riseMinute));
        sunset.setText(new DecimalFormat("00").format(setHour)+":"+new DecimalFormat("00").format(setMinute));
    }
}