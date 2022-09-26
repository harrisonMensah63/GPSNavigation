package com.harryWorld.weatherGPS.map.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.harryWorld.weatherGPS.MapsActivity;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.recyclerVeiw.ListAdapter;
import com.harryWorld.weatherGPS.map.recyclerVeiw.SearchAdapter;
import com.harryWorld.weatherGPS.map.retrofit.GO;
import com.harryWorld.weatherGPS.map.retrofit.Geocode;
import com.harryWorld.weatherGPS.map.utils.Places;
import com.harryWorld.weatherGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.weatherGPS.map.viewModels.VolleyViewModel;
import com.harryWorld.weatherGPS.schedule.Schedule;
import com.harryWorld.weatherGPS.weather.constant.Decoration;
import com.harryWorld.weatherGPS.weather.repository.WeatherRepository;
import com.harryWorld.weatherGPS.weather.utils.Resource;
import com.harryWorld.weatherGPS.weather.viewModels.MainViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.harryWorld.weatherGPS.map.constant.Constant.CHECK_RESULTS;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.FROM_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.FROM_SEARCH_FIRST;
import static com.harryWorld.weatherGPS.map.constant.Constant.FROM_SEARCH_SECOND;
import static com.harryWorld.weatherGPS.map.constant.Constant.LATITUDE_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.Constant.LONGITUDE_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.Constant.MAIN_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.MAIN_SEARCH_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.MAIN_SEARCH_NAVIGATION;
import static com.harryWorld.weatherGPS.map.constant.Constant.MAP_NAVIGATION_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.NEW_GUY_LATITUDE;

public class SearchActivity extends AppCompatActivity implements
        SearchAdapter.SearchViewHolder.SearchListener, ListAdapter.ListViewHolder.ListListener {
    private static final String TAG = "SearchActivity";
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private PlacesViewModel viewModel;
    TextView loadingText;
    LinearLayout loadingLayout;
    ProgressBar loadingBar;
    ListAdapter listAdapter;
    ImageView loadingImage;
    private RelativeLayout chooseFromMap;
    private RecyclerView searchRecycler;
    private VolleyViewModel volleyViewModel;
    private ActivityResultLauncher<Intent> startForResult;

    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        volleyViewModel = new ViewModelProvider(this).get(VolleyViewModel.class);
        SearchView searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.search_recycler_view);
        viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
        loadingBar = findViewById(R.id.search_progress_bar);
        loadingLayout = findViewById(R.id.layout_loading);
        loadingText = findViewById(R.id.loading);
        loadingImage = findViewById(R.id.network_error);
        chooseFromMap = findViewById(R.id.choose_map);
        searchRecycler = findViewById(R.id.search_recycler);

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_light));
        if (getIntent().hasExtra(EMPTY_SEARCH) || getIntent().hasExtra(FROM_SEARCH)){
            recyclerView.setVisibility(View.GONE);
            chooseFromMap.setVisibility(View.GONE);
        }
        if (getIntent().hasExtra(FROM_SEARCH_FIRST) || getIntent().hasExtra(FROM_SEARCH_SECOND)){
            chooseFromMap.setVisibility(View.GONE);
        }
        String origin = "41.43206,-81.38992";
        String destination = "40.43206,-82.38992";
//        mainViewModel.getDirection(origin, destination).observe(this, new Observer<Resource<List<Direction>>>() {
//            @Override
//            public void onChanged(Resource<List<Direction>> listResource) {
//                if (listResource != null){
//                    if (listResource.status == Resource.Status.SUCCESS){
//                        Log.d(TAG, "onChanged: there was success "+listResource.data.get(0).getRoutes().get(0).getLegs().get(0).getSteps().get(0).getPolyline().getPoints());
//                    }
//                    else if (listResource.status == Resource.Status.ERROR){
//                        Log.d(TAG, "onChanged: there was an error "+listResource.data.get(0).getRoutes().get(0).getLegs().get(0).getSteps().get(0).getPolyline().getPoints());
//                    }
//                }
//            }
//        });



        setRecyclerView();
       // searchView.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    loadingText.setText(R.string.loading);
                    loadingLayout.setVisibility(View.VISIBLE);
                    loadingImage.setVisibility(View.GONE);
                    loadingBar.setVisibility(View.VISIBLE);
                    searchRecycler.setVisibility(View.VISIBLE);
                    //setGeocode(newText);
                    WeatherRepository repository = new WeatherRepository();
                    WeatherRepository.getWeatherRepository().getGeocoder(newText).enqueue(new Callback<GO>() {
                        @Override
                        public void onResponse(Call<GO> call, Response<GO> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null){
                                    if (response.body().getGeocode() != null) {
                                        setListRecyclerView();
                                        loadingLayout.setVisibility(View.GONE);
                                        loadingImage.setVisibility(View.VISIBLE);
                                        listAdapter.setPrediction(response.body().getGeocode());
                                        listAdapter.notifyDataSetChanged();
                                        searchRecycler.setVisibility(View.VISIBLE);
                                    }
                                }
                                else{
                                    Log.d(TAG, "onResponse: body was null ");
                                }
                            }
                            else{
                                searchRecycler.setVisibility(View.GONE);
                                loadingText.setText(R.string.offline);
                                loadingBar.setVisibility(View.GONE);
                                loadingImage.setVisibility(View.VISIBLE);
                                Log.d(TAG, "onResponse: there was error "+response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<GO> call, Throwable t) {

                            Log.d(TAG, "onFailure: lets see the failure "+t.getMessage());
                            searchRecycler.setVisibility(View.GONE);
                            loadingText.setText(R.string.offline);
                            loadingBar.setVisibility(View.GONE);
                            loadingImage.setVisibility(View.VISIBLE);
                        }
                    });
                }
                else{
                    loadingLayout.setVisibility(View.GONE);
                }
                if (newText.trim().length() == 0){
                    searchRecycler.setVisibility(View.GONE);
                }
                return false;
            }
        });
        setPlaces();
        setChooseFromMap();
        gettingResults();
    }
    private void gettingResults(){
        startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null && result.getData().hasExtra(LATITUDE_CATE_SUB)) {
                    double lat = result.getData().getDoubleExtra(LATITUDE_CATE_SUB,0.0);
                    double lon = result.getData().getDoubleExtra(LONGITUDE_CATE_SUB,0.0);
                    List<LatLng> list = new ArrayList<>();
                    LatLng latLng = new LatLng(lat,lon);
                    list.add(latLng);

                    Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
                    intent.putExtra(NEW_GUY_LATITUDE, (Serializable) list);
                    startActivity(intent);

                }
            }
        });
    }

    private void setChooseFromMap(){
            chooseFromMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
                    intent.putExtra(MAP_NAVIGATION_SEARCH, "");
                    startForResult.launch(intent);
                    //finish();
                }
            });
    }
    private void setListRecyclerView(){
        listAdapter = new ListAdapter(this, this);
        searchRecycler.setLayoutManager(new LinearLayoutManager(this));
        searchRecycler.setAdapter(listAdapter);
        Log.d(TAG, "setListRecyclerView: this is working");
    }
    private void setRecyclerView(){
        adapter = new SearchAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Decoration(10));
        recyclerView.setAdapter(adapter);
    }

    private void setPlaces(){
        viewModel.getPlaces.observe(this, new Observer<List<com.harryWorld.weatherGPS.map.utils.Places>>() {
            @Override
            public void onChanged(List<com.harryWorld.weatherGPS.map.utils.Places> places) {
                if (places != null){
                    if (places.size() > 0 ) {
                        Log.d(TAG, "onChanged: places size was not null "+places.get(1).getCityName());
                        Log.d(TAG, "onChanged: places size was not null "+places.get(1).getLongitude());
                        Log.d(TAG, "onChanged: places size was not null "+places.get(0).getCityName());
                        //insertingDefault(places);
                    }
                    else {
                        Log.d(TAG, "insertingDefault: list was null ");
                    }
                    adapter.setPlacesList(places);
                }
            }
        });
    }

    @Override
    public void getPosition(int position) {
        if (getIntent().hasExtra(MAIN_SEARCH)){
            double latitude = adapter.getPlacesList().get(position).getLatitude();
            double longitude = adapter.getPlacesList().get(position).getLongitude();
            if (longitude != 0.0 && latitude != 0.0){
                if ( position == 1  || position ==0) {
                    Intent intent = new Intent(this, MapsActivity.class);
                    intent.putExtra(MAIN_SEARCH_LATITUDE, adapter.getPlacesList().get(position));
                    startActivity(intent);
                    //finish();
                }
                else{
                    Intent intent = new Intent(this, MapsActivity.class);
                    intent.putExtra(MAIN_SEARCH_LATITUDE,adapter.getPlacesList().get(position));
                    startActivity(intent);
                }
            }
            else{
                Intent intent = new Intent(this, AddingFavouriteActivity.class);
                intent.putExtra(EMPTY_SEARCH,adapter.getPlacesList().get(position));
                startActivity(intent);
            }
        }
        else if (getIntent().hasExtra(FROM_SEARCH_FIRST)) {
            Schedule schedule = getIntent().getParcelableExtra(FROM_SEARCH_FIRST);
            Places places = adapter.getPlacesList().get(position);
            if (places.getLatitude() != 0.0 && places.getLongitude() != 0.0) {
                if (schedule != null) {
                    schedule.setFromDestination(places.getContent());
                    schedule.setFromLongitudeValue(places.getLongitude());
                    schedule.setFromLatitudeValue(places.getLatitude());

                    Intent intent = new Intent(SearchActivity.this, PlanSettingActivity.class);
                    intent.putExtra(FROM_SEARCH_FIRST, schedule);
                    startActivity(intent);
                    finish();
                }
            }
            else if (position != 0 && position != 1){
                if (schedule != null) {
                    schedule.setFromDestination(places.getContent());
                    schedule.setFromLongitudeValue(places.getLongitude());
                    schedule.setFromLatitudeValue(places.getLatitude());

                    Intent intent = new Intent(SearchActivity.this, PlanSettingActivity.class);
                    intent.putExtra(FROM_SEARCH_FIRST, schedule);
                    startActivity(intent);
                    finish();
                }
            }
        }
        else if (getIntent().hasExtra(FROM_SEARCH_SECOND)){
            Schedule schedule = getIntent().getParcelableExtra(FROM_SEARCH_SECOND);
            Places places = adapter.getPlacesList().get(position);
            if (places.getLatitude() != 0.0 && places.getLongitude() != 0.0) {
                if (schedule != null) {
                    schedule.setFinalDestination(places.getContent());
                    schedule.setFinalLongitudeValue(places.getLongitude());
                    schedule.setFinalLatitudeValue(places.getLatitude());

                    Intent intent = new Intent(SearchActivity.this, PlanSettingActivity.class);
                    intent.putExtra(FROM_SEARCH_SECOND, schedule);
                    startActivity(intent);
                    finish();
                }
            }
            else if (position != 0 && position != 1) {
                if (schedule != null) {
                    schedule.setFinalDestination(places.getContent());
                    schedule.setFinalLongitudeValue(places.getLongitude());
                    schedule.setFinalLatitudeValue(places.getLatitude());

                    Intent intent = new Intent(SearchActivity.this, PlanSettingActivity.class);
                    intent.putExtra(FROM_SEARCH_SECOND, schedule);
                    startActivity(intent);
                    finish();
                }
            }

        }
    }

    @Override
    public void getListPosition(Geocode prediction) {
       searchMighty(prediction);
    }
    private void setGeocode(String address){
        mainViewModel.getGeocode(address).observe(this, new Observer<Resource<GO>>() {
            @Override
            public void onChanged(Resource<GO> geocodeResource) {
                if (geocodeResource != null){
                    if (geocodeResource.status == Resource.Status.SUCCESS){

                        setListRecyclerView();
                        loadingLayout.setVisibility(View.GONE);
                        loadingImage.setVisibility(View.VISIBLE);
                        if (geocodeResource.data != null) {
                            listAdapter.setPrediction(geocodeResource.data.getGeocode());
                        }
                        listAdapter.notifyDataSetChanged();
                        searchRecycler.setVisibility(View.VISIBLE);

                    }
                    else{
                        searchRecycler.setVisibility(View.GONE);
                        loadingText.setText(R.string.offline);
                        loadingBar.setVisibility(View.GONE);
                        loadingImage.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onChanged: there was an error getting geocode "+geocodeResource.message);
                       // Log.d(TAG, "onChanged: there was an error getting geocode "+geocodeResource.data.get(0).getAddress_components().get(0).getLong_name());
                    }
                }
            }
        });
    }
//    private void setReverseGeocode() {
//        WeatherRepository.getWeatherRepository().getReverseGeocoder("6.700071,-1.630783").enqueue(new Callback<GO>() {
//            @Override
//            public void onResponse(Call<GO> call, Response<GO> response) {
//                Log.d(TAG, "onResponse: lets see response "+response.body().getReverseGeocode().get(0).getAddress().getCounty());
//            }
//
//            @Override
//            public void onFailure(Call<GO> call, Throwable t) {
//                Log.d(TAG, "onFailure: lets check error "+t.getMessage());
//            }
//        });
//    }
        private void searchMighty(Geocode geocode){

        String county = geocode.getAddress().getCounty();
        double latitude = geocode.getPosition().getLatitude();
        double longitude = geocode.getPosition().getLongitude();
        String label = geocode.getAddress().getLabel()+","+geocode.getAddress().getCountry();
        Schedule schedule;
        com.harryWorld.weatherGPS.map.utils.Places places = new com.harryWorld.weatherGPS.map.utils.Places();
        places.setLatitude(geocode.getPosition().getLatitude());
        places.setLongitude(geocode.getPosition().getLongitude());
        places.setCityName(geocode.getAddress().getCounty());
        places.setContent(geocode.getAddress().getLabel());
        if (getIntent().hasExtra(MAIN_SEARCH)){
            volleyViewModel.connector.loading.setValue(true);
            Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
            intent.putExtra(CHECK_RESULTS,places);
            startActivity(intent);
        }
        else if (getIntent().hasExtra(MAIN_SEARCH_NAVIGATION)) {
            Intent intent = new Intent(this,MapsActivity.class);
            intent.putExtra(MAIN_SEARCH_NAVIGATION,places);
            startActivity(intent);
          //  finish();
        }
        else if (getIntent().hasExtra(FROM_SEARCH)) {
            com.harryWorld.weatherGPS.map.utils.Places place = getIntent().getParcelableExtra(FROM_SEARCH);
            place.setLatitude(latitude);
            place.setLongitude(longitude);
            place.setCityName(county);
            place.setContent(label);
            Intent intent = new Intent(this,AddingFavouriteActivity.class);
            intent.putExtra(FROM_SEARCH, place);
            startActivity(intent);
            finish();
        }
        if (getIntent().hasExtra(EMPTY_SEARCH)){
            com.harryWorld.weatherGPS.map.utils.Places place = getIntent().getParcelableExtra(EMPTY_SEARCH);
            place.setLatitude(latitude);
            place.setLongitude(longitude);
            place.setCityName(county);
            place.setContent(label);
            Intent intent = new Intent(this,AddingFavouriteActivity.class);
            intent.putExtra(EMPTY_SEARCH, place);
            startActivity(intent);
            finish();        }
        else if (getIntent().hasExtra(FROM_SEARCH_FIRST)) {
            schedule = getIntent().getParcelableExtra(FROM_SEARCH_FIRST);
            if (schedule == null){
                schedule = new Schedule();
            }
            schedule.setFromLongitudeValue(longitude);
            schedule.setFromLatitudeValue(latitude);
            schedule.setFromDestination(label);
            Intent intent = new Intent(this,PlanSettingActivity.class);
            intent.putExtra(FROM_SEARCH_FIRST, schedule);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(FROM_SEARCH_SECOND)) {
            schedule = getIntent().getParcelableExtra(FROM_SEARCH_SECOND);
            if (schedule == null){
                schedule = new Schedule();
            }
            schedule.setFinalLongitudeValue(longitude);
            schedule.setFinalLatitudeValue(latitude);
            schedule.setFinalDestination(label);
            Intent intent = new Intent(this,PlanSettingActivity.class);
            intent.putExtra(FROM_SEARCH_SECOND, schedule);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra(FROM_SEARCH_FIRST)){
            Schedule schedule = getIntent().getParcelableExtra(FROM_SEARCH_FIRST);
            Intent intent = new Intent(SearchActivity.this,PlanSettingActivity.class);
            intent.putExtra(FROM_SEARCH_FIRST,schedule);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(FROM_SEARCH_SECOND)){
            Schedule schedule = getIntent().getParcelableExtra(FROM_SEARCH_SECOND);
            Intent intent = new Intent(SearchActivity.this,PlanSettingActivity.class);
            intent.putExtra(FROM_SEARCH_SECOND,schedule);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(FROM_SEARCH)){
            Places places = getIntent().getParcelableExtra(FROM_SEARCH);
            Intent intent = new Intent(SearchActivity.this,AddingFavouriteActivity.class);
            intent.putExtra(FROM_SEARCH_SECOND,places);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(EMPTY_SEARCH)){
            Places places = getIntent().getParcelableExtra(EMPTY_SEARCH);
            Intent intent = new Intent(SearchActivity.this,AddingFavouriteActivity.class);
            intent.putExtra(EMPTY_SEARCH,places);
            startActivity(intent);
            finish();
        }

        super.onBackPressed();
    }
}