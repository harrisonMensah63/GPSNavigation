package com.harryWorld.weatherGPS.map.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.harryWorld.weatherGPS.MapsActivity;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.recyclerVeiw.YourPlaceAdapter;
import com.harryWorld.weatherGPS.map.utils.Places;
import com.harryWorld.weatherGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.weatherGPS.weather.constant.Decoration;
import com.harryWorld.weatherGPS.weather.utils.Resource;


import static com.harryWorld.weatherGPS.map.constant.Constant.PLACES_LIST;
import static com.harryWorld.weatherGPS.map.constant.Constant.PLACES_LIST_EDIT;

public class YourPlaceActivity extends AppCompatActivity implements YourPlaceAdapter.MyViewHolder.WeatherListener {
    private static final String TAG = "YourPlaceActivity";

    private RecyclerView recyclerView;
    private PlacesViewModel viewModel;
    private YourPlaceAdapter adapter;
    private int placeSize;

    private ImageView imageAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_place);

        recyclerView = findViewById(R.id.placeid);
        viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);

        imageAdd = findViewById(R.id.image_add);
        setRecyclerView();
        setPlaces();

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_dark));
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                R.string.swipe, Snackbar.LENGTH_LONG);
        snackbar.show();

        imageAdd.setOnClickListener(v -> {
            Intent intent = new Intent(YourPlaceActivity.this, AddingFavouriteActivity.class);
            intent.putExtra(PLACES_LIST_EDIT,getPlaceSize()+1);
            startActivity(intent);
        });
    }
    private void setPlaces(){
        viewModel.getPlaces.observe(this, places -> {
            if (places != null){
                if (places.size() > 0 ) {
                    setPlaceSize(places.size());
                    Log.d(TAG, "onChanged: places size was not null "+places.get(1).getCityName());
                    Log.d(TAG, "onChanged: places size was not null "+places.get(1).getLongitude());
                    Log.d(TAG, "onChanged: places size was not null "+places.get(0).getCityName());
                    //insertingDefault(places);
                }
                else {
                }
                adapter.setPlacesList(places);
            }
        });
    }


    private void insertingDefault(){
            Log.d(TAG, "insertingDefault: list was null");
            Places places = new Places();
            Places places1 = new Places();

            places.setTitle(getString(R.string.home));
            places1.setTitle(getString(R.string.work));

            places.setContent(getString(R.string.home_address));
            places1.setContent(getString(R.string.home_address));

                Log.d(TAG, "insertingDefault: this was okay");
//                insertingPlace(places);
//                insertingPlace(places1);

    }
//    private void insertingPlace(Places places){
//            viewModel.gettingInsert(places).observe(this, new Observer<Resource<Long>>() {
//                @Override
//                public void onChanged(Resource<Long> longResource) {
//                    if (longResource != null) {
//                        if (longResource.status == Resource.Status.SUCCESS) {
//                            Log.d(TAG, "onChanged: places was inserted successfully" + longResource.data);
//                            isDatabaseEmpty = false;
//                        } else {
//                            isDatabaseEmpty = true;
//                            Log.d(TAG, "onChanged: there was an error inserting places " + longResource.message);
//                        }
//                    }
//                }
//            });
//    }
    private void setRecyclerView(){
        adapter = new YourPlaceAdapter(this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Decoration(10));
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            deleteList(adapter.getPlacesList().get(viewHolder.getAdapterPosition()));
        }
    }; 
    private void deleteList(Places places){
        if (places.getId() != 1 && places.getId() != 2) {
            adapter.getPlacesList().remove(places);
            adapter.notifyItemRemoved(places.getId()-1);
            deletingPlaces(places);
        }
        else{
            adapter.notifyDataSetChanged();
            Toast.makeText(this,
                    places.getTitle()+" "+getString(R.string.only_update),
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void deletingPlaces(Places places){
        viewModel.deletingPlaces(places).observe(this, integerResource -> {
            if (integerResource != null){
                if (integerResource.status == Resource.Status.LOADING){
                    Log.d(TAG, "onChanged: places was deleted successfully");
                }
                else if (integerResource.status == Resource.Status.ERROR){
                    Log.d(TAG, "onChanged: there was an error deleting places "+integerResource.message);
                }
            }
        });
    }
    @Override
    public void getPosition(int position) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(PLACES_LIST, adapter.getPosition(position));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public int getPlaceSize() {
        return placeSize;
    }

    public void setPlaceSize(int placeSize) {
        this.placeSize = placeSize;
    }
}