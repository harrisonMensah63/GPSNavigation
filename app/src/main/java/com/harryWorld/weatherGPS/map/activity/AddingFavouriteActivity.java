package com.harryWorld.weatherGPS.map.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.harryWorld.weatherGPS.MapsActivity;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.utils.Places;
import com.harryWorld.weatherGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.weatherGPS.weather.utils.Resource;
import com.harryWorld.weatherGPS.weather.utils.Weather;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.harryWorld.weatherGPS.map.constant.Constant.ADDING_FAVOURITE;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_NAVIGATION;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_NAVIGATION_MAP;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_SEARCH_MAP;
import static com.harryWorld.weatherGPS.map.constant.Constant.FORWARD_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.FROM_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.FROM_SEARCH_SECOND;
import static com.harryWorld.weatherGPS.map.constant.Constant.PLACES_LIST_EDIT;


public class AddingFavouriteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddingFavouriteActivity";
    public static final String CHOOSE="choose";

    private TextView enteringAddress, finger;
    private TextView saving;
    private AdView adView;
    private EditText titleEdit;
    private TextView titleText;
    private PlacesViewModel placesViewModel;
    private RelativeLayout relativeLayout;
    com.harryWorld.weatherGPS.map.utils.Places places = new com.harryWorld.weatherGPS.map.utils.Places();
    private double latitude;
    private double longitude;
    private int placesSize;
    private boolean isSaveClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_favourite);

        placesViewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
        titleEdit = findViewById(R.id.title_edit);
        enteringAddress = findViewById(R.id.entering_address);
        finger = findViewById(R.id.finger);
        saving = findViewById(R.id.saving_favourite);
        relativeLayout = findViewById(R.id.my_layout);
        titleText = findViewById(R.id.title_textview);

        enteringAddress.setOnClickListener(this);
        finger.setOnClickListener(this);
        saving.setOnClickListener(this);

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_dark));
        if (!getIntent().hasExtra(EMPTY_NAVIGATION) && !getIntent().hasExtra(EMPTY_SEARCH)
                && !getIntent().hasExtra(EMPTY_NAVIGATION_MAP) && !getIntent().hasExtra(EMPTY_SEARCH_MAP)) {
            titleEdit.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
        }
        else {
            Log.d(TAG, "onCreate: noooo");
        }

        setPlaces();
         if (getIntent().hasExtra(EMPTY_NAVIGATION)){
            Log.d(TAG, "onCreate: empty is triggured");
            places = getIntent().getParcelableExtra(EMPTY_NAVIGATION);
            if (places != null) {
                titleText.setVisibility(View.VISIBLE);
                titleEdit.setVisibility(View.GONE);
                if (places.getId() == 1){
                    titleEdit.setText(getString(R.string.home));
                }
                else{
                    titleEdit.setText(getString(R.string.work));
                }
            }
        }
        else if ( getIntent().hasExtra(EMPTY_SEARCH)){
            places = getIntent().getParcelableExtra(EMPTY_SEARCH);
             titleText.setVisibility(View.VISIBLE);
             titleEdit.setVisibility(View.GONE);
            if (places != null) {
                if (places.getId() == 1){
                    titleText.setText(getString(R.string.home));
                }
                else{
                    titleText.setText(getString(R.string.work));
                }
                if (places.getTitle() != null && !places.getTitle().trim().isEmpty()) {
                    titleEdit.setText(places.getTitle());
                }
                if (places.getContent() != null && !places.getContent().trim().isEmpty()) {
                    enteringAddress.setText(places.getContent());
                }
            }
        }
        else if (getIntent().hasExtra(EMPTY_SEARCH_MAP)){
            places = getIntent().getParcelableExtra(EMPTY_SEARCH_MAP);
            if (places != null) {
                titleText.setVisibility(View.VISIBLE);
                titleEdit.setVisibility(View.GONE);

                if (places.getId() == 1){
                    titleText.setText(getString(R.string.home));
                }
                else{
                    titleText.setText(getString(R.string.work));
                }
                if (places.getContent() != null && !places.getContent().trim().isEmpty()){
                    enteringAddress.setText(places.getContent());
                }

            }

        }
        else if ( getIntent().hasExtra(EMPTY_NAVIGATION_MAP)){
            places  = getIntent().getParcelableExtra(EMPTY_NAVIGATION_MAP);
            if (places != null) {
                titleText.setVisibility(View.VISIBLE);
                titleEdit.setVisibility(View.GONE);
                if (places.getId() == 1){
                    titleText.setText(getString(R.string.home));
                }
                else{
                    titleText.setText(getString(R.string.work));
                }
                double latitude = places.getLatitude();
                double longitude = places.getLongitude();
                setWeatherCurrent(latitude, longitude);
            }
        }
        else if (getIntent().hasExtra(FROM_SEARCH)){
            places = getIntent().getParcelableExtra(FROM_SEARCH);
            titleEdit.setText(places.getTitle());
            enteringAddress.setText(places.getContent());
        }
        else if (getIntent().hasExtra(FORWARD_LATITUDE)){
            places = getIntent().getParcelableExtra(FORWARD_LATITUDE);
            Log.d(TAG, "onCreate: puuull");
            titleEdit.setText(places.getTitle());
            if (places.getContent() != null && places.getContent().trim().length()>0) {
                enteringAddress.setText(places.getContent());
            }
        }
        else if (getIntent().hasExtra(PLACES_LIST_EDIT)) {
             places = getIntent().getParcelableExtra(PLACES_LIST_EDIT);
             Log.d(TAG, "onCreate: puuull");
             if (places != null){
                 titleEdit.setText(places.getTitle());
             if (places.getContent() != null && places.getContent().trim().length() > 0) {
                 enteringAddress.setText(places.getContent());
             }
         }
        }
         else if (getIntent().hasExtra(FROM_SEARCH_SECOND)) {
             places = getIntent().getParcelableExtra(FROM_SEARCH_SECOND);
             Log.d(TAG, "onCreate: puuull");
             if (places != null){
                 titleEdit.setText(places.getTitle());
                 if (places.getContent() != null && places.getContent().trim().length() > 0) {
                     enteringAddress.setText(places.getContent());
                 }
             }
         }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        adView = findViewById(R.id.adding_favourites_adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    private void setWeatherCurrent(double latitude, double longitude) {
        placesViewModel.setWeatherRepository(latitude, longitude).observe(this, new Observer<Resource<List<Weather>>>() {
            @Override
            public void onChanged(Resource<List<Weather>> listResource) {
                if (listResource.data != null && listResource.data.size() != 0) {
                    switch (listResource.status) {
                        case SUCCESS: {
                            Log.d(TAG, "onChanged: weather was retrieve successful " + listResource.data.get(0).getCityName());

                            String city = getMyCItyName(latitude,longitude);
                            String country = getCountry(latitude,longitude);
                            Weather weather = listResource.data.get(0);
                            places.setLatitude(latitude);
                            places.setLongitude(longitude);
                            places.setHumidity(weather.getHumidity());
                            places.setRain(weather.getPop());
                            places.setWind(weather.getWindSpeed());
                            places.setTemp(weather.getTemp());
                            places.setCityName(weather.getCityName());

                            if (city != null && country != null) {
                                if (!city.trim().equals("null")) {
                                    if (!country.trim().equals("null")) {
                                        places.setContent(city + "," + country);
                                    } else {
                                        places.setContent(city + "," + weather.getCountryCode());
                                    }
                                } else {
                                    if (!country.trim().equals("null")) {
                                        places.setContent(weather.getCityName() + "," + country);
                                    } else {
                                        places.setContent(weather.getCityName() + "," + weather.getCountryCode());
                                    }
                                }
                            }
                            enteringAddress.setText(places.getContent());

                        }
                        break;
                        case ERROR: {

                            Log.d(TAG, "onChanged: there was an error retrieving weather " + listResource.message);
                        }
                    }
                }
            }
        });
    }

    private String getMyCItyName(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);

            return addresses.get(0).getLocality();

        } catch (IOException e) {

            //google geo coding api
            e.printStackTrace();
        }
        return null;
    }

    private String getCountry(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);

            return addresses.get(0).getCountryName();
        } catch (IOException e) {

            //google geo coding api
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.entering_address:{
                if (getIntent().hasExtra(EMPTY_SEARCH)){
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent intent = new Intent(AddingFavouriteActivity.this,SearchActivity.class);
                    intent.putExtra(EMPTY_SEARCH,places);
                    startActivity(intent);
                    finish();
                }
                else {
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent intent = new Intent(AddingFavouriteActivity.this, SearchActivity.class);
                    intent.putExtra(FROM_SEARCH, places);
                    startActivity(intent);
                    finish();
                }
            }
            break;
            case R.id.finger:{
                if (getIntent().hasExtra(PLACES_LIST_EDIT)) {
                    int placesInt = getIntent().getIntExtra(PLACES_LIST_EDIT,3);
                    if (places == null) {
                        places = new Places();
                    }
                    places.setId(placesInt);
                    places.setTitle(titleEdit.getText().toString());
                    Intent mapIntent = new Intent(this, MapsActivity.class);
                    mapIntent.putExtra(ADDING_FAVOURITE, places);
                    startActivity(mapIntent);
                    finish();
                }
                else if (getIntent().hasExtra(EMPTY_NAVIGATION)){
                    places = getIntent().getParcelableExtra(EMPTY_NAVIGATION);
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent mapIntent = new Intent(this, MapsActivity.class);
                    mapIntent.putExtra(EMPTY_NAVIGATION_MAP, places);
                    startActivity(mapIntent);
                    finish();
                }
                else if (getIntent().hasExtra(EMPTY_SEARCH)){
                    places = getIntent().getParcelableExtra(EMPTY_SEARCH);
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent mapIntent = new Intent(this, MapsActivity.class);
                    mapIntent.putExtra(EMPTY_SEARCH, places);
                    startActivity(mapIntent);
                    finish();
                }
                else if (getIntent().hasExtra(EMPTY_NAVIGATION_MAP)){
                    places = getIntent().getParcelableExtra(EMPTY_NAVIGATION);
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent mapIntent = new Intent(this, MapsActivity.class);
                    mapIntent.putExtra(EMPTY_NAVIGATION_MAP, places);
                    startActivity(mapIntent);
                    finish();
                }
                else if (getIntent().hasExtra(EMPTY_SEARCH_MAP)){
                    places = getIntent().getParcelableExtra(EMPTY_SEARCH);
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent mapIntent = new Intent(this, MapsActivity.class);
                    mapIntent.putExtra(EMPTY_SEARCH_MAP, places);
                    startActivity(mapIntent);
                    finish();
                }
                else if (getIntent().hasExtra(FORWARD_LATITUDE)) {
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent favouriteIntent = new Intent(AddingFavouriteActivity.this, MapsActivity.class);
                    favouriteIntent.putExtra(ADDING_FAVOURITE, places);
                    startActivity(favouriteIntent);
                    finish();
                }
                else if (getIntent().hasExtra(FROM_SEARCH_SECOND)) {
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent favouriteIntent = new Intent(AddingFavouriteActivity.this, MapsActivity.class);
                    favouriteIntent.putExtra(ADDING_FAVOURITE, places);
                    startActivity(favouriteIntent);
                    finish();
                }
                else if (getIntent().hasExtra(FROM_SEARCH)) {
                    if (places == null) {
                        places = new Places();
                    }
                    places.setTitle(titleEdit.getText().toString());
                    Intent favouriteIntent = new Intent(AddingFavouriteActivity.this, MapsActivity.class);
                    favouriteIntent.putExtra(ADDING_FAVOURITE, places);
                    startActivity(favouriteIntent);
                    finish();
                }
            }
            break;
            case R.id.saving_favourite:{

                isSaveClicked = true;
                if (titleEdit.getVisibility() == View.VISIBLE) {
                    if ((!titleEdit.getText().toString().trim().equals(""))
                            && !enteringAddress.getText().toString().trim().isEmpty()) {
                        if (!enteringAddress.getText().toString().trim().equals(R.string.enter_address)) {
                            Log.d(TAG, "onClick: could be save");
                            if (getIntent().hasExtra(FORWARD_LATITUDE)) {
                                places.setTitle(titleEdit.getText().toString());
                                places.setId(getPlacesSize() + 1);
                                insertingPlace(places);
                                finish();
                            } else if (getIntent().hasExtra(EMPTY_NAVIGATION_MAP)) {

                                places.setTitle(titleEdit.getText().toString());
                                updatingPlace(places);
                                Intent intent = new Intent(AddingFavouriteActivity.this, NavigationActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (getIntent().hasExtra(FROM_SEARCH)) {

                                places.setTitle(titleEdit.getText().toString());
                                places.setId(getPlacesSize() + 1);
                                insertingPlace(places);
                                finish();
                            } else if (getIntent().hasExtra(EMPTY_SEARCH_MAP)) {
                                places.setTitle(titleEdit.getText().toString());

                                updatingPlace(places);
                                Intent intent = new Intent(AddingFavouriteActivity.this, SearchActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else {
                            Toast.makeText(this, getString(R.string.set_address_name), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.title_name_empty), Toast.LENGTH_SHORT).show();
                        forceShow(AddingFavouriteActivity.this);
                    }
                }
                else{
                    if (!enteringAddress.getText().toString().trim().equals(R.string.enter_address)) {
                        Log.d(TAG, "onClick: could be save");
                            places.setTitle(titleText.getText().toString());
                            updatingPlace(places);
                            Intent intent = new Intent(AddingFavouriteActivity.this, SearchActivity.class);
                            startActivity(intent);
                            finish();
                    }
                    else {
                        Toast.makeText(this, getString(R.string.set_address_name), Toast.LENGTH_SHORT).show();
                    }
                }
          ///saving locations to be added to favourite

            }
            break;
            case R.id.title_edit:{
                    if (!isSoftKeyEnabled(this)) {
                        forceShow(this);
                }
            }
            break;
            case R.id.my_layout:{
                    forceHide(this, titleEdit);
                    //hideEditText();

            }
            break;
        }
    }
    private boolean isSoftKeyEnabled(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    private static void forceShow(@NonNull Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    private static void forceHide(@NonNull Activity activity, @NonNull EditText editText) {
        if (activity.getCurrentFocus() == null || !(activity.getCurrentFocus() instanceof EditText)) {
            editText.requestFocus();
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    private void updatingPlace(com.harryWorld.weatherGPS.map.utils.Places places){
        placesViewModel.updatingPlaces(places).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource != null){
                    if (integerResource.status == Resource.Status.SUCCESS) {
                        Log.d(TAG, "onChanged: places was updated successfully" + integerResource.data);
                    } else {
                        Log.d(TAG, "onChanged: there was an error updating places " + integerResource.message);
                    }
                }
            }
        });
    }
    private void insertingPlace(com.harryWorld.weatherGPS.map.utils.Places places) {
        placesViewModel.gettingInsert(places).observe(this, new Observer<Resource<Long>>() {
            @Override
            public void onChanged(Resource<Long> longResource) {
                if (longResource != null) {
                    if (longResource.status == Resource.Status.SUCCESS) {
                        Log.d(TAG, "onChanged: places was inserted successfully" + longResource.data);
                    } else {
                        Log.d(TAG, "onChanged: there was an error inserting places " + longResource.message);
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (place.getLatLng() != null) {
                    double lat = place.getLatLng().latitude;
                    double lon = place.getLatLng().longitude;
                }
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra(FORWARD_LATITUDE)){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
    private void setPlaces(){
        placesViewModel.getPlaces.observe(this, new Observer<List<com.harryWorld.weatherGPS.map.utils.Places>>() {
            @Override
            public void onChanged(List<com.harryWorld.weatherGPS.map.utils.Places> places) {
                if (places != null) {
                    if (places.size() > 0) {
                        setPlacesSize(places.size());
                    }

                }
            }
        });
    }

    public int getPlacesSize() {
        return placesSize;
    }

    public void setPlacesSize(int placesSize) {
        this.placesSize = placesSize;
    }
}