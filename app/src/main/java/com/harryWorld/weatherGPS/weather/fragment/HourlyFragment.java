package com.harryWorld.weatherGPS.weather.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.weather.constant.Decoration;
import com.harryWorld.weatherGPS.weather.forwardActivity.HourlyActivity;
import com.harryWorld.weatherGPS.weather.recyclerView.HourlyAdapter;
import com.harryWorld.weatherGPS.weather.utils.Hourly;
import com.harryWorld.weatherGPS.weather.viewModels.HourlyViewModel;
import com.harryWorld.weatherGPS.weather.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;


public class HourlyFragment extends Fragment implements HourlyAdapter.MyViewHolder.WeatherListener {
    private static final String TAG = "HourlyFragment";
    private HourlyViewModel mViewModel;
    private MainViewModel mainViewModel;
    HourlyAdapter adapter;
    RecyclerView recyclerView;
    private boolean isDay;
    long timeCheck;
    List<Hourly> hourListNow = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public static HourlyFragment newInstance() {
        return new HourlyFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.hourly_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(HourlyViewModel.class);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_hourly);
        setRecyclerView();

        getClimate(view);

    }

    private void gettingLightMode(View view){
        isDay = true;
        LinearLayout relativeLayout = view.findViewById(R.id.main_hourly_layout);
        Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.next_);
        relativeLayout.setBackground(drawable);
    }
    private void gettingDarkMode(View view){
        isDay = false;
        LinearLayout relativeLayout = view.findViewById(R.id.main_hourly_layout);
        Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.case_);
        relativeLayout.setBackground(drawable);
    }




//    private void getRecentData(){
//        mViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.AVAILABLE);
//        Log.d(TAG, "getRecentData: entered hourly");
//        mViewModel.getRetrieveHourly().observe(getViewLifecycleOwner(), new Observer<List<Hourly>>() {
//            @Override
//            public void onChanged(List<Hourly> hourlyList) {
//                if (mViewModel.hourlyInsertMode.getValue() != null && mViewModel.hourlyInsertMode.getValue() == HourlyViewModel.HourlyInsertMode.AVAILABLE) {
//                    Log.d(TAG, "onChanged: came this far");
//                    // if(getViewLifecycleOwner().getLifecycle().getCurrentState()== Lifecycle.State.RESUMED) {
//                    Log.d(TAG, "onChanged: was here too");
//                    if (hourlyList != null && mViewModel.hourlyInsertMode.getValue() == HourlyViewModel.HourlyInsertMode.AVAILABLE ) {
//                        mViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.ABORT);
//                        //      mViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.ABORT);
//                        timeCheck = System.currentTimeMillis() / 1000;
//                        hourListNow.clear();
//                        hourListNow.addAll(hourlyList);
//                        adapter.setMyHourlyWeather(hourlyList);
//                        Log.d(TAG, "onChanged: current was not null " + hourlyList.size());
//                    } else {
//                        Log.d(TAG, "onChanged: data retrieving was null");
//                    }
//                }
//              //  }
//            }
//        });
//    }
    private void setRecyclerView() {
        adapter = new HourlyAdapter(getActivity(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        Decoration decoration = new Decoration(20);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setElevation(5);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getPosition(int position) {
        Intent intent = new Intent(getActivity(), HourlyActivity.class);
        intent.putExtra("hourlyData",adapter.getPosition(position));
        startActivity(intent);
    }

    private void getClimate(View view){
        mainViewModel.getRetrieveClimate().observe(getViewLifecycleOwner(), climate -> {
            if (climate != null) {
                if (climate.getCurrent().getIs_day() == 1) {
                    gettingLightMode(view);
                }
                else {
                    gettingDarkMode(view);
                }
                adapter.setMyHourlyWeather(climate);
            }
        });
    }
}







