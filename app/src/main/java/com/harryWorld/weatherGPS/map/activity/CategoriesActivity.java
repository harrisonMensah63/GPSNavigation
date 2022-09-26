package com.harryWorld.weatherGPS.map.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


import com.google.android.gms.maps.model.LatLng;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.activity.customCategories.CustomCateActivity;
import com.harryWorld.weatherGPS.map.recyclerVeiw.inside.InsideAdapter;
import com.harryWorld.weatherGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.weatherGPS.weather.constant.Decoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.harryWorld.weatherGPS.map.constant.Constant.CUSTOM_CATE;
import static com.harryWorld.weatherGPS.map.constant.Constant.CUSTOM_CATE_A;
import static com.harryWorld.weatherGPS.map.constant.Constant.CUSTOM_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.CUSTOM_LONGITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.LATITUDE_CATE_MAIN;
import static com.harryWorld.weatherGPS.map.constant.Constant.LATITUDE_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.Constant.LONGITUDE_CATE_MAIN;
import static com.harryWorld.weatherGPS.map.constant.Constant.LONGITUDE_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.Constant.STRING_CATE_SUB;

public class CategoriesActivity extends AppCompatActivity implements InsideAdapter.InsideVIewHolder.InsideListener {
    private static final String TAG = "CategoriesActivity";
    private RecyclerView recyclerInside1;
    public PlacesViewModel viewModel;
    InsideAdapter insideAdapter;
    double latitude, longitude;
    private ActivityResultLauncher<Intent> startForResult;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    List<List<String>> myStringList = new ArrayList<>();
    List<List<String>> sList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.apply();
        recyclerInside1 = findViewById(R.id.categories_recycler);
        setRecyclerInside();
        viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_light));

        addCategories1();
        addCategories2();
        addCategories3();
        addCategories4();

        checkingIntent();
        gettingResults();
    }
    private void gettingResults(){
        startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null && result.getData().hasExtra(LATITUDE_CATE_SUB)) {
                    double lan = result.getData().getDoubleExtra(LATITUDE_CATE_SUB,0.0);
                    double lon = result.getData().getDoubleExtra(LONGITUDE_CATE_SUB,0.0);
                    String name = result.getData().getStringExtra(STRING_CATE_SUB);
                    Intent output = new Intent();
                    output.putExtra(LATITUDE_CATE_SUB, lan);
                    output.putExtra(LONGITUDE_CATE_SUB, lon);
                    output.putExtra(STRING_CATE_SUB, name);
                    setResult(RESULT_OK, output);
                    finish();

                }
                else if (result.getData() != null && result.getData().hasExtra(LATITUDE_CATE_MAIN)) {
                    List<LatLng> list = (List<LatLng>) result.getData().getSerializableExtra(LATITUDE_CATE_MAIN);
                    Log.d(TAG, "lest see probelel "+list.size());
                    Intent output = new Intent();
                    output.putExtra(LATITUDE_CATE_MAIN, (Serializable) list);
                    setResult(RESULT_OK, output);
                    finish();
                }
            }
        });
    }
    private void checkingIntent(){
        if (getIntent().hasExtra(LATITUDE_CATE_MAIN)){
            latitude = getIntent().getDoubleExtra(LATITUDE_CATE_MAIN,0.0);
            longitude = getIntent().getDoubleExtra(LONGITUDE_CATE_MAIN,0.0);
            if (latitude != 0.0 && longitude != 0.0) {
                editor.putFloat(CUSTOM_LATITUDE, (float) latitude);
                editor.putFloat(CUSTOM_LONGITUDE, (float) longitude);
                editor.apply();
            }

        }
        else if (getIntent().hasExtra(LATITUDE_CATE_SUB)){
            latitude = getIntent().getDoubleExtra(LATITUDE_CATE_SUB,0.0);
            longitude = getIntent().getDoubleExtra(LONGITUDE_CATE_SUB,0.0);
            if (latitude != 0.0 && longitude != 0.0) {
                editor.putFloat(CUSTOM_LATITUDE, (float) latitude);
                editor.putFloat(CUSTOM_LONGITUDE, (float) longitude);
                editor.apply();
            }
        }
    }

    private void setRecyclerInside(){
        insideAdapter = new InsideAdapter(this,this);
        recyclerInside1.setLayoutManager(new LinearLayoutManager(this));
        recyclerInside1.addItemDecoration(new Decoration(1));
        recyclerInside1.setAdapter(insideAdapter);

    }

    private void addCategories1(){
        List<String> emergency = new ArrayList<>();
        List<String> emer = new ArrayList<>();
        emergency.add(getString(R.string.emergency));
        emergency.add(getString(R.string.police));
        emergency.add(getString(R.string.hospital));
        emergency.add(getString(R.string.pharmacy));
        emergency.add(getString(R.string.gas_station));
        emergency.add(getString(R.string.car_repair));
        myStringList.add(emergency);

        emer.add("emergency");
        emer.add("police");
        emer.add("hospital");
        emer.add("pharmacy");
        emer.add("gas station");
        emer.add("car repair");
        sList.add(emer);

    }

    private void addCategories2(){
        List<String> emergency = new ArrayList<>();
        List<String> emer = new ArrayList<>();
        emergency.add(getString(R.string.eateries));
        emergency.add(getString(R.string.restaurant));
        emergency.add(getString(R.string.coffee));
        emergency.add(getString(R.string.bars));
        emergency.add(getString(R.string.cafeteria));
        emergency.add(getString(R.string.teashop));
        myStringList.add(emergency);

        emer.add("eateries");
        emer.add("restaurant");
        emer.add("coffee");
        emer.add("bars");
        emer.add("cafeteria");
        emer.add("teashop");
        sList.add(emer);
    }

    private void addCategories3(){
        List<String> emergency = new ArrayList<>();
        List<String> emer = new ArrayList<>();
        emergency.add(getString(R.string.entertainment));
        emergency.add(getString(R.string.movies));
        emergency.add(getString(R.string.nightlife));
        emergency.add(getString(R.string.park));
        emergency.add(getString(R.string.libraries));
        emergency.add(getString(R.string.museums));
        myStringList.add(emergency);

        emer.add("entertainment");
        emer.add("movies");
        emer.add("nightlife");
        emer.add("park");
        emer.add("libraries");
        emer.add("museums");
        sList.add(emer);
    }

    private void addCategories4(){
        List<String> emergency = new ArrayList<>();
        List<String> emer = new ArrayList<>();
        emergency.add(getString(R.string.services));
        emergency.add(getString(R.string.supermarket));
        emergency.add(getString(R.string.atms));
        emergency.add(getString(R.string.car_wash));
        emergency.add(getString(R.string.parking));
        emergency.add(getString(R.string.electric_vehicle_charging));
        myStringList.add(emergency);

        emer.add("services");
        emer.add("supermarket");
        emer.add("atms");
        emer.add("car wash");
        emer.add("parking");
        emer.add("electric vehicle charging");
        sList.add(emer);
        insideAdapter.setCategories(myStringList);
    }

    @Override
    public void getText1(int position) {
        if (getIntent().hasExtra(LATITUDE_CATE_MAIN)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(1));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(1));
            intent.putExtra(LATITUDE_CATE_MAIN,"");
            startForResult.launch(intent);

        }
        else if (getIntent().hasExtra(LATITUDE_CATE_SUB)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(1));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(1));
            intent.putExtra(LATITUDE_CATE_SUB,"");
            startForResult.launch(intent);
        }
    }

    @Override
    public void getText2(int position) {
        if (getIntent().hasExtra(LATITUDE_CATE_MAIN)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(2));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(2));
            intent.putExtra(LATITUDE_CATE_MAIN,"");
            startForResult.launch(intent);
        }
        else if (getIntent().hasExtra(LATITUDE_CATE_SUB)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(2));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(2));
            intent.putExtra(LATITUDE_CATE_SUB,"");
            startForResult.launch(intent);
        }
    }

    @Override
    public void getText3(int position) {
        if (getIntent().hasExtra(LATITUDE_CATE_MAIN)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(3));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(3));
            intent.putExtra(LATITUDE_CATE_MAIN,"");
            startForResult.launch(intent);
        }
        else if (getIntent().hasExtra(LATITUDE_CATE_SUB)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(3));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(3));
            intent.putExtra(LATITUDE_CATE_SUB,"");
            startForResult.launch(intent);
        }

    }

    @Override
    public void getText4(int position) {
        if (getIntent().hasExtra(LATITUDE_CATE_MAIN)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(4));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(4));
            intent.putExtra(LATITUDE_CATE_MAIN,"");
            startForResult.launch(intent);
        }
        else if (getIntent().hasExtra(LATITUDE_CATE_SUB)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(4));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(4));
            intent.putExtra(LATITUDE_CATE_SUB,"");
            startForResult.launch(intent);
        }
    }

    @Override
    public void getText5(int position) {
        if (getIntent().hasExtra(LATITUDE_CATE_MAIN)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(5));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(5));
            intent.putExtra(LATITUDE_CATE_MAIN,"");
            startForResult.launch(intent);
        }
        else if (getIntent().hasExtra(LATITUDE_CATE_SUB)){
            Intent intent = new Intent(CategoriesActivity.this, CustomCateActivity.class);
            intent.putExtra(CUSTOM_CATE, sList.get(position).get(5));
            intent.putExtra(CUSTOM_CATE_A, myStringList.get(position).get(5));
            intent.putExtra(LATITUDE_CATE_SUB,"");
            startForResult.launch(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 419 && resultCode == RESULT_OK){

        }
    }

    //    @Override
//    public void getInside(int position) {
//        switch (position){
//            case 0:{
//                editor.putString("police","police");
//                editor.apply();
//            }
//            break;
//            case 1:{
//                editor.putString("police","hospital");
//                editor.apply();
//            }
//            break;
//            case 2:{
//                editor.putString("police","pharmacy");
//                editor.apply();
//            }
//            break;
//            case 3:{
//                editor.putString("police","fire_station");
//                editor.apply();
//            }
//            break;
//            case 4:{
//                editor.putString("police","gas_station");
//                editor.apply();
//            }
//            break;
//        }
//        CategoriesActivity.this.finish();
//    }


}