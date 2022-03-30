package com.harryWorld.navigationGPS.weather.fragment;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.weather.api.Climate;
import com.harryWorld.navigationGPS.weather.constant.DateUtil;
import com.harryWorld.navigationGPS.weather.utils.Current;
import com.harryWorld.navigationGPS.weather.viewModels.CurrentViewModel;
import com.harryWorld.navigationGPS.weather.viewModels.MainViewModel;

import java.util.List;
import java.util.Locale;

public class CurrentFragment extends Fragment {

    TextView currentTemp;
    TextView sunsetTime;
    TextView currentDate;
    TextView cloudInfo;
    TextView cloudPercentage;
    TextView humidity;
    TextView humidityResults;
    TextView pressureResults;
    TextView index;
    TextView indexResults;
    TextView feelsLike;
    TextView feelsResults;
    TextView visibility;
    TextView visibilityResults;
    TextView wind;
    TextView windDirection;
    TextView windResults;
    TextView sunriseTime;
    public CurrentViewModel mViewModel;
    boolean alreadyExecuted = true;
    private SharedPreferences sharedPreferences;
    private boolean isDay;
    View mView;
    private MainViewModel mainViewModel;

    long timeCheck;

    private static final String TAG = "CurrentFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.current_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(CurrentViewModel.class);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        mView = view;
        setProperties(view);
        getClimate(view);
            timeCheck = System.currentTimeMillis()/1000;
            if (Locale.getDefault().getLanguage().equals("de")){
                TextView humidity = view.findViewById(R.id.humidity);
                TextView pressure = view.findViewById(R.id.pressure);
                TextView index = view.findViewById(R.id.index);
                TextView feelsLike = view.findViewById(R.id.app_temp);
                TextView visibility = view.findViewById(R.id.visibility);
                TextView wind = view.findViewById(R.id.wind);
                humidity.setTextSize(14f);
                pressure.setTextSize(14f);
                index.setTextSize(14f);
                feelsLike.setTextSize(14f);
                visibility.setTextSize(14f);
                wind.setTextSize(14f);
            }


        }



    private void gettingDarkMode(View view){
        isDay = false;
        RelativeLayout mainLayout = view.findViewById(R.id.main_layout);
        RelativeLayout humidityLayout = view.findViewById(R.id.layout_humidity);
        RelativeLayout pressureLayout = view.findViewById(R.id.layout_pressure);
        RelativeLayout indexLayout = view.findViewById(R.id.layout_index);
        RelativeLayout feelLayout = view.findViewById(R.id.layout_feel);
        RelativeLayout visibilityLayout = view.findViewById(R.id.layout_visibility);
        RelativeLayout  windLayout = view.findViewById(R.id.layout_wind);

        if (getActivity() != null) {
            Log.d(TAG, "checkCurrentTime: dark mode activated");
            Drawable main = ContextCompat.getDrawable(getActivity(), R.drawable.case_);
            Drawable humidity = ContextCompat.getDrawable(getActivity(), R.drawable.rectangle_4);
            Drawable pressure = ContextCompat.getDrawable(getActivity(), R.drawable.rectangle_4);
            Drawable index = ContextCompat.getDrawable(getActivity(), R.drawable.rectangle_4);
            Drawable feel = ContextCompat.getDrawable(getActivity(), R.drawable.rectangle_4);
            Drawable visibility = ContextCompat.getDrawable(getActivity(), R.drawable.rectangle_4);
            Drawable wind = ContextCompat.getDrawable(getActivity(), R.drawable.rectangle_4);

                mainLayout.setBackground(main);
                humidityLayout.setBackground(humidity);
                pressureLayout.setBackground(pressure);
                indexLayout.setBackground(index);
                feelLayout.setBackground(feel);
                visibilityLayout.setBackground(visibility);
                windLayout.setBackground(wind);

        }
    }

    private void gettingLightMode(View view) {
        isDay = true;
        RelativeLayout mainLayout = view.findViewById(R.id.main_layout);
        RelativeLayout humidityLayout = view.findViewById(R.id.layout_humidity);
        RelativeLayout pressureLayout = view.findViewById(R.id.layout_pressure);
        RelativeLayout indexLayout = view.findViewById(R.id.layout_index);
        RelativeLayout feelLayout = view.findViewById(R.id.layout_feel);
        RelativeLayout visibilityLayout = view.findViewById(R.id.layout_visibility);
        RelativeLayout windLayout = view.findViewById(R.id.layout_wind);

        if (getActivity() != null) {
            Log.d(TAG, "checkCurrentTime: light mode activated");
            Drawable main = ContextCompat.getDrawable(getActivity(), R.drawable.next_);
            Drawable humidity = ContextCompat.getDrawable(getActivity(), R.drawable.day_rect);
            Drawable pressure = ContextCompat.getDrawable(getActivity(), R.drawable.day_rect);
            Drawable index = ContextCompat.getDrawable(getActivity(), R.drawable.day_rect);
            Drawable feel = ContextCompat.getDrawable(getActivity(), R.drawable.day_rect);
            Drawable visibility = ContextCompat.getDrawable(getActivity(), R.drawable.day_rect);
            Drawable wind = ContextCompat.getDrawable(getActivity(), R.drawable.day_rect);

            mainLayout.setBackground(main);
            humidityLayout.setBackground(humidity);
            pressureLayout.setBackground(pressure);
            indexLayout.setBackground(index);
            feelLayout.setBackground(feel);
            visibilityLayout.setBackground(visibility);
            windLayout.setBackground(wind);
        }
    }



//    private void getRecentData(){
//        mViewModel.getRetrieveData().observe(getViewLifecycleOwner(), new Observer<List<Current>>() {
//            @Override
//            public void onChanged(List<Current> currentList) {
//                if(getViewLifecycleOwner().getLifecycle().getCurrentState()== Lifecycle.State.RESUMED) {
//                    if (currentList != null) {
//
//                        if (currentList.size() > 0) {
//                            Log.d(TAG, "onChanged: recent was not null "+currentList.size());
//                            setCurrentProperties(currentList.get(currentList.size() - 1));
//                        } else {
//                            Log.d(TAG, "onChanged: current size was null");
//                        }
//                    } else {
//                        Log.d(TAG, "onChanged: getrecent data retrieving was null");
//
//                    }
//                }
//            }
//        });
//    }

    private void setProperties(View view) {
        currentTemp = view.findViewById(R.id.temp);
        currentDate = view.findViewById(R.id.date);

        cloudInfo = view.findViewById(R.id.cloud_info);
        cloudPercentage = view.findViewById(R.id.cloud_percentage);

        humidity = view.findViewById(R.id.humidity);
        humidityResults = view.findViewById(R.id.humidity_result);

        pressureResults = view.findViewById(R.id.pressure_result);

        index = view.findViewById(R.id.index);
        indexResults = view.findViewById(R.id.index_result);


        feelsLike = view.findViewById(R.id.app_temp);
        feelsResults = view.findViewById(R.id.feels_result);

        visibility = view.findViewById(R.id.visibility);
        visibilityResults = view.findViewById(R.id.visibility_result);

        wind = view.findViewById(R.id.wind);
        windDirection = view.findViewById(R.id.wind_direction);
        windResults = view.findViewById(R.id.wind_result);

        sunriseTime = view.findViewById(R.id.sunrise_time);
        sunsetTime = view.findViewById(R.id.sunset_time);

    }

    private void setCurrentProperties(Climate climate) {
        Log.d(TAG, "setCurrentProperties: getting current "+climate.getLocation().getName());
        String month = climate.getForecast().getForecastday().get(0).getDate().substring(5, 7);
       String month1 = DateUtil.getMonthFromNumber(month);
        String day = climate.getForecast().getForecastday().get(0).getDate().substring(8, 10);
        String year = climate.getForecast().getForecastday().get(0).getDate().substring(0,4);

        int day1 = Integer.parseInt(day);
        int month2 = Integer.parseInt(month);
        Log.d(TAG, "setCurrentProperties: checking input "+month);
        int year1 = Integer.parseInt(year);
        String gettingDaysOfWeek = DateUtil.getDay(day1,month2,year1);

        String date = gettingDaysOfWeek+","+ month1 + " " + day;

        currentTemp.setText(Math.round(climate.getCurrent().getTemp())+"°");
        sunsetTime.setText(climate.getForecast().getForecastday().get(0).getAstro().getSunset());
        currentDate.setText(date);
        if (climate.getCurrent().getCondition() != null) {
            cloudInfo.setText(climate.getCurrent().getCondition().getDescription());
        }
        cloudPercentage.setText(climate.getCurrent().getCloud()+"%"+getString(R.string.cloud_coverage));
        humidityResults.setText(Math.round(climate.getCurrent().getHumidity())+"%");
        pressureResults.setText(climate.getCurrent().getPressure()+" mb");
        indexResults.setText(String.valueOf(Math.round(climate.getCurrent().getUvIndex())));
        feelsResults.setText(Math.round(climate.getCurrent().getFeelLike())+"°C");
        visibilityResults.setText(climate.getCurrent().getVisibility()+"Km");
        windDirection.setText(String.valueOf(climate.getCurrent().getWindDirection()));
        windResults.setText(String.valueOf(Math.round(climate.getCurrent().getWindSpeed())));
        sunriseTime.setText(climate.getForecast().getForecastday().get(0).getAstro().getSunrise());
    }

    @Override
    public void onDestroyView() {
        if (!alreadyExecuted){
            alreadyExecuted =true;
        }
        Log.d(TAG, "onDestroy: on destroy called");
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPaused: on pause called");
        if (!alreadyExecuted){
            alreadyExecuted =true;
        }
        super.onPause();
    }

//    private void settingSharedPreference(View view){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        boolean getDay = preferences.getBoolean("isDay", true);
//        if (getDay){
//            gettingLightMode(view);
//        }
//        else {
//            gettingDarkMode(view);
//        }
//    }
//    private void checkInsertMode(){
//        mViewModel.currentInsertMode.observe(getViewLifecycleOwner(), new Observer<CurrentViewModel.CurrentInsertMode>() {
//            @Override
//            public void onChanged(CurrentViewModel.CurrentInsertMode currentInsertMode) {
//                if (currentInsertMode != null){
//                    Log.d(TAG, "onChanged: currenthjhjgjgjhgjhjhjhjhjhjhjhllllllllllllllll");
//                    switch(currentInsertMode){
//                        case AVAILABLE:{
//                            Log.d(TAG, "onChanged: currentllllllllllllllll");
//                           getRecentData();
//                        }
//                        break;
//                        case ERROR:{
//                            try {
//                                Toast.makeText(getActivity(), getString(R.string.check_network), Toast.LENGTH_SHORT).show();
//                            }
//                            catch (Exception e){
//                                Log.d(TAG, "onChanged: checking eception "+e.toString());
//                            }
//                        }
//                        break;
//                        case ABORT:{
//
//                        }
//                        break;
//                    }
//                }
//                else{
//                    Log.d(TAG, "onChanged: current was null");
//                }
//            }
//        });
//    }

    private void getClimate(View view){
        mainViewModel.getRetrieveClimate().observe(getViewLifecycleOwner(), new Observer<Climate>() {
            @Override
            public void onChanged(Climate climate) {
                if (climate != null){
                    if (climate.getCurrent().getIs_day() == 1){
                        gettingLightMode(view);
                    }
                    else {
                        gettingDarkMode(view);
                    }
                    setCurrentProperties(climate);
                }
                else{
                    try {
                        Toast.makeText(getActivity(),getString(R.string.check_network),Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Log.d(TAG, "onChanged: toast returned an error "+e.getMessage());
                    }
                }
            }
        });
    }
}



