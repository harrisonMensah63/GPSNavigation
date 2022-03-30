package com.harryWorld.navigationGPS.weather.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.weather.api.Climate;
import com.harryWorld.navigationGPS.weather.constant.DateUtil;
import com.harryWorld.navigationGPS.weather.constant.Decoration;
import com.harryWorld.navigationGPS.weather.forwardActivity.DailyActivity;
import com.harryWorld.navigationGPS.weather.recyclerView.DailyAdapter;
import com.harryWorld.navigationGPS.weather.utils.Daily;
import com.harryWorld.navigationGPS.weather.utils.Resource;
import com.harryWorld.navigationGPS.weather.utils.Weather;
import com.harryWorld.navigationGPS.weather.viewModels.DailyViewModel;
import com.harryWorld.navigationGPS.weather.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.harryWorld.navigationGPS.weather.constant.Constant.BOOLEAN_CHECK_DAILY;


public class DailyFragment extends Fragment {
    private static final String TAG = "DailyFragment";
    private DailyViewModel mViewModel;
    private MainViewModel mainViewModel;
    RecyclerView recyclerView;
    DailyAdapter dailyAdapter;
    private boolean isDay;
    AdView adView;

    private TextView maxTemp, minTemp, maxFeelsLike, minFeelsLike, humidity, dateDetails,
            pressure, uVIndex, visibility, cloud, speed, direction, gust, rain, snow, chance, sunrise, sunset;

    long timeCheck;
    List<Daily> dailyConfirm = new ArrayList<>();


    public static DailyFragment newInstance() {
        return new DailyFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.daily_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(DailyViewModel.class);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        adView = view.findViewById(R.id.daily_adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        instantiate(view);
        getClimate(view);

    }
    private void setDateDetails(Climate climate){
        String month = climate.getForecast().getForecastday().get(0).getDate().substring(5,7);
        String day = climate.getForecast().getForecastday().get(0).getDate().substring(8,10);
        String year = climate.getForecast().getForecastday().get(0).getDate().substring(0,4);

        int day1 = Integer.parseInt(day);
        int month1 = Integer.parseInt(month);
        int year1 = Integer.parseInt(year);

        month = DateUtil.getMonthFromNumber(month);
        String date = month + " " + day;
        String gettingDaysOfWeek = DateUtil.getDay(day1,month1,year1);
        dateDetails.setText(gettingDaysOfWeek+","+date);
    }

    private void settingProperties(Climate climate){
        setDateDetails(climate);
        maxTemp.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getMax_temp())+"°");
        minTemp.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getMin_temp())+"°");
//        maxFeelsLike.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getM())+"°");
//        minFeelsLike.setText(Math.round(mDaily.getApp_min_temp_daily())+"°");
        //description.setText(climate.getForecast().getForecastday().get(0).getDay().getCondition().getDescription());
        humidity.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getHumidity())+"%");
       // pressure.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().get())+"mb");
        uVIndex.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getUVIndex())+"");
        visibility.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getVisibility())+"Km");
        //cloud.setText(climate.getForecast().getForecastday().get(0).getDay().()+"%");
        speed.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getWindSpeed())+"kph");
      //  direction.setText(climate.getForecast().getForecastday().get(0).getDay().getW()+"");
        //gust.setText(mDaily.getDailyWindGust()+"mm");
        rain.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getRainFall())+"mm");
        snow.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getSnowProbability())+"%");
        chance.setText(Math.round(climate.getForecast().getForecastday().get(0).getDay().getProbability())+"%");
        sunrise.setText(climate.getForecast().getForecastday().get(0).getAstro().getSunrise());
        sunset.setText(climate.getForecast().getForecastday().get(0).getAstro().getSunset());
    }
    private void instantiate(View view){
        dateDetails = view.findViewById(R.id.date_detalis);
        maxTemp = view.findViewById(R.id.daily_detail_temp_max);
        minTemp = view.findViewById(R.id.daily_detail_temp_min);
        maxFeelsLike = view.findViewById(R.id.daily_detail_feel_max);
        minFeelsLike = view.findViewById(R.id.daily_detail_feel_min);
        humidity = view.findViewById(R.id.daily_detail_humidity_text);
        pressure = view.findViewById(R.id.daily_detail_pressure_text);
        uVIndex = view.findViewById(R.id.daily_detail_index_text);
        visibility = view.findViewById(R.id.daily_detail_visibity_text);
        cloud = view.findViewById(R.id.daily_detail_cloud_text);
        speed = view.findViewById(R.id.daily_detail_speed_text);
        direction = view.findViewById(R.id.daily_detail_direction_text);
        gust = view.findViewById(R.id.daily_detail_gust_text);
        rain = view.findViewById(R.id.daily_detail_rain_text);
        snow = view.findViewById(R.id.daily_detail_snow_text);
        chance = view.findViewById(R.id.daily_detail_chance_text);
        sunrise = view.findViewById(R.id.daily_detail_sunrise_text);
        sunset = view.findViewById(R.id.daily_detail_sunset_text);
    }
    private void gettingLightMode(View view){
       // updatingDay(true);
        isDay = true;
        Log.d(TAG, "gettingLightMode: daily light mode activated");
        FrameLayout relativeLayout = view.findViewById(R.id.main_daily_layout);
        Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.next_);
        relativeLayout.setBackground(drawable);
    }
    private void gettingDarkMode(View view){
       // updatingDay(false);
        isDay = false;
        Log.d(TAG, "gettingDarkMode: daily dark mode activated");

        FrameLayout relativeLayout = view.findViewById(R.id.main_daily_layout);
        Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.case_);
        relativeLayout.setBackground(drawable);
    }

    private void getClimate(View view){
        mainViewModel.getRetrieveClimate().observe(getViewLifecycleOwner(), new Observer<Climate>() {
            @Override
            public void onChanged(Climate climate) {
                if (climate.getCurrent().getIs_day() == 1){
                    gettingLightMode(view);
                }
                else {
                    gettingDarkMode(view);
                }
                settingProperties(climate);
            }
        });
    }
}




