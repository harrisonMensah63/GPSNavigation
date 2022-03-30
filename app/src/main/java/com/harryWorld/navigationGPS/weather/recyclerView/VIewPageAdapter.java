package com.harryWorld.navigationGPS.weather.recyclerView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.harryWorld.navigationGPS.weather.fragment.CurrentFragment;
import com.harryWorld.navigationGPS.weather.fragment.DailyFragment;
import com.harryWorld.navigationGPS.weather.fragment.HourlyFragment;


public class VIewPageAdapter extends FragmentStateAdapter {


    public VIewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CurrentFragment(); //ChildFragment1 at position 0
            case 1:
                return new HourlyFragment();
            //ChildFragment2 at position 1
            case 2:
                return new DailyFragment(); //ChildFragment3 at position 2

        }

        return new CurrentFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
