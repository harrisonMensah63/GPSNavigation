package com.harryWorld.weatherGPS.weather;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.weather.api.Climate;
import com.harryWorld.weatherGPS.weather.recyclerView.VIewPageAdapter;
import com.harryWorld.weatherGPS.weather.utils.Current;
import com.harryWorld.weatherGPS.weather.viewModels.CurrentViewModel;
import com.harryWorld.weatherGPS.weather.viewModels.DailyViewModel;
import com.harryWorld.weatherGPS.weather.viewModels.HourlyViewModel;
import com.harryWorld.weatherGPS.weather.viewModels.MainViewModel;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TabLayout tabLayout;
    LinearLayout appBarLayout;
    private CurrentViewModel currentViewModel;
    private DailyViewModel dailyViewModel;
    private HourlyViewModel hourlyViewModel;
    private MainViewModel mainViewModel;
    Current currentNow = new Current();
    Climate climate;
    private InterstitialAd mInterstitialAd;


    ViewPager2 viewPager;
    TextView toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        viewPager = findViewById(R.id.view_page);
        VIewPageAdapter vIewPageAdapter = new VIewPageAdapter(this);
        viewPager.setAdapter(vIewPageAdapter);

        toolbar = findViewById(R.id.toolBar);

         tabLayout = findViewById(R.id.tabs);
         appBarLayout = findViewById(R.id.app_bar);

         currentViewModel = new ViewModelProvider(this).get(CurrentViewModel.class);
         dailyViewModel = new ViewModelProvider(this).get(DailyViewModel.class);
         hourlyViewModel = new ViewModelProvider(this).get(HourlyViewModel.class);
         mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_dark));
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-2650278620916037/8075653760", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

        new TabLayoutMediator(tabLayout, viewPager, true, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(getString(R.string.current));
                    break;
                case 1:
                    tab.setText(getString(R.string.hourly));
                    break;
                case 2:
                    tab.setText(getString(R.string.tomorrow));
                    break;
            }
        }
        ).attach();

         climate = getIntent().getParcelableExtra("let there be light");
         if (climate != null) {
             toolbar.setText(climate.getLocation().getName());
         }
        mainViewModel.setRetrieveClimate(climate);
        if (currentViewModel.currentInsertMode.getValue() == null ||
                currentViewModel.currentInsertMode.getValue() != CurrentViewModel.CurrentInsertMode.ABORT) {
        }
        settingSharedPreference();

        new InterstitialAdLoadCallback(){
            @Override
            public void onAdLoaded(@NonNull @io.reactivex.annotations.NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        mInterstitialAd = null;
                        // Called when fullscreen content is dismissed.
                        Log.d("TAG", "The ad was dismissed.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        mInterstitialAd = null;
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });

            }
        };
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            super.onBackPressed();

        } else {

            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }


    }


    static RequestOptions requestOptions(){
        return RequestOptions
                .placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
    }

    public RequestManager requestManager(){
        return Glide.with(this)
                .setDefaultRequestOptions(requestOptions());
    }

    private void settingSharedPreference(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean getDay = preferences.getBoolean("isDay", false);
        if (climate.getCurrent().getIs_day() == 1){
            gettingLightMode();
        }
        else {
            gettingDarkMode();
        }
        toolbar.setText(climate.getLocation().getName());
    }
    private void gettingLightMode(){
        Drawable head = ContextCompat.getDrawable(this, R.color.head_light);
        Drawable tab = ContextCompat.getDrawable(this, R.color.tab_light);
        appBarLayout.setBackground(head);
        tabLayout.setBackground(tab);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.indicator_light));
    }
    private void gettingDarkMode(){
        Log.d(TAG, "gettingDarkMode: MainActivity dark mode activated");
        Drawable head = ContextCompat.getDrawable(this, R.color.head_dark);
        Drawable tab = ContextCompat.getDrawable(this, R.color.tab_dark);
        appBarLayout.setBackground(head);
        tabLayout.setBackground(tab);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.indicator_dark));
    }

//    private void gettingCurrentData(double latitude, double longitude){
//        mainViewModel.getCurrentCoordinates(latitude, longitude).observe(this, new Observer<Resource<List<Weather>>>() {
//            @Override
//            public void onChanged(Resource<List<Weather>> listResource) {
//                if (listResource.data != null) {
//                    String network ="";
//                    network = String.valueOf(R.string.networkConnection);
//                    switch (listResource.status){
//
//                        case ERROR:{
//                            currentViewModel.currentInsertMode.setValue(CurrentViewModel.CurrentInsertMode.ERROR);
//                            Log.d(TAG, "onChanged: there was an error getting current weather"+" "+listResource.message);
//                        }
//                        break;
//                        case SUCCESS:{
////                            editor.putString("cityName", listResource.data.get(0).getCityName());
////                            editor.commit();
//                            toolbar.setTitle(listResource.data.get(0).getCityName());
//                            insertCurrent(listResource.data.get(0));
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
//    private void gettingDailyData(double latitude, double longitude){
//        mainViewModel.getDailyCoordinates(latitude,longitude).observe(this, new Observer<Resource<List<Weather>>>() {
//            @Override
//            public void onChanged(Resource<List<Weather>> listResource) {
//                if (listResource.data != null) {
//                    switch (listResource.status){
//                        case ERROR:{
//                            dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.ERROR);
//                            Log.d(TAG, "onChanged: there was an error getting daily weather"+" "+listResource.message);
//                        }
//                        break;
//                        case SUCCESS:{
//                            Log.d(TAG, "onChanged: daily weather was retrieve "+listResource.data.get(0).getMin_temp());
//                            dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.AVAILABLE);
//                            retrievingDaily(listResource.data);
//                        }
//                        break;
//                    }
//                }
//            }
//        });
//    }
//    private void gettingHourlyData(double latitude, double longitude){
//        mainViewModel.getHourlyCoordinates(latitude,longitude).observe(this, new Observer<Resource<List<Weather>>>() {
//            @Override
//            public void onChanged(Resource<List<Weather>> listResource) {
//                if (listResource.data != null) {
//                    switch (listResource.status){
//                        case ERROR:{
//                            hourlyViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.ERROR);
//                            Log.d(TAG, "onChanged: there was an error getting hourly weather"+" "+listResource.message);
//                        }
//                        break;
//                        case SUCCESS:{
////                            editor.putBoolean("hourly", true);
////                            editor.commit();
//                            hourlyViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.AVAILABLE);
//                            retrievingHourly(listResource.data);
//                            Log.d(TAG, "onChanged: hourly weather was retrieve "+listResource.data.size());
//                        }
//                        break;
//                    }
//                }
//            }
//        });
//    }
//
//    private void insertCurrent(Weather weather){
//        currentNow.setId(1);
//        currentViewModel.insertCurrent(weather,currentNow).observe(this, new Observer<Resource<Integer>>() {
//            @Override
//            public void onChanged(Resource<Integer> integerResource) {
//                if (integerResource != null){
//                    if (integerResource.status == Resource.Status.ERROR){
//                        Log.d(TAG, "onChanged: there was an error");
//                    }
//                    else if (integerResource.status == Resource.Status.SUCCESS){
//                        Log.d(TAG, "onChanged: current inserted successffuly");
//                        currentViewModel.currentInsertMode.setValue(CurrentViewModel.CurrentInsertMode.AVAILABLE);
//                    }
//                }
//            }
//        });
//    }
//    private void insertHourly(List<Weather> weather){
//        hourlyViewModel.insertHourly(weather).observe(this, new Observer<Resource<List<Long>>>() {
//            @Override
//            public void onChanged(Resource<List<Long>> listResource) {
//                if (dailyViewModel.dailyInsertMode.getValue() != null &&
//                        dailyViewModel.dailyInsertMode.getValue() == DailyViewModel.DailyInsertMode.AVAILABLE) {
//                    dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.ABORT);
//                    if (listResource != null) {
//                        hourlyViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.ABORT);
//                        if (listResource.status == Resource.Status.ERROR) {
//                            Log.d(TAG, "onChanged: there was an error wiht hourly");
//                        } else if (listResource.status == Resource.Status.SUCCESS) {
//                            hourlyViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.AVAILABLE);
//                            Log.d(TAG, "onChanged: hourly inserted successfuly");
//                        }
//                    }
//                }
//            }
//        });
//    }
//    private void insertDaily(List<Weather> weather, List<Daily> dailyList){
//        if (dailyList == null || dailyList.size() == 0) {
//            dailyViewModel.insertDaily(weather).observe(this, new Observer<Resource<List<Long>>>() {
//                @Override
//                public void onChanged(Resource<List<Long>> listResource) {
//                    if (dailyViewModel.dailyInsertMode.getValue() != null &&
//                            dailyViewModel.dailyInsertMode.getValue() == DailyViewModel.DailyInsertMode.AVAILABLE) {
//                            dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.ABORT);
//                        if (listResource != null) {
//                            if (listResource.status == Resource.Status.ERROR) {
//                                Log.d(TAG, "onChanged: there was an error with daily");
//                            } else if (listResource.status == Resource.Status.SUCCESS) {
//                                dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.AVAILABLE);
//                                Log.d(TAG, "onChanged: daily inserted successfuly");
//                            }
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//    private void retrievingHourly(List<Weather> weathers){
//        hourlyViewModel.getRetrieveHourly().observe(this, new Observer<List<Hourly>>() {
//            @Override
//            public void onChanged(List<Hourly> hourlies) {
//                if (hourlyViewModel.hourlyInsertMode.getValue() != null &&
//                        hourlyViewModel.hourlyInsertMode.getValue() == HourlyViewModel.HourlyInsertMode.AVAILABLE) {
//                    hourlyViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.ABORT);
//                    if (hourlies != null && hourlies.size() > 0) {
//                        Log.d(TAG, "onChanged: no insertion for hourly");
//                    } else {
//                        insertHourly(weathers);
//                    }
//                }
//            }
//        });
//    }
//    private void retrievingDaily(List<Weather> weather){
//            dailyViewModel.getRetrieveDaily().observe(this, new Observer<List<Daily>>() {
//                @Override
//                public void onChanged(List<Daily> hourlies) {
//                    if (dailyViewModel.dailyInsertMode.getValue() != null &&
//                            dailyViewModel.dailyInsertMode.getValue() == DailyViewModel.DailyInsertMode.AVAILABLE) {
//                        dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.ABORT);
//                    if (hourlies != null && hourlies.size() > 0) {
//                        Log.d(TAG, "onChanged: stop");
//                    } else {
//                        insertDaily(weather, hourlies);
//                    }
//                }
//                }
//            });
//        }

    @Override
    protected void onPostResume() {
        currentViewModel.currentInsertMode.setValue(CurrentViewModel.CurrentInsertMode.ABORT);
        super.onPostResume();
    }

//    private void deleteDailyWhenExit(List<Daily> dailyList){
//        dailyViewModel.deleteDaily(dailyList).observe(this, new Observer<Resource<Integer>>() {
//            @Override
//            public void onChanged(Resource<Integer> integerResource) {
//                if (integerResource != null){
//                    if (integerResource.status == Resource.Status.SUCCESS){
//                        Log.d(TAG, "onChanged: deleted daily before exiting");
//                    }
//                }
//            }
//        });
//    }
//    private void retrievingToDeleteDaily(){
//        dailyViewModel.getRetrieveDaily().observe(this, new Observer<List<Daily>>() {
//            @Override
//            public void onChanged(List<Daily> hourlies) {
//                    if (hourlies != null && hourlies.size() > 0) {
//                        deleteDailyWhenExit(hourlies);
//                }
//            }
//        });
//    }
//
//    private void deleteHourlyWhenExit(List<Hourly> hourlyList){
//        hourlyViewModel.deleteHourly(hourlyList).observe(this, new Observer<Resource<Integer>>() {
//            @Override
//            public void onChanged(Resource<Integer> integerResource) {
//                if (integerResource != null){
//                    if (integerResource.status == Resource.Status.SUCCESS){
//                        Log.d(TAG, "onChanged: deleted hourly before exiting");
//                    }
//                }
//            }
//        });
//    }
//    private void retrievingToDeleteHourly(){
//        hourlyViewModel.getRetrieveHourly().observe(this, new Observer<List<Hourly>>() {
//            @Override
//            public void onChanged(List<Hourly> hourlies) {
//                if (hourlies != null && hourlies.size() > 0) {
//                    deleteHourlyWhenExit(hourlies);
//                }
//            }
//        });
//    }
}
