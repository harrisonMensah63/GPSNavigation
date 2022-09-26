package com.harryWorld.weatherGPS.map.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.harryWorld.weatherGPS.MapsActivity;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.recyclerVeiw.SearchAdapter;
import com.harryWorld.weatherGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.weatherGPS.weather.constant.Decoration;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Locale;

import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_NAVIGATION;
import static com.harryWorld.weatherGPS.map.constant.Constant.MAP_NAVIGATION_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.NAVIGATION_SEARCH;

public class NavigationActivity extends AppCompatActivity implements
        SearchAdapter.SearchViewHolder.SearchListener {
    private static final String TAG = "SearchActivity";
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private PlacesViewModel viewModel;
    private SearchView searchView;
    private RelativeLayout chooseFromMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

         searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.search_recycler_view);
        viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
        chooseFromMap = findViewById(R.id.choose_map);

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_dark));
        setRecyclerView();
        setPlaces();
        searchAction();
    }
    private void searchAction(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
                // and once again when the user makes a selection (for example when calling fetchPlace()).
                AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

                // Create a RectangularBounds object.
                RectangularBounds bounds = RectangularBounds.newInstance(
                        new LatLng(-33.880490, 151.184363),
                        new LatLng(-33.858754, 151.229596));
                // Use the builder to create a FindAutocompletePredictionsRequest.
                FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                        // Call either setLocationBias() OR setLocationRestriction().
                        .setLocationBias(bounds)
                        //.setLocationRestriction(bounds)
                        .setOrigin(new LatLng(-33.8749937,151.2041382))
                        .setCountries("AU", "NZ")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(newText)
                        .build();

                if (!com.google.android.libraries.places.api.Places.isInitialized()) {
                    com.google.android.libraries.places.api.Places.initialize(getApplicationContext(),
                            getString(R.string.google_maps_key), Locale.getDefault());
                }
                PlacesClient placesClient = Places.createClient(NavigationActivity.this);
                placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
                    for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                        Log.i(TAG, prediction.getPlaceId());
                        Log.i(TAG, prediction.getPrimaryText(null).toString());
                    }
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                    }
                });
                return false;
            }
        });
        chooseFromMap.setOnClickListener(v -> {
            Intent intent = new Intent(NavigationActivity.this,MapsActivity.class);
            intent.putExtra(MAP_NAVIGATION_SEARCH,"");
            startActivity(intent);
            finish();
        });
    }
    private void setRecyclerView(){
        adapter = new SearchAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Decoration(10));
        recyclerView.setAdapter(adapter);
    }

    private void setPlaces(){
        viewModel.getPlaces.observe(this, places -> {
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
        });
    }

    @Override
    public void getPosition(int position) {
        double latitude = adapter.getPlacesList().get(position).getLatitude();
        double longitude = adapter.getPlacesList().get(position).getLongitude();
        if (longitude != 0.0 && latitude != 0.0){
            Intent intent = new Intent(this,MapsActivity.class);
            intent.putExtra(NAVIGATION_SEARCH, adapter.getPlacesList().get(position));
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, AddingFavouriteActivity.class);
            intent.putExtra(EMPTY_NAVIGATION,adapter.getPlacesList().get(position));
            startActivity(intent);
        }
    }
}