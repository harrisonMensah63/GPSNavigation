package com.harryWorld.navigationGPS.map.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.harryWorld.navigationGPS.MapsActivity;
import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.map.recyclerVeiw.YourPlaceAdapter;
import com.harryWorld.navigationGPS.map.utils.Places;
import com.harryWorld.navigationGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.navigationGPS.weather.constant.Decoration;
import com.harryWorld.navigationGPS.weather.utils.Resource;

import java.util.List;

import static com.harryWorld.navigationGPS.map.constant.Constant.PLACES_LIST;
import static com.harryWorld.navigationGPS.map.constant.Constant.PLACES_LIST_EDIT;

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



        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YourPlaceActivity.this, AddingFavouriteActivity.class);
                intent.putExtra(PLACES_LIST_EDIT,getPlaceSize()+1);
                startActivity(intent);
            }
        });
    }
    private void setPlaces(){
        viewModel.getPlaces.observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
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
            adapter.notifyDataSetChanged();
            deletingPlaces(places);
        }
        else{
            adapter.notifyDataSetChanged();
            Toast.makeText(this,
                    places.getTitle()+getString(R.string.only_update),
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void deletingPlaces(Places places){
        viewModel.deletingPlaces(places).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource != null){
                    if (integerResource.status == Resource.Status.LOADING){
                        Log.d(TAG, "onChanged: places was deleted successfully");
                    }
                    else if (integerResource.status == Resource.Status.ERROR){
                        Log.d(TAG, "onChanged: there was an error deleting places "+integerResource.message);
                    }
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