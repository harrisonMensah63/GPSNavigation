package com.harryWorld.navigationGPS.map.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.map.recyclerVeiw.inside.InsideAdapter;
import com.harryWorld.navigationGPS.map.recyclerVeiw.inside.InsideAdapter2;
import com.harryWorld.navigationGPS.map.recyclerVeiw.inside.InsideAdapter3;
import com.harryWorld.navigationGPS.map.recyclerVeiw.inside.InsideAdapter4;
import com.harryWorld.navigationGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.navigationGPS.weather.constant.Decoration;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements InsideAdapter.InsideVIewHolder.InsideListener,
        InsideAdapter2.InsideVIewHolder2.InsideListener2, InsideAdapter3.InsideVIewHolder3.InsideListener3,
        InsideAdapter4.InsideVIewHolder4.InsideListener4 {
    private RecyclerView recyclerInside1;
    private RecyclerView recyclerInside2;
    private RecyclerView recyclerInside3;
    private RecyclerView recyclerInside4;
    public PlacesViewModel viewModel;
    InsideAdapter insideAdapter;
    InsideAdapter2 insideAdapter2;
    InsideAdapter3 insideAdapter3;
    InsideAdapter4 insideAdapter4;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_layout);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.apply();
        recyclerInside1 = findViewById(R.id.recycler_inside1);
        recyclerInside2 = findViewById(R.id.recycler_inside2);
        recyclerInside3 = findViewById(R.id.recycler_inside3);
        recyclerInside4 = findViewById(R.id.recycler_inside4);
        setRecyclerInside1();
        setRecyclerInside2();
        setRecyclerInside3();
        setRecyclerInside4();
        viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);

       // addHeader();
        addCategories1();
        addCategories2();
        addCategories3();
        addCategories4();

       // retrieveCategories();
    }
//    private void addHeader(){
//        List<String> emergency = new ArrayList<>();
//        emergency.add("Emergency");
//        emergency.add("Eateries");
//        emergency.add("Entertainment");
//        emergency.add("Services");
//        adapter.setCategoriesList(emergency);
//
//    }

    private void setRecyclerInside1(){
        insideAdapter = new InsideAdapter(this);
        recyclerInside1.setLayoutManager(new LinearLayoutManager(this));
        recyclerInside1.addItemDecoration(new Decoration(1));
        recyclerInside1.setAdapter(insideAdapter);

    }
    private void setRecyclerInside2(){
        insideAdapter2 = new InsideAdapter2(this);
        recyclerInside2.setLayoutManager(new LinearLayoutManager(this));
        recyclerInside2.addItemDecoration(new Decoration(1));
        recyclerInside2.setAdapter(insideAdapter2);

    }
    private void setRecyclerInside3(){
        insideAdapter3 = new InsideAdapter3(this);
        recyclerInside3.setLayoutManager(new LinearLayoutManager(this));
        recyclerInside3.addItemDecoration(new Decoration(1));
        recyclerInside3.setAdapter(insideAdapter3);

    }
    private void setRecyclerInside4(){
        insideAdapter4 = new InsideAdapter4(this);
        recyclerInside4.setLayoutManager(new LinearLayoutManager(this));
        recyclerInside4.addItemDecoration(new Decoration(1));
        recyclerInside4.setAdapter(insideAdapter4);

    }
    private void addCategories1(){
        List<String> emergency = new ArrayList<>();
        emergency.add(getString(R.string.police));
        emergency.add(getString(R.string.hospital));
        emergency.add(getString(R.string.pharmacy));
        emergency.add(getString(R.string.fire_station));
        emergency.add(getString(R.string.gas_station));
        emergency.add(getString(R.string.car_repair));
        insideAdapter.setCategories(emergency);
    }

    private void addCategories2(){
        List<String> emergency = new ArrayList<>();
        emergency.add(getString(R.string.restaurant));
        emergency.add(getString(R.string.coffee));
        emergency.add(getString(R.string.bars));
        emergency.add(getString(R.string.cafeteria));
        emergency.add(getString(R.string.teashop));

        insideAdapter2.setCategoriesList2(emergency);
    }

    private void addCategories3(){
        List<String> emergency = new ArrayList<>();
        emergency.add(getString(R.string.movies));
        emergency.add(getString(R.string.nightlife));
        emergency.add(getString(R.string.park));
        emergency.add(getString(R.string.libraries));
        emergency.add(getString(R.string.museums));

        insideAdapter3.getCategoriesList3(emergency);
    }

    private void addCategories4(){
        List<String> emergency = new ArrayList<>();
        emergency.add(getString(R.string.supermarket));
        emergency.add(getString(R.string.atms));
        emergency.add(getString(R.string.car_wash));
        emergency.add(getString(R.string.parking));
        emergency.add(getString(R.string.laundry));
        emergency.add(getString(R.string.electric_vehicle_charging));

        insideAdapter4.getCategoriesList4(emergency);
    }


    @Override
    public void getInside(int position) {
        switch (position){
            case 0:{
                editor.putString("police","police");
                editor.apply();
            }
            break;
            case 1:{
                editor.putString("police","hospital");
                editor.apply();
            }
            break;
            case 2:{
                editor.putString("police","pharmacy");
                editor.apply();
            }
            break;
            case 3:{
                editor.putString("police","fire_station");
                editor.apply();
            }
            break;
            case 4:{
                editor.putString("police","gas_station");
                editor.apply();
            }
            break;
        }
        CategoriesActivity.this.finish();
    }

    @Override
    public void getInside2(int position) {
        switch (position){
            case 0:{
                editor.putString("police","restaurant");
                editor.apply();
            }
            break;
            case 1:
            case 4:
            case 3: {
                editor.putString("police","cafe");
                editor.apply();
            }
            break;
            case 2:{
                editor.putString("police","bar");
                editor.apply();
            }
            break;
        }
        CategoriesActivity.this.finish();
    }

    @Override
    public void getInside3(int position) {
        switch (position){
            case 0:{
                editor.putString("police","movie_theater");
                editor.apply();
            }
            break;
            case 1:{
                editor.putString("police","night_club");
                editor.apply();
            }
            break;
            case 2:{
                editor.putString("police","park");
                editor.apply();
            }
            break;
            case 3:{
                editor.putString("police","library");
                editor.apply();
            }
            break;
            case 4:{
                editor.putString("police","museum");
                editor.apply();
            }
            break;
        }
        CategoriesActivity.this.finish();
    }

    @Override
    public void getInside4(int position) {
        switch (position){
            case 0:{
                editor.putString("police","supermarket");
                editor.apply();
            }
            break;
            case 1:{
                editor.putString("police","atm");
                editor.apply();
            }
            break;
            case 2:{
                editor.putString("police","car_wash");
                editor.apply();
            }
            break;
            case 3:{
                editor.putString("police","parking");
                editor.apply();
            }
            break;
            case 4:{
                editor.putString("police","laundry");
                editor.apply();
            }
            break;
            case 5:{
                editor.putString("police","electric_vehicle_charging");
                editor.apply();
            }
            break;
        }
        CategoriesActivity.this.finish();
    }
}