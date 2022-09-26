package com.harryWorld.weatherGPS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.Priority;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.harryWorld.weatherGPS.map.activity.AddingFavouriteActivity;
import com.harryWorld.weatherGPS.map.activity.PlanSettingActivity;
import com.harryWorld.weatherGPS.map.activity.SearchActivity;
import com.harryWorld.weatherGPS.map.retrofit.GO;
import com.harryWorld.weatherGPS.map.utils.Places;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.harryWorld.weatherGPS.map.activity.CategoriesActivity;
import com.harryWorld.weatherGPS.map.activity.YourPlaceActivity;
import com.harryWorld.weatherGPS.map.constant.DetectedActivityReceiver;
import com.harryWorld.weatherGPS.map.utils.direction.Directions;
import com.harryWorld.weatherGPS.map.utils.direction.Segments;
import com.harryWorld.weatherGPS.map.utils.direction.Steps;
import com.harryWorld.weatherGPS.map.viewModels.CoordinateViewModel;
import com.harryWorld.weatherGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.weatherGPS.map.viewModels.VolleyViewModel;
import com.harryWorld.weatherGPS.schedule.Schedule;
import com.harryWorld.weatherGPS.schedule.ScheduleUI;
import com.harryWorld.weatherGPS.weather.MainActivity;
import com.harryWorld.weatherGPS.weather.api.Climate;
import com.harryWorld.weatherGPS.weather.repository.WeatherRepository;
import com.harryWorld.weatherGPS.weather.utils.Current;
import com.harryWorld.weatherGPS.weather.utils.Daily;
import com.harryWorld.weatherGPS.weather.utils.Hourly;
import com.harryWorld.weatherGPS.weather.utils.Resource;
import com.harryWorld.weatherGPS.weather.utils.Weather;
import com.harryWorld.weatherGPS.weather.viewModels.CurrentViewModel;
import com.harryWorld.weatherGPS.weather.viewModels.DailyViewModel;
import com.harryWorld.weatherGPS.weather.viewModels.HourlyViewModel;
import com.harryWorld.weatherGPS.weather.viewModels.MainViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.harryWorld.weatherGPS.map.constant.Constant.ADDING_FAVOURITE;
import static com.harryWorld.weatherGPS.map.constant.Constant.CHECK_RESULTS;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_NAVIGATION;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_NAVIGATION_MAP;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.EMPTY_SEARCH_MAP;
import static com.harryWorld.weatherGPS.map.constant.Constant.FIRST_ADDRESS;
import static com.harryWorld.weatherGPS.map.constant.Constant.FIRST_ADDRESS_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.FORWARD_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.LATITUDE_CATE_MAIN;
import static com.harryWorld.weatherGPS.map.constant.Constant.LATITUDE_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.Constant.LONGITUDE_CATE_MAIN;
import static com.harryWorld.weatherGPS.map.constant.Constant.LONGITUDE_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.Constant.MAIN_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.MAIN_SEARCH_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.MAP_NAVIGATION_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.NAVIGATION_SEARCH;
import static com.harryWorld.weatherGPS.map.constant.Constant.NEW_GUY_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.PLACES_LIST;
import static com.harryWorld.weatherGPS.map.constant.Constant.PLACES_LIST_EDIT;
import static com.harryWorld.weatherGPS.map.constant.Constant.SCHEDULE_U_I;
import static com.harryWorld.weatherGPS.map.constant.Constant.SECOND_ADDRESS;
import static com.harryWorld.weatherGPS.map.constant.Constant.SECOND_ADDRESS_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.STRING_CATE_SUB;
import static com.harryWorld.weatherGPS.map.constant.DetectedActivityReceiver.RECEIVER_ACTION;
import static com.harryWorld.weatherGPS.map.utils.direction.ConstantDIrection.CYCLING;
import static com.harryWorld.weatherGPS.map.utils.direction.ConstantDIrection.DRIVING;
import static com.harryWorld.weatherGPS.map.utils.direction.ConstantDIrection.WALKING;
import static com.harryWorld.weatherGPS.map.utils.direction.ConstantDIrection.WHEEL_CHAIR;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnPoiClickListener,
        SearchView.OnQueryTextListener,
        View.OnFocusChangeListener,
        View.OnClickListener, NavigationBarView.OnItemSelectedListener {

    private ReviewManager reviewManager;
    private ReviewInfo reviewInfo;

    private static final String TAG = "MapsActivity";
    private boolean current = false;
    private boolean fromCate = false;
    LatLng latLngCate = null;
    private boolean daily;
    private boolean hourly;
    private double connectionLat, connectionLon;
    boolean speechBoolean = true;
    private boolean cateBoo = true;
    private boolean allowingMaker = true;
    private ActivityResultLauncher<Intent> startForResult;
    boolean getOut = false;

    Polyline polyline = null;
    double zoomLevel = 0.0;
    private boolean showAds;
    Climate climate = new Climate();
    List<Daily> dailyListNow = new ArrayList<>();
    List<Hourly> hourlyListNow = new ArrayList<>();
    Current currentNow = new Current();
    List<LatLng> points = new ArrayList<>();
    List<LatLng> updateList = new ArrayList<>();
    List<String> updateDistance = new ArrayList<>();
    List<String> updateDuration = new ArrayList<>();
    List<String> stepsInstruction = new ArrayList<>();
    List<String> stepsNames = new ArrayList<>();
    List<Integer> stepsWayPoint = new ArrayList<>();
    List<List<LatLng>> trafficRoute = new ArrayList<>();
    List<Integer> mainDuration = new ArrayList<>();
    List<Integer> duration_in_traffic = new ArrayList<>();
    String travelMode = "nodfljkgh";
    private String travelForm = "car";
    private PolylineOptions options;

    private VolleyViewModel volleyViewModel;

    List<LatLng> categoriesCord = new ArrayList<>();
    List<Marker> categoriesMarker = new ArrayList<>();
    private FusedLocationProviderClient client;
    private FragmentContainerView mainMap;
    private boolean isEditClicked;
    private boolean isListNull;
    private boolean isAllowed;
    private boolean isClicked;
    private boolean lightMode = true;
    private boolean fromFirstAddress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private boolean moved = false;
    private BottomNavigationView nav;
    private LinearLayout layoutStart;
    private TextView start;
    private TextView timeTaken, distanceTaken;
    private TextView subLocal;
    private ImageView navMode;
    private ImageView navCategories, navSpeakerOn, navSpeakerOff;
    private PlacesViewModel placesViewModel;
    private Places places;
    private TextView saveButton;
    private Marker currentMaker;
    private Marker homeMaker;
    private Marker workMaker;
    private ImageView car, bike, motor, bus, walk;

    LocationRequest locationRequest;
    double latSource;
    double longSource;
    List<LatLng> coordinatesList = new ArrayList<>();
    int burd = 0;
    List<String> instructions = new ArrayList<>();
    List<String> shortInstructions = new ArrayList<>();
    List<Double> stepListDuraiton = new ArrayList<>();
    List<Double> stepListDistance = new ArrayList<>();
    private double totalDistance, totalDuration;
    List<List<HashMap<String, String>>> routeList = new ArrayList<>();
    TextToSpeech tts;
    String routeMode = "no idea";
    private boolean isZoom = false;

    private Polyline mPolyline;
    private String defaultCityName;
    private String defaultCountryCode;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    //    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
//            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private final static String REQUIRED_SDK_PERMISSIONS = Manifest.permission.ACCESS_FINE_LOCATION;
    private PointOfInterest interest;

    private static final int INITIAL_STROKE_WIDTH_PX = 5;


    protected LatLng startLatitude = null;
    protected LatLng endLatitude = null;


    //polyline object
    private List<Polyline> polylines = null;

    private LinearLayout relativeWeather, bottomLayout;
    private ImageView searchNext, goNext, imageEdit;
    private TextView cityName, temp, wind, humidity, rain;
    private LinearLayout layout1, wrapLayout;
    RelativeLayout.LayoutParams relativeParams, params1;
    private RewardedAd mRewardedAd;

    LatLng ori;
    LatLng dest;
    private double latitude, longitude;
    LocationRequest request;
    LocationCallback callback;
    LatLng currentLocation;
    PendingIntent pendingIntent;
    ActivityTransitionRequest finalRequest;
    private MainViewModel mainViewModel;
    public String data;
    private ProgressBar bigLoading;

    public CoordinateViewModel coordinateViewModel;
    private CurrentViewModel currentViewModel;
    private DailyViewModel dailyViewModel;
    private HourlyViewModel hourlyViewModel;

    private GoogleMap mMap;

    String myCItyName;
    private boolean isDatabaseEmpty;
    private boolean isLocationNeeded;
    private boolean isSlided;
    private Schedule schedule;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        isSlided = false;
        instantiating();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.category_map);
        mapFragment.getMapAsync(this);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        volleyViewModel = new ViewModelProvider(this).get(VolleyViewModel.class);
        gettingCallback();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.commit();

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_light));


        //properties();
        //activateTransition();
        //getCoordinates();
        setPlaces();
        deleteCurrent();
        setLoading();
        getDirectionsLive();
        observeStepsInstruction();
        observeDistance();
        observeDuration();
        observeStepsWayPoint();
        observeStepsName();
        checkingConnection();
        gettingResults();
        gettingLayoutStart();
        observeCityName();
        setChoose();
        setReviewManager();
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-2650278620916037/6279356363",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });

        hasInternetConnection().subscribe((hasInternet) -> {
            if (hasInternet) {
                Log.d(TAG, "onCreate: has internet");
            } else {
                Log.d(TAG, "onCreate: there is no internet");
            }
        });


        if (saveButton.getVisibility() == View.VISIBLE) {
            wrapLayout.setVisibility(View.GONE);
        }

    }






    //    private void showChangeThemeAlertDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Change Theme");
//        builder.setSingleChoiceItems(themes, checkedTheme, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                checkedTheme = which;
//                switch (which) {
//                    case 0:
//                        setAppTheme(0);
//                        break;
//                    case 1:
//                        setAppTheme(1);
//                        break;
//                    case 2:
//                        setAppTheme(2);
//                        break;
//                }
//                dialog.dismiss();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//    }
    void setReviewManager(){
        reviewManager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                 reviewInfo = task.getResult();
                Log.d(TAG, "setReviewManager: sdkfdkdfdf "+reviewInfo.toString());
            } else {
                // There was some problem, log or handle the error code.
             //   @ReviewErrorCode int reviewErrorCode = ((TaskException) task.getException()).getErrorCode();
            }
        });
    }
    void checkLastAction(){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 8);
        if (randomNum == 7){
            showAds =false;
            showReview();
        }
        else if(showAds && randomNum % 3 == 0){
            loadAd();
        }

    }
    void showReview(){
        Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
        flow.addOnCompleteListener(task -> {
            Log.d(TAG, "showReview: sdfkjdfhjdjd "+task.toString());
            // The flow has finished. The API does not indicate whether the user
            // reviewed or not, or even whether the review dialog was shown. Thus, no
            // matter the result, we continue our app flow.
        });
    }
    void gettingLayoutStart(){
        volleyViewModel.getShowStart().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null) {
                    if (aBoolean) {
                        layoutStart.setVisibility(View.VISIBLE);
                    }
                    else {
                        layoutStart.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
    private void settingSpeech(){
        speechBoolean = true;
        allowingMaker = false;
    }
    private void drawRoute(String mode, String start, String end) {
        volleyViewModel.connector.loading.setValue(true);
        coordinateViewModel.getOpenDirections(mode, start, end);
    }

    private void checkingConnection(){
        mainViewModel.getConnected().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean){
                    setWeatherCurrent(connectionLat, connectionLon);
                }
            }
        });
    }
    private void getDirectionsLive() {
//        if (volleyViewModel.connector.liveOptions == null) {
//            volleyViewModel.connector.loading.setValue(true);
        coordinateViewModel.getDirectionsLive().observe(this, new Observer<Directions>() {
            @Override
            public void onChanged(Directions directions) {
                volleyViewModel.connector.loading.setValue(false);
                if (directions != null) {
                    //  volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.START);
                    List<Double> latitude = new ArrayList<>();
                    List<Double> longitude = new ArrayList<>();
                    List<List<Double>> coordinates = directions.getFeatures().get(0).getGeometry().getCoordinates();
                    Segments segments = directions.getFeatures().get(0).getProperties().getSegments().get(0);
                    List<Steps> steps = segments.getSteps();

                    for (int i = 0; i < coordinates.size(); i++) {
                        latitude.add(coordinates.get(i).get(1));
                        longitude.add(coordinates.get(i).get(0));
                    }
                    List<LatLng> latLng = new ArrayList<>();
                    for (int j = 0; j < latitude.size(); j++) {

                        latLng.add(new LatLng(latitude.get(j), longitude.get(j)));
                    }
                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.addAll(latLng);
                    volleyViewModel.connector.liveOptions.setValue(polylineOptions);

                    List<String> stepsDirection = new ArrayList<>();
                    List<String> stepsName = new ArrayList<>();
                    List<Double> stepsDistance = new ArrayList<>();
                    List<Double> stepsDuration = new ArrayList<>();
                    List<Integer> stepsWayPoints = new ArrayList<>();


                    for (int k = 0; k < steps.size(); k++) {
                        stepsDirection.add(steps.get(k).getInstruction());
                        stepsName.add(steps.get(k).getName());
                        stepsDistance.add(steps.get(k).getDistance());
                        stepsDuration.add(steps.get(k).getDuration());
                        stepsWayPoints.add(steps.get(k).getWay_points().get(0));
                    }
                    stepListDuraiton.addAll(stepsDuration);
                    stepListDistance.addAll(stepsDistance);
                    totalDistance = segments.getDistance();
                    totalDuration = segments.getDuration();
                    volleyViewModel.setLiveStepsDirection(stepsDirection);
                    volleyViewModel.setLiveDistace(convertingDistance(segments.getDistance()));
                    volleyViewModel.setLiveDuration(convertingTime(segments.getDuration()));
                    volleyViewModel.setLiveStepsName(stepsName);
                    volleyViewModel.setLiveWayPoint(stepsWayPoints);

                } else {
                    volleyViewModel.setLiveStepsDirection(null);
                    volleyViewModel.setLiveDistace(null);
                    volleyViewModel.setLiveDuration(null);
                    volleyViewModel.setLiveStepsName(null);
                    volleyViewModel.setLiveWayPoint(null);
                    volleyViewModel.connector.liveOptions.setValue(null);
                    Log.d(TAG, "onChanged: there was an error with open directions");
                }
            }
        });
        //     }
    }

    private String convertingTime(double seconds) {
        double divide = seconds / 2;
        seconds += divide;
        double hour1 = seconds / 3600;
        double roundHour = Math.floor(hour1);
        double minute = hour1 - roundHour;
        minute *= 60;
        int minute1 = (int) minute;
        int hour = (int) roundHour;
        int day;
        if (hour >= 24) {
            day = (int) Math.floor(hour / 24);
            hour = hour % 24;
            if (day > 1) {
                return day + " " + getString(R.string.days) + " " + hour + " " + getString(R.string.hours);
            } else {
                return day + " " + getString(R.string.day) + " " + hour + " " + getString(R.string.hours);
            }
        } else {
            if (roundHour > 1 && minute1 > 1) {
                return hour + " " + getString(R.string.hours) + " " + minute1 + " " + getString(R.string.minutes);
            } else if (hour > 1 && minute1 <= 1) {
                return hour + " " + getString(R.string.hours) + " " + minute1 + " " + getString(R.string.minute);
            } else if (hour == 1 && minute1 > 1) {
                return hour + " " + getString(R.string.hour) + " " + minute1 + " " + getString(R.string.minutes);
            } else if (hour == 0) {
                return minute1 + " " + getString(R.string.minutes);
            }
            return hour + " " + getString(R.string.hour) + " " + minute1 + " " + getString(R.string.minute);
        }
    }

    private String convertingDistance(double duration) {
        int km = (int) (duration / 1000);
        return "(" + km + "Km" + ")";
    }

    private void insertingDefault() {
        Log.d(TAG, "insertingDefault: list was null");
        Places places = new Places();
        Places places1 = new Places();

        places.setId(1);
        places1.setId(2);

        places.setTitle(getString(R.string.home));
        places1.setTitle(getString(R.string.work));

        places.setContent(getString(R.string.home_address));
        places1.setContent(getString(R.string.home_address));

        Log.d(TAG, "insertingDefault: this was okay");
        insertingPlace(places);
        insertingPlace(places1);

    }

    private void insertingPlace(Places places) {
        placesViewModel.gettingInsert(places).observe(this, new Observer<Resource<Long>>() {
            @Override
            public void onChanged(Resource<Long> longResource) {
                if (longResource != null) {
                    if (longResource.status == Resource.Status.SUCCESS) {
                        Log.d(TAG, "onChanged: places was inserted successfully" + longResource.data);
                        isDatabaseEmpty = false;
                    } else {
                        isDatabaseEmpty = true;
                        Log.d(TAG, "onChanged: there was an error inserting places " + longResource.message);
                    }
                }
            }
        });
    }

    private void alMightyFunction() {
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                zoomLevel = mMap.getCameraPosition().zoom;

                if (options != null) {
                    if (zoomLevel < 11.0) {
                        options.width(17.0f);
                        if (polyline != null) {
                            polyline.setWidth(17.0f);
                        }
                    } else {
                        options.width(17.0f);
                        if (polyline != null) {
                            polyline.setWidth(17.0f);
                        }
                    }
                }
            }
        });
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                zoomLevel = mMap.getCameraPosition().zoom;
                if (volleyViewModel.getMapSate().getValue() == VolleyViewModel.mapState.RECENTER && cateBoo) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latSource, longSource)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latSource, longSource)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latSource, longSource), (float) zoomLevel));
                }
                if (options != null) {
                    if (zoomLevel < 11.0) {
                        options.width(16.0f);
                        if (polyline != null) {
                            polyline.setWidth(16.0f);
                        }
                    } else {
                        options.width(17.0f);
                        if (polyline != null) {
                            polyline.setWidth(17.0f);
                        }
                    }
                }
                Log.d(TAG, "onCameraIdle: checking zoom level " + zoomLevel);
                // polyline.setWidth();
            }
        });

        if (getIntent().hasExtra(PLACES_LIST)) {

            isEditClicked = false;
            showAds = false;
            Log.d(TAG, "alMightyFunction: favourite clicked");
            places = getIntent().getParcelableExtra(PLACES_LIST);
            if (places != null) {
                if (places.getLatitude() == 0.0 && places.getLongitude() == 0.0) {
                    isLocationNeeded = false;
                    Log.d(TAG, "alMightyFunction: still heading to cape coast");
                    imageEdit.setVisibility(View.GONE);
                    drawMaker(new LatLng(0.0, 0.0));
                    saveButton.setVisibility(View.VISIBLE);

                } else {
                    connectionLat = places.getLatitude();
                    connectionLon = places.getLongitude();
                    isAllowed = false;
                    imageEdit.setVisibility(View.VISIBLE);
                    isLocationNeeded = false;
                    Log.d(TAG, "alMightyFunction: no more cape coast");
                    isListNull = false;
                    saveButton.setVisibility(View.GONE);
                    LatLng position = new LatLng(places.getLatitude(), places.getLongitude());

                    Log.d(TAG, "alMightyFunction: checking name " + places.getTitle());
                    Log.d(TAG, "alMightyFunction: content name " + places.getContent());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(position));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 13f));
                    setWeatherCurrent(position.latitude, position.longitude);
                }
            }

            layout1.setVisibility(View.GONE);
            nav.setVisibility(View.GONE);

        }
        else if (getIntent().hasExtra("categories")) {
            showAds = false;
            String name;
            name = getIntent().getStringExtra("categories");

        }
        else if (getIntent().hasExtra(ADDING_FAVOURITE) || getIntent().hasExtra(EMPTY_NAVIGATION_MAP)
                || getIntent().hasExtra(EMPTY_SEARCH_MAP)) {
            showAds = false;
            saveButton.setVisibility(View.VISIBLE);
            isEditClicked = false;
            isLocationNeeded = false;
            layout1.setVisibility(View.GONE);
            nav.setVisibility(View.GONE);
            drawMaker(new LatLng(0.0f, 0.0f));
        }
        else if (getIntent().hasExtra(EMPTY_NAVIGATION_MAP)) {
            showAds = false;
            places = getIntent().getParcelableExtra(EMPTY_NAVIGATION_MAP);
            isEditClicked = false;
            isLocationNeeded = true;
            // checkLocationUpdate();
            layout1.setVisibility(View.GONE);
            nav.setVisibility(View.GONE);
            connectionLat = places.getLatitude();
            connectionLon = places.getLongitude();

            getLocation();
        }
        else if (getIntent().hasExtra(EMPTY_SEARCH_MAP)) {
            showAds = false;
            places = getIntent().getParcelableExtra(EMPTY_SEARCH_MAP);
            isEditClicked = false;
            isLocationNeeded = true;
            // checkLocationUpdate();
            layout1.setVisibility(View.GONE);
            nav.setVisibility(View.GONE);

            connectionLat = places.getLatitude();
            connectionLon = places.getLongitude();
            getLocation();
        }
        else if (getIntent().hasExtra(EMPTY_SEARCH)) {
            showAds = false;
            saveButton.setVisibility(View.VISIBLE);
            isEditClicked = false;
            isLocationNeeded = false;
            layout1.setVisibility(View.GONE);
            nav.setVisibility(View.GONE);
            drawMaker(new LatLng(0.0, 0.0));
        }
        else if (getIntent().hasExtra(FIRST_ADDRESS)) {
            showAds = false;
            schedule = getIntent().getParcelableExtra(FIRST_ADDRESS);
            isLocationNeeded = false;
            layout1.setVisibility(View.GONE);
            nav.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            drawMaker(new LatLng(0.0, 0.0));
            connectionLat = schedule.getFinalLatitudeValue();
            connectionLon = schedule.getFinalLongitudeValue();
        }
        else if (getIntent().hasExtra(SECOND_ADDRESS)) {
            showAds = false;
            schedule = getIntent().getParcelableExtra(SECOND_ADDRESS);
            isLocationNeeded = false;
            layout1.setVisibility(View.GONE);
            nav.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            drawMaker(new LatLng(0.0, 0.0));
            connectionLat = schedule.getFinalLatitudeValue();
            connectionLon = schedule.getFinalLongitudeValue();
        }
        else if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE)) {
            showAds = false;
            places = getIntent().getParcelableExtra(MAIN_SEARCH_LATITUDE);
            if (places != null) {
                isLocationNeeded = true;
                latitude = places.getLatitude();
                longitude = places.getLongitude();

                connectionLat = latitude;
                connectionLon = longitude;
                isLocationNeeded = false;
                layout1.setVisibility(View.GONE);
                wrapLayout.setVisibility(View.GONE);
                nav.setVisibility(View.GONE);
                subLocal.setText(places.getCityName());
                if (subLocal.getText().toString().length() > 14) {
                    subLocal.setTextSize(12.0f);
                }
                setWeatherCurrent(latitude, longitude);
                volleyViewModel.setShowStart(true);
                if (instructions.size() > 0) {
                    volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.START);

                }

            }
        }
        else if (getIntent().hasExtra(NAVIGATION_SEARCH)) {
            showAds = false;
            places = getIntent().getParcelableExtra(NAVIGATION_SEARCH);
            isLocationNeeded = false;
            if (places != null) {
                if (places.getLatitude() == 0.0 && places.getLongitude() == 0.0) {
                    Intent intent = new Intent(this, AddingFavouriteActivity.class);
                    intent.putExtra(EMPTY_NAVIGATION, places.getId());
                } else {
                    connectionLat = places.getLatitude();
                    connectionLon = places.getLongitude();
                    nav.setVisibility(View.GONE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    setWeatherCurrent(latitude, longitude);
                }
            }
        }
        else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
            showAds = false;
            isLocationNeeded = false;
            layout1.setVisibility(View.GONE);
            wrapLayout.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            saveButton.setText(R.string.choose);
            drawMaker(new LatLng(0.0, 0.0));
        }
        else if (getIntent().hasExtra(CHECK_RESULTS)) {
            showAds = false;
            places = getIntent().getParcelableExtra(CHECK_RESULTS);
            if (places != null) {
                connectionLat = places.getLatitude();
                connectionLon = places.getLongitude();
                volleyViewModel.connector.loading.setValue(true);
                Log.d(TAG, "alMightyFunction: lets see whats in places " + places.getCityName());
                isLocationNeeded = false;
                layout1.setVisibility(View.GONE);
                wrapLayout.setVisibility(View.GONE);
                nav.setVisibility(View.GONE);
                setWeatherCurrent(places.getLatitude(), places.getLongitude());
            } else {
                Log.d(TAG, "alMightyFunction: places was null");
            }
        }
        else if (getIntent().hasExtra(SCHEDULE_U_I)) {
            showAds = false;
            volleyViewModel.connector.loading.setValue(true);
            searchNext.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.VISIBLE);
            volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.START);
            nav.setVisibility(View.GONE);
            volleyViewModel.setShowStart(true);
            schedule = getIntent().getParcelableExtra(SCHEDULE_U_I);
            String[] result = schedule.getFromDestination().split(",", 2);
            String first = result[0];

            connectionLat = schedule.getFinalLatitudeValue();
            connectionLon = schedule.getFinalLongitudeValue();
            String[] results = schedule.getFinalDestination().split(",", 2);
            String second = results[0];
            subLocal.setText(first + " " + "-" + " " + second);
            if (subLocal.getText().toString().length() > 22) {
                subLocal.setTextSize(14.0f);
            }
            LatLng origin = new LatLng(schedule.getFromLatitudeValue(), schedule.getFromLongitudeValue());
            LatLng destination = new LatLng(schedule.getFinalLatitudeValue(), schedule.getFinalLongitudeValue());
            Log.d(TAG, "alMightyFunction: what is happening "+schedule.getFinalLatitudeValue());
            drawMaker(destination);
            setWeatherCurrent(origin.latitude, origin.longitude);
            String start = origin.longitude + "," + origin.latitude;
            String end = destination.longitude + "," + destination.latitude;
            connectionLat = schedule.getFinalLatitudeValue();
            connectionLon = schedule.getFinalLongitudeValue();
            drawRoute(DRIVING, start, end);
            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
            if (points.size() > 0) {
                volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.START);
            }
        }
        else if (getIntent().hasExtra(NEW_GUY_LATITUDE)){
            showAds = false;
            List<LatLng> latLng = (List<LatLng>) getIntent().getSerializableExtra(NEW_GUY_LATITUDE);
            double lat = latLng.get(0).latitude;
            double lon = latLng.get(0).longitude;

            Log.d(TAG, "alMightyFunction: chghghghfdghghhgnhjhgv "+lat+", "+lon);
                connectionLat = lat;
                connectionLon = lon;
                isLocationNeeded = false;
                layout1.setVisibility(View.GONE);
                wrapLayout.setVisibility(View.GONE);
                nav.setVisibility(View.GONE);

                setWeatherCurrent(lat, lon);
                volleyViewModel.setShowStart(true);
                if (instructions.size() > 0) {
                    volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.START);

                }


        }
        else {
            volleyViewModel.connector.loading.setValue(true);
            showAds = true;
            isLocationNeeded = true;
            getLocation();
        }
    }


    private void instantiating() {
        client = LocationServices.getFusedLocationProviderClient(this);
        placesViewModel = new ViewModelProvider(this).get(PlacesViewModel.class);


        wrapLayout = findViewById(R.id.wrap_layout);
        mainMap = findViewById(R.id.category_map);
        navSpeakerOff = findViewById(R.id.navi_speaker_off);
        navSpeakerOn = findViewById(R.id.navi_speaker_on);
        timeTaken = findViewById(R.id.time_taken);// time to reach destination
        distanceTaken = findViewById(R.id.distance_taken); // distance to take reach destination
        navMode = findViewById(R.id.nav_mode);
        subLocal = findViewById(R.id.sub_local);
        car = findViewById(R.id.choosing_car);
        motor = findViewById(R.id.choosing_motor);
        bike = findViewById(R.id.choosing_bike);
        bus = findViewById(R.id.choosing_Bus);
        walk = findViewById(R.id.choosing_walk);

        coordinateViewModel = new ViewModelProvider(this).get(CoordinateViewModel.class);
        currentViewModel = new ViewModelProvider(this).get(CurrentViewModel.class);
        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel.class);
        hourlyViewModel = new ViewModelProvider(this).get(HourlyViewModel.class);

        navCategories = findViewById(R.id.nav_categories);
        nav = findViewById(R.id.nav_map);
        nav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        nav.setOnItemSelectedListener(this);
        relativeWeather = findViewById(R.id.weather_relative_next);
        bottomLayout = findViewById(R.id.bottom_layout);
        relativeParams = (RelativeLayout.LayoutParams) relativeWeather.getLayoutParams();
        searchNext = findViewById(R.id.image_search);
        imageEdit = findViewById(R.id.image_edit);
        cityName = findViewById(R.id.city_name_next);
        temp = findViewById(R.id.temp_result_next);
        wind = findViewById(R.id.wind_speed_result_next);
        humidity = findViewById(R.id.humidity_result_next);
        rain = findViewById(R.id.rain_result_next);
        saveButton = findViewById(R.id.save_button);
        layoutStart = findViewById(R.id.layout_start);
        start = findViewById(R.id.start);
        bigLoading = findViewById(R.id.big_loading);


        layout1 = findViewById(R.id.linear_layout_next);
        params1 = (RelativeLayout.LayoutParams) layout1.getLayoutParams();


    }

    public void slideDown(View view) {
        isSlided = true;
        view.setVisibility(View.GONE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void visibility(Places places) {
        if (places != null) {
            if (places.getCityName() != null) {
                if (getIntent().hasExtra(NEW_GUY_LATITUDE)){
                    subLocal.setText(places.getCityName());
                    if (subLocal.getText().toString().length() > 14) {
                        subLocal.setTextSize(12.0f);
                    }
                }
                else if(getIntent().hasExtra(MAIN_SEARCH_LATITUDE) && subLocal.getText().toString().trim().length()==0){
                    subLocal.setText(places.getCityName());
                    if (subLocal.getText().toString().length() > 14) {
                        subLocal.setTextSize(12.0f);
                    }
                }
                if (getIntent().hasExtra(CHECK_RESULTS)) {
                    //cityName is stored at places.getErrorMessage from the previous activity
                    cityName.setText(places.getErrorMessage());
                    volleyViewModel.setCityViewModel(places.getErrorMessage());
                } else {
                    cityName.setText(places.getCityName());

                }
                double number = places.getWind();
                number = Math.round(number * 100);
                number = number / 100;
                Log.d(TAG, "visibility: should be visible");
                if (places.getTemp() != null) {
                    temp.setText(Math.round(places.getTemp()) + "°");
                    wind.setText(number + "m/s");
                    humidity.setText(Math.round(places.getHumidity()) + "%");
                    rain.setText(places.getRain() + "%");
                    relativeWeather.setVisibility(View.VISIBLE);
                }
            } else {
                relativeWeather.setVisibility(View.GONE);
            }

            if (mMap != null && places.getLongitude() != 0.0 && places.getLatitude() != 0.0) {
                Log.d(TAG, "visibility: getting longitude " + places.getLongitude());
                LatLng position = new LatLng(places.getLatitude(), places.getLongitude());
                if (points == null || points.size() == 0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                    mMap.setOnPoiClickListener(MapsActivity.this);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(position));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10f));
                    if (getIntent().hasExtra(CHECK_RESULTS)) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 13f));
                        volleyViewModel.setShowStart(true);
                    }
                }
                if (isClicked) {
                    Log.d(TAG, "visibility: save was clicked");
//                    places.setContent(getMyCItyName(places.getLatitude(), places.getLongitude()) + "," + getCountry(places.getLatitude(), places.getLongitude()));
//
//                    setContentForward(getMyCItyName(places.getLatitude(), places.getLongitude()) + "," + getCountry(places.getLatitude(), places.getLongitude()));

                    updatingWeather(places);
                } else {
                    Log.d(TAG, "visibility: save was not clicked");
                }

                Log.d(TAG, "visibility: checking update +" + places.getContent());
            } else {
                Log.d(TAG, "visibility: was still null");
            }

        } else {
            Log.d(TAG, "visibility: places was null");
        }
    }


    public void settingProperties(Climate climate) {
        Log.d(TAG, "settingProperties: we are now in properties");
        String cityName = climate.getLocation().getName();
        double humidity = climate.getCurrent().getHumidity();
        double wind = climate.getCurrent().getWindSpeed();
        int rain = climate.getForecast().getForecastday().get(0).getHour().get(0).getProbability();
        double temp = climate.getCurrent().getTemp();
        double latitude = climate.getLocation().getLatitude();
        double longitude = climate.getLocation().getLongitude();

        if (getIntent().hasExtra(PLACES_LIST)) {
            places = getIntent().getParcelableExtra(PLACES_LIST);

            places.setHumidity(humidity);
            places.setRain(rain);
            places.setWind(wind);
            places.setTemp(temp);
            places.setCityName(cityName);
            //  places.setContent(getMyCItyName(places.getLatitude(), places.getLongitude()) + "," + getCountry(places.getLatitude(), places.getLongitude()));

        }
        else if (getIntent().hasExtra(ADDING_FAVOURITE)) {
            places = new Places();
            places.setLatitude(latitude);
            places.setLongitude(longitude);
            places.setHumidity(humidity);
            places.setRain(rain);
            places.setWind(wind);
            places.setTemp(temp);
            places.setCityName(cityName);
        }
        else if (getIntent().hasExtra(CHECK_RESULTS)) {
            places.setHumidity(humidity);
            places.setRain(rain);
            places.setWind(wind);
            places.setTemp(temp);
            places.setErrorMessage(cityName);
            if (currentMaker != null) {
                currentMaker.setTitle(places.getCityName());
                currentMaker.setSnippet(places.getContent());
                //currentMaker.showInfoWindow();
            }
        }
        else {
            places = new Places();
            places.setLatitude(latitude);
            places.setLongitude(longitude);
            places.setHumidity(humidity);
            places.setRain(rain);
            places.setWind(rain);
            places.setTemp(temp);
            places.setCityName(cityName);
        }


        //updatingWeather(places);
        visibility(places);

    }

    private void updatingWeather(com.harryWorld.weatherGPS.map.utils.Places places) {
        placesViewModel.updatingPlaces(places).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource != null) {
                    if (integerResource.status == Resource.Status.SUCCESS) {
                        Log.d(TAG, "onChanged: placesNextActivity updating places was successful ");

                    } else {
                        Log.d(TAG, "onChanged: placesNextActivity there was an error updating places " + integerResource.message);
                    }
                }
            }
        });
    }

    private void insertingWeather(com.harryWorld.weatherGPS.map.utils.Places places) {
        placesViewModel.gettingInsert(places).observe(this, new Observer<Resource<Long>>() {
            @Override
            public void onChanged(Resource<Long> integerResource) {
                if (integerResource != null) {
                    if (integerResource.status == Resource.Status.SUCCESS) {
                        Log.d(TAG, "onChanged: placesNextActivity inserting places was successful ");
                        visibility(places);
                    } else {
                        Log.d(TAG, "onChanged: placesNextActivity there was an error inserting places " + integerResource.message);
                    }
                }
            }
        });
    }

    private void showAllMarkers(List<LatLng> markerList) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (LatLng m : markerList) {
            builder.include(m);
        }

        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.30);

        // Zoom and animate the google map to show all markers

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);
    }

    private void gettingMoment(Weather weather) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        Log.d(TAG, "onChanged: checking current data " + weather.getCityName());

        String sunriseString = weather.getSunrise();
        int sunriseHourly = Integer.parseInt(sunriseString.substring(0, 2));
        int sunriseMinute = Integer.parseInt(sunriseString.substring(3));

        String sunsetString = weather.getSunset();
        int sunsetHourly = Integer.parseInt(sunsetString.substring(0, 2));
        int sunsetMinute = Integer.parseInt(sunsetString.substring(3));

        if (currentHour >= sunriseHourly && currentHour < sunsetHourly) {
            Log.d(TAG, "onChanged: first was called");
            if (currentHour == sunriseHourly) {
                if (currentMinute >= sunriseMinute) {
                    //light mode activated
                    lightMode = true;
                } else {
                    //dark mode activated
                    lightMode = false;
                }
            } else {
                //light mode activated
                lightMode = true;
            }
        } else if (sunriseHourly > sunsetHourly) {
            Log.d(TAG, "onChanged: second was called");
            if (currentHour >= sunriseHourly || currentHour <= sunsetHourly) {
                if (currentHour == sunriseHourly) {
                    if (currentMinute >= sunriseMinute) {
                        //     gettingLightMode();
                        lightMode = true;
                    } else {
                        //   gettingDarkMode();
                        lightMode = false;
                    }
                } else if (currentMinute == sunsetHourly) {
                    if (currentMinute < sunsetMinute) {
                        //  gettingLightMode();
                        lightMode = true;
                    } else {
                        // gettingDarkMode();
                        lightMode = false;
                    }
                } else {
                    // gettingLightMode();
                    lightMode = true;
                }

            } else {
                //  gettingDarkMode();
                lightMode = false;
            }
        } else {
            Log.d(TAG, "onChanged: last was called");
            //  gettingDarkMode();
            lightMode = false;
        }
    }

    //    private void setWeatherCurrent(double latitude, double longitude) {
//        placesViewModel.setWeatherRepository(latitude, longitude).observe(this, new Observer<Resource<List<Weather>>>() {
//            @Override
//            public void onChanged(Resource<List<Weather>> listResource) {
//                if (listResource.data != null && listResource.data.size() != 0) {
//                    switch (listResource.status) {
//                        case SUCCESS: {
//                            bigLoading.setVisibility(View.GONE);
//                            gettingMoment(listResource.data.get(0));
//                            Log.d(TAG, "onChanged: checking light mode " + lightMode);
//                            Log.d(TAG, "onChanged: weather was retrieve successful " + listResource.data.get(0).getCityName());
//                            LatLng position = new LatLng(latitude, longitude);
//                            if (getIntent().hasExtra(PLACES_LIST)) {
//                                if (imageEdit.getVisibility() == View.VISIBLE) {
//                                    isAllowed = false;
//                                    Log.d(TAG, "onChanged: dammm");
//                                    settingProperties(listResource.data.get(0));
//                                } else {
//                                    Log.d(TAG, "onChanged: whaat");
//                                    relativeWeather.setVisibility(View.GONE);
//                                    drawMaker(position);
//                                }
//                            }
//                            if (getIntent().hasExtra(ADDING_FAVOURITE)) {
//                                if (isClicked) {
//                                    settingProperties(listResource.data.get(0));
//                                } else {
//
//                                    drawMaker(position);
//                                }
//                            }
//                            else if (current){
//                                settingProperties(listResource.data.get(0));
//                                gettingMoment(listResource.data.get(0));
//
//                            }
//                            else if (getIntent().hasExtra(FIRST_ADDRESS) || getIntent().hasExtra(SECOND_ADDRESS))
//                            {
//                                drawMaker(position);
//                            }
//                            else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
//                                settingProperties(listResource.data.get(0));
//                                gettingMoment(listResource.data.get(0));
//                            }
//                            else {
//                                    Log.d(TAG, "onChanged: dammm1");
//                                    settingProperties(listResource.data.get(0));
//                                    drawMaker(position);
//                                    gettingMoment(listResource.data.get(0));
//
//                            }
//                            editor.putBoolean("isDay", lightMode);
//                            editor.commit();
//                        }
//                        break;
//                        case ERROR: {
//                            bigLoading.setVisibility(View.GONE);
//
//                            if (getIntent().hasExtra(PLACES_LIST)) {
//                                places = getIntent().getParcelableExtra(PLACES_LIST);
//                                visibility(places);
//                            }
//                            else if(isClicked){
//                                isClicked = false;
//                            }
//                            if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE)) {
//                                places = getIntent().getParcelableExtra(MAIN_SEARCH_LATITUDE);
//                                visibility(places);
//                            }
//                            //Toast.makeText(MapsActivity.this, "please check network connection", Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "onChanged: there was an error retrieving weather " + listResource.message);
//                        }
//                    }
//                }
//            }
//        });
//    }
//    private void setWeatherCurrent(double latitude, double longitude){
//
//        WeatherRepository.getWeatherRepository().getClimate(latitude+","+longitude)
//                .enqueue(new Callback<Climate>() {
//            @Override
//            public void onResponse(Call<Climate> call, Response<Climate> response) {
//                if (response.isSuccessful()){
//                    volleyViewModel.connector.loading.setValue(false);
//                    if (response.body() != null){
//                        Climate climate = response.body();
//                        MapsActivity.this.climate = climate;
//                        Log.d(TAG, "onChanged: current weather was live here kldjflkdsjfkljdf "+climate.getCurrent().getTemp());
//                        // gettingMoment(listResource.data.get(0));
//                        Log.d(TAG, "onChanged: checking light mode " + lightMode);
//                        //  Log.d(TAG, "onChanged: weather was retrieve successful " + listResource.data.get(0).getCityName());
//                        LatLng position = new LatLng(latitude, longitude);
//                        if (getIntent().hasExtra(PLACES_LIST)) {
//                            if (imageEdit.getVisibility() == View.VISIBLE) {
//                                isAllowed = false;
//                                Log.d(TAG, "onChanged: dammm");
//                                settingProperties(climate);
//                            } else {
//                                Log.d(TAG, "onChanged: whaat");
//                                relativeWeather.setVisibility(View.GONE);
//                                drawMaker(position);
//                            }
//                        }
//                        if (getIntent().hasExtra(ADDING_FAVOURITE)) {
//                            if (isClicked) {
//                                settingProperties(climate);
//                            } else {
//
//                                drawMaker(position);
//                            }
//                        }
//                        else if (current){
//                            settingProperties(climate);
//                            //  gettingMoment(listResource.data.get(0));
//
//                        }
//                        else if (getIntent().hasExtra(FIRST_ADDRESS) || getIntent().hasExtra(SECOND_ADDRESS))
//                        {
//                            drawMaker(position);
//                        }
//                        else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
//                            settingProperties(climate);
//                            //gettingMoment(listResource.data.get(0));
//                        }
//                        else {
//                            Log.d(TAG, "onChanged: dammm1");
//                            settingProperties(climate);
//                            drawMaker(position);
//                            // gettingMoment(listResource.data.get(0));
//
//                        }
//                        editor.putBoolean("isDay", lightMode);
//                        editor.commit();
//                    }
//                    else{
//                        Log.d(TAG, "onResponse: response body was null ");
//                        Toast.makeText(MapsActivity.this,getString(R.string.check_network),Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else{
//                    volleyViewModel.connector.loading.setValue(false);
//                    Log.d(TAG, "onResponse: climate response was not successful");
//                    Toast.makeText(MapsActivity.this,getString(R.string.check_network),Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Climate> call, Throwable t) {
//                Log.d(TAG, "onFailure: climate response failed "+t.getMessage());
//                String network ="";
//                network = String.valueOf(R.string.networkConnection);
//                try {
//                    //Toast.makeText(mContext, network, Toast.LENGTH_SHORT).show();
//                }
//                catch (Exception e){
//                    Log.d(TAG, "onFailure: toast exception "+e.getMessage());
//                }
//
//            }
//        });
//
//    }
    private void setWeatherCurrent(double latitude, double longitude) {
        volleyViewModel.connector.loading.setValue(true);
        mainViewModel.gettingClimateInfo(latitude + "," + longitude).observe(this, new Observer<Climate>() {
            @Override
            public void onChanged(Climate climate) {
                mainViewModel.setConnection(false);
                if (climate.getCurrent().getIs_day() == 1) {
                    lightMode = true;
                }
                else {
                    lightMode = true;
                }
                MapsActivity.this.climate = climate;
                volleyViewModel.connector.loading.setValue(false);
                Log.d(TAG, "onChanged: checking light mode " + lightMode);
                LatLng position = new LatLng(latitude, longitude);
                if (getIntent().hasExtra(PLACES_LIST)) {
                    if (imageEdit.getVisibility() == View.VISIBLE) {
                        bottomLayout.setVisibility(View.VISIBLE);
                        relativeWeather.setAlpha(0.8f);
                        isAllowed = false;
                        Log.d(TAG, "onChanged: dammm");
                        settingProperties(climate);
                    } else {
                        Log.d(TAG, "onChanged: whaat");
                        relativeWeather.setVisibility(View.GONE);
                        if (allowingMaker){
                            drawMaker(position);
                        }
                        else{
                            allowingMaker = true;
                        }
                    }
                }
                if (getIntent().hasExtra(ADDING_FAVOURITE)) {
                    if (isClicked) {
                        settingProperties(climate);
                    }
                    else {

                        if (allowingMaker){
                            drawMaker(position);
                        }
                        else{
                            allowingMaker = true;
                        }
                    }
                }
                else if (current) {
                    settingProperties(climate);
                    //  gettingMoment(listResource.data.get(0));

                }
                else if (getIntent().hasExtra(FIRST_ADDRESS) ||
                        getIntent().hasExtra(SECOND_ADDRESS) ) {
                    if (allowingMaker){
                        drawMaker(position);
                    }
                    else{
                        allowingMaker = true;
                    }
                }
                else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                    settingProperties(climate);
                    //gettingMoment(listResource.data.get(0));
                }
                else {
                    Log.d(TAG, "onChanged: dammm1");
                    settingProperties(climate);
                    if (allowingMaker){
                        drawMaker(position);
                    }
                    else{
                        allowingMaker = true;
                    }
                    // gettingMoment(listResource.data.get(0));

                }
            }
        });
    }

    public static Single<Boolean> hasInternetConnection() {
        return io.reactivex.rxjava3.core.Single.fromCallable(() -> {
                    try {
                        // Connect to Google DNS to check for connection
                        int timeoutMs = 1500;
                        Socket socket = new Socket();
                        InetSocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

                        socket.connect(socketAddress, timeoutMs);
                        socket.close();

                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void onClick() {
        relativeWeather.setOnClickListener(this);
        searchNext.setOnClickListener(this);
        imageEdit.setOnClickListener(this);
        navMode.setOnClickListener(this);
        navCategories.setOnClickListener(this);
        navSpeakerOn.setOnClickListener(this);
        navSpeakerOff.setOnClickListener(this);
        car.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        motor.setOnClickListener(this);
        bus.setOnClickListener(this);
        bike.setOnClickListener(this);
        walk.setOnClickListener(this);
        layoutStart.setOnClickListener(this);
    }

    private void gettingCurrentData(double latitude, double longitude) {
        mainViewModel.getCurrentCoordinates(latitude, longitude).observe(this, new Observer<Resource<List<Weather>>>() {
            @Override
            public void onChanged(Resource<List<Weather>> listResource) {
                if (listResource.data != null) {
                    String network = "";
                    network = String.valueOf(R.string.networkConnection);
                    switch (listResource.status) {

                        case ERROR: {
                            currentViewModel.currentInsertMode.setValue(CurrentViewModel.CurrentInsertMode.ERROR);
                            Log.d(TAG, "onChanged: there was an error getting current weather" + " " + listResource.message);
                        }
                        break;
                        case SUCCESS: {
                            editor.putString("cityName", listResource.data.get(0).getCityName());
                            editor.commit();
                            insertCurrent(listResource.data.get(0));
                        }
                        break;
                    }
                } else {
                    Log.d(TAG, "onChanged: current was null");
                }
            }
        });
    }

    private void gettingDailyData(double latitude, double longitude) {
        mainViewModel.getDailyCoordinates(latitude, longitude).observe(this, new Observer<Resource<List<Weather>>>() {
            @Override
            public void onChanged(Resource<List<Weather>> listResource) {
                if (listResource.data != null) {
                    switch (listResource.status) {
                        case ERROR: {
                            dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.ERROR);
                            Log.d(TAG, "onChanged: there was an error getting daily weather" + " " + listResource.message);
                        }
                        break;
                        case SUCCESS: {
                            Log.d(TAG, "onChanged: daily weather was retrieve " + listResource.data.get(0).getMin_temp());
                            retrievingDaily(listResource.data);
                        }
                        break;
                    }
                }
            }
        });
    }

    private void gettingHourlyData(double latitude, double longitude) {
        mainViewModel.getHourlyCoordinates(latitude, longitude).observe(this, new Observer<Resource<List<Weather>>>() {
            @Override
            public void onChanged(Resource<List<Weather>> listResource) {
                if (listResource.data != null) {
                    switch (listResource.status) {
                        case ERROR: {
                            hourlyViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.ERROR);
                            Log.d(TAG, "onChanged: there was an error getting hourly weather" + " " + listResource.message);
                        }
                        break;
                        case SUCCESS: {
                            editor.putBoolean("hourly", true);
                            editor.commit();
                            retrievingHourly(listResource.data);
                            Log.d(TAG, "onChanged: hourly weather was retrieve " + listResource.data.size());
                        }
                        break;
                    }
                }
            }
        });
    }
//    private void getCoordinates() {
//        final String name1 = "mumbai+india";
//        coordinateViewModel.getCoordinates(name1).observe(this, new Observer<Resource<List<List<Coordinate>>>>() {
//            @Override
//            public void onChanged(Resource<List<List<Coordinate>>> listResource) {
//                if (listResource != null) {
//                    switch (listResource.status) {
//                        case ERROR: {
//                            Log.d(TAG, "onChanged: there was an error gettimg coordinates " + listResource.message);
//                        }
//                        break;
//                        case SUCCESS: {
//
//
//                            // code ll be done here
//                            if (listResource.data != null) {
//                                List<List<Double>> coordinates = new ArrayList<>();
//                                Log.d(TAG, "onChanged: there was success gettimg coordinates " + listResource.data);
//
//                                Log.d(TAG, "onChanged: there was success gettimg coordinates " + listResource.data.size());
//                                //   if (listResource.data.size() > 7){
//                                Log.d(TAG, "onChanged: there was success gettimg coordinates " + listResource.data.get(0).get(0).getGeojson().get(0).getCoordinates().size());
//                                PolylineOptions polylineOptions = new PolylineOptions().
//                                        geodesic(true).
//                                        color(Color.BLUE).
//                                        width(5);
//                                coordinates = listResource.data.get(0).get(0).getGeojson().get(0).getCoordinates();
//                                if(coordinates.size() > 2){
//                                for (int i = 0; i < listResource.data.size(); i++) {
//                                    double lat = coordinates.get(i).get(0);
//                                    double lon = coordinates.get(i).get(1);
//
//                                    polylineOptions.add(new LatLng(lat, lon));
//
//                                    mMap.addPolyline(polylineOptions);
//                                }
//
//                                  }
////                                else {
////
////                                    LatLng location = new LatLng(latitude, longitude);
////                                    mMap.addMarker(new MarkerOptions().position(location).title("mumbai")
////                                            .draggable(true));
////                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
////                                }
//                            }
//                            else {
//                                Log.d(TAG, "onChanged: geoJson was null");
//                            }
//                        }
//                    }
//                }
//            }
//        });
//    }

    private void activateTransition() {
        List<ActivityTransition> transitionsList = new ArrayList<>();

        transitionsList.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.IN_VEHICLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        transitionsList.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.IN_VEHICLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build()
        );

        finalRequest = new ActivityTransitionRequest(transitionsList);

        Intent intent = new Intent(RECEIVER_ACTION);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);


        DetectedActivityReceiver receiver = new DetectedActivityReceiver();

        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver, new IntentFilter(RECEIVER_ACTION)
        );
    }



    public void checkPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                MapsActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            MapsActivity.this,
                            new String[]{permission},
                            requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                try {
                    //Toast.makeText(MapsActivity.this,getString(R.string.need_to_allow_location), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.d(TAG, "onRequestPermissionsResult: lets see "+e.toString());
                }
              //  checkPermission(REQUIRED_SDK_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
//
            }
        }
    }

    private void getLocation() {
        Log.d(TAG, "onSuccess: location was detected ready to start");
        if (isLocationNeeded) {
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                client.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    longitude = location.getLongitude();
                                    latitude = location.getLatitude();


                                    Log.d(TAG, "onSuccess: entered");
                                    currentLocation = new LatLng(latitude, longitude);
                                    if (getIntent().hasExtra(PLACES_LIST)) {
                                        relativeWeather.setVisibility(View.GONE);
                                        saveButton.setVisibility(View.VISIBLE);
                                        drawMaker(currentLocation);

                                    }
                                    else if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE )|| getIntent().hasExtra(NEW_GUY_LATITUDE)) {
                                        if (places != null) {
                                            LatLng destination = new LatLng(places.getLatitude(), places.getLongitude());

                                            ori = currentLocation;
                                            dest = destination;
                                            String start = ori.longitude+","+ori.latitude;
                                            String end = dest.longitude+","+dest.latitude;
                                            drawRoute(DRIVING, start, end);
                                            subLocal.setText(places.getCityName());

                                        }
                                    }
                                    else if (getIntent().hasExtra(CHECK_RESULTS) || getIntent().hasExtra(MAIN_SEARCH_LATITUDE) ||
                                            getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                                        Log.d(TAG, "onSuccess: checking");
                                        if (places != null) {
                                            LatLng destination = new LatLng(places.getLatitude(), places.getLongitude());

                                            ori = currentLocation;
                                            dest = destination;
                                            String start = ori.longitude+","+ori.latitude;
                                            String end = dest.longitude+","+dest.latitude;
                                            drawRoute(DRIVING, start, end);
                                            subLocal.setText(places.getCityName());
                                        }
                                    }
                                    else if (getIntent().hasExtra(SCHEDULE_U_I)){
                                        schedule = getIntent().getParcelableExtra(SCHEDULE_U_I);
                                        if (schedule != null) {
                                            LatLng destination = new LatLng(schedule.getFinalLatitudeValue(), schedule.getFinalLongitudeValue());

                                            ori = new LatLng(schedule.getFromLatitudeValue(), schedule.getFromLongitudeValue());
                                            dest = destination;
                                            String start = ori.longitude+","+ori.latitude;
                                            String end = dest.longitude+","+dest.latitude;
                                            drawRoute(DRIVING, start, end);
                                            }
                                        }
                                    else {
                                        Log.d(TAG, "onSuccess: location no string attached");
                                        setWeatherCurrent(latitude, longitude);
                                    }
                                }
                                else {
                                    volleyViewModel.connector.loading.setValue(false);
                                    Log.d(TAG, "onSuccess: location was null");
                                }
                            }
                        });
            }
            else{
                volleyViewModel.connector.loading.setValue(false);
            }
        }

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.work_pin);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void drawMaker(LatLng position) {
        isAllowed = true;
        Log.d(TAG, "drawMaker: maker was drawn");
        MarkerOptions markerEdit = new MarkerOptions().position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.pin)));
        if (getIntent().hasExtra(PLACES_LIST)) {
            places = getIntent().getParcelableExtra(PLACES_LIST);
            if (places != null && places.getId() == 1) {
                markerEdit.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.home_pin)));
            } else if (places.getId() == 2) {
                markerEdit.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.cate_pin)));
            } else {
                markerEdit.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.cate_pin)));
            }
        }

        MarkerOptions markerOptions = new MarkerOptions().position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.pin)));
        currentMaker = mMap.addMarker(markerOptions);

        if (getIntent().hasExtra(PLACES_LIST) || getIntent().hasExtra(ADDING_FAVOURITE) ||
                getIntent().hasExtra(FIRST_ADDRESS) || getIntent().hasExtra(SECOND_ADDRESS)
                || getIntent().hasExtra(MAP_NAVIGATION_SEARCH) || getIntent().hasExtra(EMPTY_NAVIGATION_MAP)
                || getIntent().hasExtra(EMPTY_SEARCH_MAP)|| getIntent().hasExtra(EMPTY_SEARCH)) {
            if (imageEdit.getVisibility() == View.GONE) {
                if (isAllowed) {
                    Log.d(TAG, "drawMaker: marker should move");
                    if (currentMaker != null) {
                        mMap.clear();
                    }

                    mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                        @Override
                        public void onCameraMove() {
                            if (imageEdit.getVisibility() == View.GONE) {
                                if (isAllowed) {
                                    if (currentMaker != null) {
                                        mMap.clear();
                                    }

                                    LatLng midLatLng = mMap.getCameraPosition().target;
                                    currentMaker = mMap.addMarker(markerEdit.position(midLatLng));
                                    Log.d(TAG, "onCameraMove: checking new maker latitude " + currentMaker.getPosition().latitude);
                                    Log.d(TAG, "onCameraMove: checking new maker longitude " + currentMaker.getPosition().longitude);
                                }
                            }
                        }
                    });
                    mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                        @Override
                        public void onCameraIdle() {
                            if (imageEdit.getVisibility() == View.GONE) {
                                if (isAllowed) {
                                    if (currentMaker != null) {
                                        mMap.clear();
                                    }

                                    LatLng midLatLng = mMap.getCameraPosition().target;
                                    currentMaker = mMap.addMarker(markerEdit.position(midLatLng));
                                    Log.d(TAG, "onCameraIdle: checking new maker latitude " + currentMaker.getPosition().latitude);
                                    Log.d(TAG, "onCameraIdle: checking new maker longitude " + currentMaker.getPosition().longitude);
                                }
                            }
                        }
                    });
                }
            }
        }

//                if (isAllowed) {
//                    if (currentMaker != null) {
//                        places.setLatitude(currentMaker.getPosition().latitude);
//                        places.setLongitude(currentMaker.getPosition().longitude);
//                    }
        //updatingWeather(places);
    }

    private void gettingCallback() {
        request = LocationRequest.create();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        int permission1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission1 == PackageManager.PERMISSION_GRANTED) {

            callback = new LocationCallback() {
                @Override
                public void onLocationResult(@io.reactivex.annotations.NonNull LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    // Use the location object to get Latitude and Longitude and then update your text view.
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();

                    currentLocation = new LatLng(latitude, longitude);

                }
            };
        } else {
            checkPermission(REQUIRED_SDK_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            //gettingCallback();
        }
    }

    private void checkLocationUpdate() {
        LocationSettingsRequest builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request)
                .build();

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> responseTask = client.checkLocationSettings(builder);

        responseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d(TAG, "onSuccess: gps is enabled");

            }
        });
        responseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;

                    try {
                        apiException.startResolutionForResult(MapsActivity.this, 26);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
        });
    }

    private void stopLocation() {
        if (client != null && callback != null) {
            client.removeLocationUpdates(callback);
        }
    }

    @Override
    protected void onPause() {
        if (currentMaker != null) {
            currentMaker.remove();
        }
        super.onPause();
        Log.d(TAG, "onPause: onPaused is called");
        if (currentLocation != null) {
            Log.d(TAG, "onPause:  location is not null");
            stopLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("police", "nothing");
        Log.d(TAG, "onCreate: lets see what happened "+name);
        if (!name.trim().equals("nothing")){
            bigLoading.setVisibility(View.VISIBLE);
            Log.d(TAG, "onResume: entered");
            String location = latSource+","+longSource;
            volleyViewModel.traffic(location, name);
            preferences.edit().clear().apply();
            new CameraPosition(new LatLng(latSource, longSource),12.0f,0.0f,0.0f);
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latSource, longSource)));

        }
        setPlaces();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: on stop is called");
        if (currentLocation != null) {
            stopLocation();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // checkLocationUpdate();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: activity have been destroyed");
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        checkLocationUpdate();
        getLocationUpdate();
        observePolyline();
        alMightyFunction();
        getLocation();

        observeMapState();
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setPadding(0, 150, 0, 0);

        onClick();
//            MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.silver);
//            mMap.setMapStyle(mapStyleOptions);

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            // Show rationale and request permission.
//        }
        CameraPosition cameraPosition = mMap.getCameraPosition();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (wrapLayout.getVisibility() == View.VISIBLE && nav.getVisibility() == View.VISIBLE) {
                    slideDown(wrapLayout);
                    relativeWeather.setAlpha(0.7f);

                }
            }
        });
//        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
//            @Override
//            public void onCameraMove() {
//                Log.d(TAG, "onCameraMove: camera is move "+cameraPosition.zoom);
//                CameraPosition cameraPosition1 = mMap.getCameraPosition();
//                if (cameraPosition.zoom  != cameraPosition1.zoom){
//                    Log.d(TAG, "onCameraMove: camera position "+cameraPosition1.zoom);
//
//                    slideDown(wrapLayout);
//                }
//            }
//        });
    }

    private void homeMarker(LatLng homeMarker) {
        Log.d(TAG, "addCustomMarker()");
        if (mMap == null) {
            return;
        }

        // adding a marker on map with image from  drawable
        homeMaker = mMap.addMarker(new MarkerOptions()
                .position(homeMarker)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.home_pin))));
        if (getIntent().hasExtra(PLACES_LIST)) {
            if (imageEdit.getVisibility() == View.GONE) {
                homeMaker.remove();
            }
        }
    }

    private void workMarker(LatLng workMarker) {
        Log.d(TAG, "addCustomMarker()");
        if (mMap == null) {
            return;
        }

        // adding a marker on map with image from  drawable
        workMaker = mMap.addMarker(new MarkerOptions()
                .position(workMarker)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.work_pin))));
        if (getIntent().hasExtra(PLACES_LIST)) {
            if (imageEdit.getVisibility() == View.GONE) {
                workMaker.remove();
            }
        }
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }


    @Override
    public void onPoiClick(PointOfInterest pointOfInterest) {
//        Toast.makeText(this, "clicked" + pointOfInterest.name + " "
//                        + pointOfInterest.placeId + "/n" + pointOfInterest.latLng.latitude + pointOfInterest.latLng.longitude,
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_search: {
                Intent searchIntent = new Intent(MapsActivity.this, SearchActivity.class);
                searchIntent.putExtra(MAIN_SEARCH, "");
                startActivity(searchIntent);
//                LinearLayout layout = findViewById(R.id.wrap_layout);
//                slideDown(layout);
            }
            break;
            case R.id.weather_relative_next: {
                if (places != null) {
                    bigLoading.setVisibility(View.VISIBLE);
                    latitude = places.getLatitude();
                    longitude = places.getLongitude();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MapsActivity.this);
                    String name = preferences.getString("cityName", "true");
                    Log.d(TAG, "onClick: checking cityName "+name);
                   // if (cityName.getText().toString().equals(name)) {
//                        gettingCurrentData(places.getLatitude(), places.getLongitude());
//                        gettingDailyData(places.getLatitude(), places.getLongitude());
//                        gettingHourlyData(places.getLatitude(), places.getLongitude());
//                    }
//                    else{
//                        currentViewModel.currentInsertMode.setValue(CurrentViewModel.CurrentInsertMode.AVAILABLE);
//                        dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.AVAILABLE);
//                        hourlyViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.AVAILABLE);
//                    }
                }
                else {
                    latitude = 0.0;
                    longitude = 0.0;
                    Log.d(TAG, "onClick: places was null");
                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        bigLoading.setVisibility(View.GONE);
                        Intent weatherIntent = new Intent(MapsActivity.this, MainActivity.class);
                        weatherIntent.putExtra("let there be light",climate);
                        startActivity(weatherIntent);
//                    }
//                },15000);

            }
            break;
            case R.id.nav_mode: {
                LinearLayout layout = findViewById(R.id.navigate_mode);
                coordinateViewModel.slideUp(layout);
            }
            break;
            case R.id.image_edit: {
                if (currentMaker != null) {
                    currentMaker.remove();
                }
                if (homeMaker != null) {
                    Log.d(TAG, "onClick: home was not null");
                    mMap.clear();
                } else {
                    Log.d(TAG, "onClick: home was  null");

                }
                if (workMaker != null) {
                    Log.d(TAG, "onClick: work was not null");
                    mMap.clear();
                }

                if (getIntent().hasExtra(PLACES_LIST)) {
                    places = getIntent().getParcelableExtra(PLACES_LIST);
                    saveButton.setVisibility(View.VISIBLE);
                    imageEdit.setVisibility(View.GONE);
                    relativeWeather.setVisibility(View.GONE);
                    drawMaker(new LatLng(places.getLatitude(),places.getLongitude()));
                } else {
                    isEditClicked = true;
                    isLocationNeeded = true;
                    getLocation();
                }
            }
            break;
            case R.id.nav_categories: {
                Intent intent = new Intent(MapsActivity.this, CategoriesActivity.class);
                intent.putExtra(LATITUDE_CATE_SUB, latSource);
                intent.putExtra(LONGITUDE_CATE_SUB, longSource);
                startForResult.launch(intent);
            }
            break;
            case R.id.navi_speaker_on: {
                if (navSpeakerOn.getVisibility() == View.VISIBLE) {
                    navSpeakerOn.setVisibility(View.GONE);
                    navSpeakerOff.setVisibility(View.VISIBLE);
                }
            }
            break;
            case R.id.navi_speaker_off: {
                if (navSpeakerOff.getVisibility() == View.VISIBLE) {
                    navSpeakerOff.setVisibility(View.GONE);
                    navSpeakerOn.setVisibility(View.VISIBLE);
                }
            }
            break;
            case R.id.save_button: {
              //  volleyViewModel.connector.loading.setValue(true);
                isAllowed = false;
                isClicked = true;
                if (homeMaker != null) {
                    homeMaker.remove();
                }
                if (workMaker != null) {
                    workMaker.remove();
                }

                if (getIntent().hasExtra(PLACES_LIST)) {
                    isAllowed = false;
                    saveButton.setVisibility(View.GONE);
                    imageEdit.setVisibility(View.VISIBLE);
                    places = getIntent().getParcelableExtra(PLACES_LIST);
                    if (places != null && places.getCityName() != null) {
                        relativeWeather.setVisibility(View.VISIBLE);
                    }
                    if (currentMaker != null) {
                        LatLng position = new LatLng(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude);
                        setWeatherCurrent(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude);
                        places.setLatitude(position.latitude);
                        places.setLongitude(position.longitude);
                        String coordinates = currentMaker.getPosition().latitude + "," + currentMaker.getPosition().longitude;
                        setReverseGeocode(coordinates,null);
                        updatingWeather(places);
                        currentMaker.remove();
                        if (currentMaker.isVisible()){
                            currentMaker.setVisible(false);
                        }
                    }
                    volleyViewModel.connector.loading.setValue(false);

                }
                else if (getIntent().hasExtra(ADDING_FAVOURITE)) {
                    if (currentMaker != null) {
                        isClicked = true;
                        places = getIntent().getParcelableExtra(ADDING_FAVOURITE);
                        places.setLatitude(currentMaker.getPosition().latitude);
                        places.setLongitude(currentMaker.getPosition().longitude);
                        String coordinates = currentMaker.getPosition().latitude + "," + currentMaker.getPosition().longitude;
                        setReverseGeocode(coordinates, null);
                    } else {
                        Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                        startActivity(favouriteIntent);
                        Toast.makeText(this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    //bigLoading.setVisibility(View.GONE);
                    volleyViewModel.connector.loading.setValue(false);
                }
                else if (getIntent().hasExtra(EMPTY_SEARCH)) {
                   // bigLoading.setVisibility(View.VISIBLE);
                    volleyViewModel.connector.loading.setValue(true);

                    places = getIntent().getParcelableExtra(EMPTY_SEARCH);
                    if (currentMaker != null) {
                        isClicked = true;

                        if (places == null) {
                            places = new Places();
                        }
                        places.setLatitude(currentMaker.getPosition().latitude);
                        places.setLongitude(currentMaker.getPosition().longitude);
                        String coordinates = currentMaker.getPosition().latitude + "," + currentMaker.getPosition().longitude;
                        setReverseGeocode(coordinates, null);

                    } else {
                        Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                        startActivity(favouriteIntent);
                        finish();
                        Toast.makeText(this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    }
                    bigLoading.setVisibility(View.GONE);
                    volleyViewModel.connector.loading.setValue(false);

                }
                else if (getIntent().hasExtra(EMPTY_NAVIGATION_MAP)) {
                    if (currentMaker != null) {
                        isClicked = true;

                        Places places = new Places();
                        places.setLatitude(currentMaker.getPosition().latitude);
                        places.setLongitude(currentMaker.getPosition().longitude);

                        Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                        favouriteIntent.putExtra(EMPTY_NAVIGATION_MAP, places);
                        startActivity(favouriteIntent);
                    } else {
                        Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                        startActivity(favouriteIntent);
                        Toast.makeText(this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    }
                    bigLoading.setVisibility(View.GONE);
                    finish();
                }
                else if (getIntent().hasExtra(FIRST_ADDRESS)) {
                    if (currentMaker != null) {
                        //    Log.d(TAG, "onClick: checking locality_sub "+getMyCItyName(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude));
                        LatLng position = currentMaker.getPosition();
                        schedule = getIntent().getParcelableExtra(FIRST_ADDRESS);
                        if (schedule == null) {
                            schedule = new Schedule();
                        }
                        String coordinates = position.latitude + "," + position.longitude;
                        schedule.setFromLatitudeValue(position.latitude);
                        schedule.setFromLongitudeValue(position.longitude);
                        setReverseGeocode(coordinates, schedule);

                    } else {
                        //bigLoading.setVisibility(View.GONE);
                        volleyViewModel.connector.loading.setValue(false);
                        Intent intent = new Intent(MapsActivity.this, PlanSettingActivity.class);
                        intent.putExtra(FIRST_ADDRESS_LATITUDE, schedule);
                        startActivity(intent);
                        finish();
                    }
                }
                else if (getIntent().hasExtra(SECOND_ADDRESS)) {
                    if (currentMaker != null) {
                        LatLng position = currentMaker.getPosition();
                        schedule = getIntent().getParcelableExtra(SECOND_ADDRESS);
                        if (schedule == null) {
                            schedule = new Schedule();
                        }

                        String coordinates = position.latitude + "," + position.longitude;
                        schedule.setFinalLatitudeValue(position.latitude);
                        schedule.setFinalLongitudeValue(position.longitude);
                        setReverseGeocode(coordinates, schedule);
                    } else {
                        volleyViewModel.connector.loading.setValue(false);
                       // bigLoading.setVisibility(View.GONE);
                        Intent intent = new Intent(MapsActivity.this, PlanSettingActivity.class);
                        intent.putExtra(SECOND_ADDRESS_LATITUDE, schedule);
                        startActivity(intent);
                        finish();
                    }
                }
                else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                   // volleyViewModel.setChoose(true);
                    if (currentMaker != null) {
                        Log.d(TAG, "onClick: it was not null");
                        Intent output = new Intent();
                        output.putExtra(LATITUDE_CATE_SUB, currentMaker.getPosition().latitude);
                        output.putExtra(LONGITUDE_CATE_SUB, currentMaker.getPosition().longitude);
                        setResult(RESULT_OK, output);
                        finish();
                    }
                }
                else {
                   // bigLoading.setVisibility(View.GONE);
                    volleyViewModel.connector.loading.setValue(true);
                    Log.d(TAG, "onClick: there was a problem");
                }
                volleyViewModel.connector.loading.setValue(false);
            }
            break;
            case R.id.choosing_car: {
                volleyViewModel.connector.loading.setValue(true);
                LinearLayout layout = findViewById(R.id.navigate_mode);
                coordinateViewModel.slideDown1(layout);
                if (!travelMode.trim().equals("DRIVING")) {
                    volleyViewModel.connector.liveOptions.setValue(null);
                    travelForm = "car";
                    if (getIntent().hasExtra(SCHEDULE_U_I)) {
                        schedule = getIntent().getParcelableExtra(SCHEDULE_U_I);
                        if (schedule != null) {
                            LatLng origin = new LatLng(schedule.getFromLatitudeValue(), schedule.getFromLongitudeValue());
                            LatLng dest = new LatLng(schedule.getFinalLatitudeValue(), schedule.getFinalLongitudeValue());
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(DRIVING, start, end);
                        }
                    }
                    else if (fromCate){
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngCate.latitude, latLngCate.longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if (getIntent().hasExtra(CHECK_RESULTS)) {
                        places = getIntent().getParcelableExtra(CHECK_RESULTS);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE)) {
                        places = getIntent().getParcelableExtra(MAIN_SEARCH_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if(getIntent().hasExtra(NEW_GUY_LATITUDE)){
                        List<LatLng> latLngs = (List<LatLng>) getIntent().getSerializableExtra(NEW_GUY_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngs.get(0).latitude, latLngs.get(0).longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                        LatLng origin = new LatLng(latSource, longSource);
                        if (currentMaker != null) {
                            LatLng dest = new LatLng(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude);
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(DRIVING, start, end);
                        }
                    }
                }
                else {
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.car);
                    navMode.setPadding(1, 1, 1, 1);
                    navMode.setImageDrawable(drawable);
                    volleyViewModel.connector.loading.setValue(false);
                }

            }
            break;
            case R.id.choosing_Bus: {
                volleyViewModel.connector.loading.setValue(true);
                LinearLayout layout = findViewById(R.id.navigate_mode);
                coordinateViewModel.slideDown1(layout);
                if (!travelMode.trim().equals("DRIVING")) {
                    volleyViewModel.connector.liveOptions.setValue(null);
                    travelForm = "bus";
                    if (getIntent().hasExtra(SCHEDULE_U_I)) {
                        schedule = getIntent().getParcelableExtra(SCHEDULE_U_I);
                        if (schedule != null) {
                            LatLng origin = new LatLng(schedule.getFromLatitudeValue(), schedule.getFromLongitudeValue());
                            LatLng dest = new LatLng(schedule.getFinalLatitudeValue(), schedule.getFinalLongitudeValue());
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(DRIVING, start, end);
                        }
                    }
                    else if (getIntent().hasExtra(CHECK_RESULTS)) {
                        places = getIntent().getParcelableExtra(CHECK_RESULTS);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if (fromCate){
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngCate.latitude, latLngCate.longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if(getIntent().hasExtra(NEW_GUY_LATITUDE)){
                        List<LatLng> latLngs = (List<LatLng>) getIntent().getSerializableExtra(NEW_GUY_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngs.get(0).latitude, latLngs.get(0).longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE)) {
                        places = getIntent().getParcelableExtra(MAIN_SEARCH_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                        LatLng origin = new LatLng(latSource, longSource);
                        if (currentMaker != null) {
                            LatLng dest = new LatLng(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude);
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(DRIVING, start, end);
                        }
                    }
                }
                else{
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.bus);
                    navMode.setPadding(1, 1, 1, 1);
                    navMode.setImageDrawable(drawable);
                    volleyViewModel.connector.loading.setValue(false);
                }
            }
            break;
            case R.id.choosing_bike: {
                volleyViewModel.connector.loading.setValue(true);
                LinearLayout layout = findViewById(R.id.navigate_mode);
                coordinateViewModel.slideDown1(layout);
                if (!travelMode.trim().equals("BICYCLING")) {
                    volleyViewModel.connector.liveOptions.setValue(null);
                    travelForm = "bike";
                    if (getIntent().hasExtra(SCHEDULE_U_I)) {
                        schedule = getIntent().getParcelableExtra(SCHEDULE_U_I);
                        if (schedule != null) {
                            LatLng origin = new LatLng(schedule.getFromLatitudeValue(), schedule.getFromLongitudeValue());
                            LatLng dest = new LatLng(schedule.getFinalLatitudeValue(), schedule.getFinalLongitudeValue());
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(CYCLING, start, end);
                        }
                    }
                    else if (fromCate){
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngCate.latitude, latLngCate.longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(CYCLING, start, end);
                    }
                    else if(getIntent().hasExtra(NEW_GUY_LATITUDE)){
                        List<LatLng> latLngs = (List<LatLng>) getIntent().getSerializableExtra(NEW_GUY_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngs.get(0).latitude, latLngs.get(0).longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(CYCLING, start, end);
                    }
                    else if (getIntent().hasExtra(CHECK_RESULTS)) {
                        places = getIntent().getParcelableExtra(CHECK_RESULTS);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(CYCLING, start, end);
                    }
                    else if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE)) {
                        places = getIntent().getParcelableExtra(MAIN_SEARCH_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(DRIVING, start, end);
                    }
                    else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                        LatLng origin = new LatLng(latSource, longSource);
                        if (currentMaker != null) {
                            LatLng dest = new LatLng(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude);
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(CYCLING, start, end);
                        }
                    }

                }
                else{
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.bike);
                    navMode.setPadding(18, 18, 18, 18);
                    navMode.setImageDrawable(drawable);
                    volleyViewModel.connector.loading.setValue(false);
                }

            }
            break;
            case R.id.choosing_motor: {
                volleyViewModel.connector.loading.setValue(true);
                LinearLayout layout = findViewById(R.id.navigate_mode);
                coordinateViewModel.slideDown1(layout);
                if (!travelMode.trim().equals("DRIVING")) {
                    volleyViewModel.connector.liveOptions.setValue(null);
                    travelForm ="motor";
                    if (getIntent().hasExtra(SCHEDULE_U_I)) {
                        schedule = getIntent().getParcelableExtra(SCHEDULE_U_I);
                        if (schedule != null) {
                            LatLng origin = new LatLng(schedule.getFromLatitudeValue(), schedule.getFromLongitudeValue());
                            LatLng dest = new LatLng(schedule.getFinalLatitudeValue(), schedule.getFinalLongitudeValue());
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(WHEEL_CHAIR, start, end);
                        }
                    }
                    else if (fromCate){
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngCate.latitude, latLngCate.longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(WHEEL_CHAIR, start, end);
                    }
                    else if(getIntent().hasExtra(NEW_GUY_LATITUDE)){
                        List<LatLng> latLngs = (List<LatLng>) getIntent().getSerializableExtra(NEW_GUY_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngs.get(0).latitude, latLngs.get(0).longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(WHEEL_CHAIR, start, end);
                    }
                    else if (getIntent().hasExtra(CHECK_RESULTS)) {
                        places = getIntent().getParcelableExtra(CHECK_RESULTS);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(WHEEL_CHAIR, start, end);
                    }
                    else if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE)) {
                        places = getIntent().getParcelableExtra(MAIN_SEARCH_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(WHEEL_CHAIR, start, end);
                    }
                    else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                        LatLng origin = new LatLng(latSource, longSource);
                        if (currentMaker != null) {
                            LatLng dest = new LatLng(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude);
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(WHEEL_CHAIR, start, end);
                        }
                    }
                }
                else{
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.motor);
                    navMode.setPadding(18, 18, 18, 18);
                    navMode.setImageDrawable(drawable);
                    volleyViewModel.connector.loading.setValue(false);
                }

            }
            break;
            case R.id.choosing_walk: {
                volleyViewModel.connector.loading.setValue(true);
                LinearLayout layout = findViewById(R.id.navigate_mode);
                coordinateViewModel.slideDown1(layout);
                if (!travelMode.trim().equals("WALKING")) {
                    volleyViewModel.connector.liveOptions.setValue(null);
                    travelForm = "walk";
                    if (getIntent().hasExtra(SCHEDULE_U_I)) {
                        schedule = getIntent().getParcelableExtra(SCHEDULE_U_I);
                        if (schedule != null) {
                            LatLng origin = new LatLng(schedule.getFromLatitudeValue(), schedule.getFromLongitudeValue());
                            LatLng dest = new LatLng(schedule.getFinalLatitudeValue(), schedule.getFinalLongitudeValue());
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(WALKING, start, end);
                        }
                    }
                    else if (fromCate){
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngCate.latitude, latLngCate.longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(WALKING, start, end);
                    }
                    else if(getIntent().hasExtra(NEW_GUY_LATITUDE)){
                        List<LatLng> latLngs = (List<LatLng>) getIntent().getSerializableExtra(NEW_GUY_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(latLngs.get(0).latitude, latLngs.get(0).longitude);
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(WALKING, start, end);
                    }
                    else if (getIntent().hasExtra(CHECK_RESULTS)) {
                        places = getIntent().getParcelableExtra(CHECK_RESULTS);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(WALKING, start, end);
                    }
                    else if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE)) {
                        places = getIntent().getParcelableExtra(MAIN_SEARCH_LATITUDE);
                        LatLng origin = new LatLng(latSource, longSource);
                        LatLng dest = new LatLng(places.getLatitude(), places.getLongitude());
                        String start = origin.longitude+","+origin.latitude;
                        String end = dest.longitude+","+dest.latitude;
                        drawRoute(WALKING, start, end);
                    }
                    else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                        LatLng origin = new LatLng(latSource, longSource);
                        if (currentMaker != null) {
                            LatLng dest = new LatLng(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude);
                            String start = origin.longitude+","+origin.latitude;
                            String end = dest.longitude+","+dest.latitude;
                            drawRoute(WALKING, start, end);
                        }
                    }
                }
                else{
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.walk);
                    navMode.setPadding(22, 22, 22, 22);
                    navMode.setImageDrawable(drawable);
                    volleyViewModel.connector.loading.setValue(false);
                }
            }
            break;
            case R.id.layout_start: {
                cateBoo = true;
                if (relativeWeather.getVisibility() == View.GONE){
                    setWeatherCurrent(latSource,longSource);
                }
                volleyViewModel.connector.loading.setValue(true);
                if (fromCate && volleyViewModel.connector.liveOptions.getValue() == null){
                    String start = longSource+","+latSource;
                    String end = latLngCate.longitude+","+latLngCate.latitude;
                    drawRoute(DRIVING, start, end);
                    searchNext.setVisibility(View.GONE);
                    coordinateViewModel.slideDown1(nav);
                }
                    if (points.size() == 0) {
                        Log.d(TAG, "onClick: enterrrrrrrrrrrrrrrrrrrrrrred");
                        schedule = getIntent().getParcelableExtra(SCHEDULE_U_I);
                        isLocationNeeded = true;
                        getLocation();
                    }
                    else{
                        Log.d(TAG, "onClick: points was not null");
                    }
                    if (volleyViewModel.getMapSate().getValue() == null){
                        volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.NAVIGATE);

                    }
                    if (volleyViewModel.getMapSate().getValue() != null) {
                        volleyViewModel.connector.loading.setValue(true);
                        Log.d(TAG, "onClick: switch was not null");
                        switch (volleyViewModel.getMapSate().getValue()) {
                            case NAVIGATE: {
                                Log.d(TAG, "onClick: is it navigation "+volleyViewModel.getMapSate().getValue());

                                volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.START);
                            }
                            break;
                            case START: {
                                Log.d(TAG, "onClick: is it navigation "+volleyViewModel.getMapSate().getValue());
                                volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.RECENTER);
                            }
                            break;
                            case OVERVIEW: {
                                Log.d(TAG, "onClick: is it navigation "+volleyViewModel.getMapSate().getValue());
                                volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.RECENTER);
                            }
                            break;
                            case RECENTER: {
                                Log.d(TAG, "onClick: is it navigation "+volleyViewModel.getMapSate().getValue());
                                volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.OVERVIEW);
                            }
                            break;
                        }
                    }


                nav.setVisibility(View.GONE);
                wrapLayout.setVisibility(View.VISIBLE);
                //observePolyline();
            }
        }
    }

    void setChoose(){
        volleyViewModel.getChoose().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null){
                    if (aBoolean){
                        Log.d(TAG, "onChanged: lets see points now "+points.size());
                    Log.d(TAG, "onClick: choose from map");
                    saveButton.setVisibility(View.GONE);
                    if (currentMaker != null) {
                        Log.d(TAG, "onClick: it was not null");
                        new CameraPosition(new LatLng(currentMaker.getPosition().latitude,currentMaker.getPosition().longitude),16.0f,0.0f,0.0f);
                        setWeatherCurrent(currentMaker.getPosition().latitude, currentMaker.getPosition().longitude);
                        //currentMaker.remove();
                        volleyViewModel.setShowStart(true);
                    }
                    else {
                        Toast.makeText(MapsActivity.this, "please check network", Toast.LENGTH_SHORT).show();
                    }
                    //  bigLoading.setVisibility(View.GONE);
                    volleyViewModel.connector.loading.setValue(true);
                }
            }
            }
        });
    }
    private void setReverseGeocode(String coordinates, Schedule schedule){
        WeatherRepository.getWeatherRepository().getReverseGeocoder(coordinates).enqueue(new Callback<GO>() {
            @Override
            public void onResponse(Call<GO> call, Response<GO> response) {
                volleyViewModel.connector.loading.setValue(true);
                if (response.isSuccessful()) {
                    if (response.body() != null){
                        if (response.body().getReverseGeocode() != null) {
                            String county = response.body().getReverseGeocode().get(0).getAddress().getCounty();
                            String position = response.body().getReverseGeocode().get(0).getPosition();
                            String[] positionSeperated = position.split(",", 0);
                            List<String> going = new ArrayList<>();
                            Collections.addAll(going, positionSeperated);
                            double latitude = Double.parseDouble(going.get(0));
                            double longitude = Double.parseDouble(going.get(1));
                            String label = response.body().getReverseGeocode().get(0).getAddress().getLabel() + "," + response.body().getReverseGeocode().get(0).getAddress().getCountry();
                            subLocal.setText(response.body().getReverseGeocode().get(0).getAddress().getCounty());
                            if (getIntent().hasExtra(FIRST_ADDRESS)) {
                                schedule.setFromDestination(label);
                                Intent intent = new Intent(MapsActivity.this, PlanSettingActivity.class);
                                intent.putExtra(FIRST_ADDRESS_LATITUDE, schedule);
                                startActivity(intent);
                                finish();
                            } else if (getIntent().hasExtra(EMPTY_SEARCH)) {
                                places.setContent(label);
                                places.setCityName(county);
                                Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                                favouriteIntent.putExtra(EMPTY_SEARCH, places);
                                startActivity(favouriteIntent);
                                finish();
                            } else if (getIntent().hasExtra(SECOND_ADDRESS)) {
                                schedule.setFinalDestination(label);
                                Intent intent = new Intent(MapsActivity.this, PlanSettingActivity.class);
                                intent.putExtra(SECOND_ADDRESS_LATITUDE, schedule);
                                startActivity(intent);
                                finish();
                            } else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
                                places = new Places();
                                places.setCityName(county);
                                places.setLatitude(latitude);
                                places.setLongitude(longitude);
                                isLocationNeeded = true;
                                getLocation();
                            } else if (getIntent().hasExtra(PLACES_LIST)) {
                                places.setContent(label);
                                updatingWeather(places);
                                currentMaker.remove();
                            } else if (getIntent().hasExtra(ADDING_FAVOURITE)) {
                                places.setContent(label);
                                places.setCityName(county);
                                Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                                favouriteIntent.putExtra(FORWARD_LATITUDE, places);
                                startActivity(favouriteIntent);
                                finish();
                            }
                        }
                        else{
                            volleyViewModel.connector.loading.setValue(false);
                        }
                }
                    else{
                        volleyViewModel.connector.loading.setValue(false);
                    }
                }


                else{
                    volleyViewModel.connector.loading.setValue(false);
                    if (getIntent().hasExtra(PLACES_LIST)){
                        try {
                            Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Log.d(TAG, "onChanged: the error "+e.toString());
                        }
                        updatingWeather(places);
                        currentMaker.remove();
                    }
                    else if (getIntent().hasExtra(ADDING_FAVOURITE)) {
                        try {
                            Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Log.d(TAG, "onChanged: the error "+e.toString());
                        }
                        Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                        favouriteIntent.putExtra(FORWARD_LATITUDE, places);
                        startActivity(favouriteIntent);
                        finish();
                    }
                    else if(getIntent().hasExtra(EMPTY_SEARCH)){
                        try {
                            Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Log.d(TAG, "onChanged: the error "+e.toString());
                        }
                        Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                        favouriteIntent.putExtra(EMPTY_SEARCH, places);
                        startActivity(favouriteIntent);
                        finish();
                    }
                    else if (getIntent().hasExtra(FIRST_ADDRESS)){
                        try {
                            Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Log.d(TAG, "onChanged: the error "+e.toString());
                        }
                        Intent favouriteIntent = new Intent(MapsActivity.this, PlanSettingActivity.class);
                        favouriteIntent.putExtra(FIRST_ADDRESS_LATITUDE, schedule);
                        startActivity(favouriteIntent);
                        finish();
                    }
                    else if (getIntent().hasExtra(SECOND_ADDRESS)){
                        try {
                            Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Log.d(TAG, "onChanged: the error "+e.toString());
                        }
                        Intent favouriteIntent = new Intent(MapsActivity.this, PlanSettingActivity.class);
                        favouriteIntent.putExtra(SECOND_ADDRESS_LATITUDE, schedule);
                        startActivity(favouriteIntent);
                        finish();
                    }
                    Log.d(TAG, "onChanged: there was an error getting geocode " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GO> call, Throwable t) {
                volleyViewModel.connector.loading.setValue(false);
                if (getIntent().hasExtra(PLACES_LIST)){
                    try {
                        Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Log.d(TAG, "onChanged: the error "+e.toString());
                    }
                    updatingWeather(places);
                    currentMaker.remove();
                }
                else if (getIntent().hasExtra(ADDING_FAVOURITE)) {
                    try {
                        Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Log.d(TAG, "onChanged: the error "+e.toString());
                    }
                    Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                    favouriteIntent.putExtra(FORWARD_LATITUDE, places);
                    startActivity(favouriteIntent);
                    finish();
                }
                else if(getIntent().hasExtra(EMPTY_SEARCH)){
                    try {
                        Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Log.d(TAG, "onChanged: the error "+e.toString());
                    }
                    Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
                    favouriteIntent.putExtra(EMPTY_SEARCH, places);
                    startActivity(favouriteIntent);
                    finish();
                }
                else if (getIntent().hasExtra(FIRST_ADDRESS)){
                    try {
                        Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Log.d(TAG, "onChanged: the error "+e.toString());
                    }
                    Intent favouriteIntent = new Intent(MapsActivity.this, PlanSettingActivity.class);
                    favouriteIntent.putExtra(FIRST_ADDRESS_LATITUDE, schedule);
                    startActivity(favouriteIntent);
                    finish();
                }
                else if (getIntent().hasExtra(SECOND_ADDRESS)){
                    try {
                        Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Log.d(TAG, "onChanged: the error "+e.toString());
                    }
                    Intent favouriteIntent = new Intent(MapsActivity.this, PlanSettingActivity.class);
                    favouriteIntent.putExtra(SECOND_ADDRESS_LATITUDE, schedule);
                    startActivity(favouriteIntent);
                    finish();
                }
                Log.d(TAG, "onChanged: there was an error getting geocode " + t.getMessage());
            }
        });
    }
//    private void setReverseGeocode(String coordinates, Schedule schedule) {
//        mainViewModel.getReverseGeocode(coordinates).observe(this, new Observer<Resource<List<Geocode>>>() {
//            @Override
//            public void onChanged(Resource<List<Geocode>> geocodeResource) {
//                if (geocodeResource != null) {
//                    if (geocodeResource.status == Resource.Status.SUCCESS) {
//                        subLocal.setText(geocodeResource.data.get(0).getName());
//                        if (getIntent().hasExtra(FIRST_ADDRESS)) {
//                            bigLoading.setVisibility(View.GONE);
//                            schedule.setFromDestination(geocodeResource.data.get(0).getLabel());
//                            Intent intent = new Intent(MapsActivity.this, PlanSettingActivity.class);
//                            intent.putExtra(FIRST_ADDRESS_LATITUDE, schedule);
//                            startActivity(intent);
//                            finish();
//                        }
//                        else if(getIntent().hasExtra(EMPTY_SEARCH)){
//                            bigLoading.setVisibility(View.GONE);
//                            places.setContent(geocodeResource.data.get(0).getLabel());
//                            places.setCityName(geocodeResource.data.get(0).getName());
//                            Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
//                            favouriteIntent.putExtra(EMPTY_SEARCH, places);
//                            startActivity(favouriteIntent);
//                            finish();
//                        }
//                        else if (getIntent().hasExtra(SECOND_ADDRESS)) {
//                            bigLoading.setVisibility(View.GONE);
//                            schedule.setFinalDestination(geocodeResource.data.get(0).getLabel());
//                            Intent intent = new Intent(MapsActivity.this, PlanSettingActivity.class);
//                            intent.putExtra(SECOND_ADDRESS_LATITUDE, schedule);
//                            startActivity(intent);
//                            finish();
//                        }
//                        else if (getIntent().hasExtra(MAP_NAVIGATION_SEARCH)) {
//                            bigLoading.setVisibility(View.GONE);
//                            places = new Places();
//                            places.setCityName(geocodeResource.data.get(0).getName());
//                            places.setLatitude(geocodeResource.data.get(0).getLatitude());
//                            places.setLongitude(geocodeResource.data.get(0).getLongitude());
//                            isLocationNeeded = true;
//                            getLocation();
//                        }
//                        else if (getIntent().hasExtra(PLACES_LIST)){
//                            bigLoading.setVisibility(View.GONE);
//                            places.setContent(geocodeResource.data.get(0).getLabel());
//                            updatingWeather(places);
//                            currentMaker.remove();
//                        }
//                        else if (getIntent().hasExtra(ADDING_FAVOURITE)) {
//                            bigLoading.setVisibility(View.GONE);
//                            places.setContent(geocodeResource.data.get(0).getLabel());
//                            places.setCityName(geocodeResource.data.get(0).getName());
//                            Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
//                            favouriteIntent.putExtra(FORWARD_LATITUDE, places);
//                            startActivity(favouriteIntent);
//                            finish();
//                        }
//                        }
//
//
//
//                    else {
//                        if (getIntent().hasExtra(PLACES_LIST)){
//                            try {
//                                Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
//                            }
//                            catch (Exception e){
//                                Log.d(TAG, "onChanged: the error "+e.toString());
//                            }
//                            updatingWeather(places);
//                            currentMaker.remove();
//                        }
//                        else if (getIntent().hasExtra(ADDING_FAVOURITE)) {
//                            try {
//                                Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
//                            }
//                            catch (Exception e){
//                                Log.d(TAG, "onChanged: the error "+e.toString());
//                            }
//                            Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
//                            favouriteIntent.putExtra(FORWARD_LATITUDE, places);
//                            startActivity(favouriteIntent);
//                            finish();
//                        }
//                        else if(getIntent().hasExtra(EMPTY_SEARCH)){
//                            try {
//                                Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
//                            }
//                            catch (Exception e){
//                                Log.d(TAG, "onChanged: the error "+e.toString());
//                            }
//                            Intent favouriteIntent = new Intent(MapsActivity.this, AddingFavouriteActivity.class);
//                            favouriteIntent.putExtra(EMPTY_SEARCH, places);
//                            startActivity(favouriteIntent);
//                            finish();
//                        }
//                        else if (getIntent().hasExtra(FIRST_ADDRESS)){
//                            try {
//                                Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
//                            }
//                            catch (Exception e){
//                                Log.d(TAG, "onChanged: the error "+e.toString());
//                            }
//                            Intent favouriteIntent = new Intent(MapsActivity.this, PlanSettingActivity.class);
//                            favouriteIntent.putExtra(FIRST_ADDRESS_LATITUDE, schedule);
//                            startActivity(favouriteIntent);
//                            finish();
//                        }
//                        else if (getIntent().hasExtra(SECOND_ADDRESS)){
//                            try {
//                                Toast.makeText(MapsActivity.this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
//                            }
//                            catch (Exception e){
//                                Log.d(TAG, "onChanged: the error "+e.toString());
//                            }
//                            Intent favouriteIntent = new Intent(MapsActivity.this, PlanSettingActivity.class);
//                            favouriteIntent.putExtra(SECOND_ADDRESS_LATITUDE, schedule);
//                            startActivity(favouriteIntent);
//                            finish();
//                        }
//                        Log.d(TAG, "onChanged: there was an error getting geocode " + geocodeResource.message);
//                    }
//                }
//            }
//        });
//    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
        } else {

        }
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
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            }
            else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        else if(requestCode == 419 && resultCode == RESULT_OK){


        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        LinearLayout layout = findViewById(R.id.navigate_mode);

        if (moved) {
            //set styling to default
            moved = false;
            volleyViewModel.setShowStart(true);
        }
        if (categoriesMarker != null && categoriesMarker.size() > 0){

            if(!categoriesMarker.get(0).isVisible()){
                volleyViewModel.connector.liveOptions.setValue(null);
                coordinateViewModel.weatherRepository.liveDirections.setValue(null);
                if (polyline != null) {
                    polyline.remove();
                }
                super.onBackPressed();
                points.clear();
                updateList.clear();
                updateDuration.clear();
                updateDistance.clear();
            }
            removeAllMarkers();

        }
        else if(fromCate){
            fromCate= false;
            volleyViewModel.setShowStart(false);
            removeAllMarkers();
            bottomLayout.setVisibility(View.GONE);
            searchNext.setVisibility(View.VISIBLE);
            coordinateViewModel.slideUp(nav);
            volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.NAVIGATE);
            volleyViewModel.connector.liveOptions.setValue(null);
            coordinateViewModel.weatherRepository.liveDirections.setValue(null);
            if (polyline != null) {
                polyline.remove();
            }


        }
        else if (volleyViewModel.connector.loading.getValue() != null && volleyViewModel.connector.loading.getValue()){
            if (getIntent().hasExtra(PLACES_LIST) || getOut){
                volleyViewModel.connector.loading.setValue(false);
            }
            else {
                getOut = true;
                try {
                    Toast.makeText(MapsActivity.this, getString(R.string.please_wait), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(TAG, "onBackPressed: lets check toast error " + e.getMessage());
                }
            }
            if (getOut){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getOut = false;
                    }
                },3000);
            }
        }
        else if (layout.getVisibility() == View.VISIBLE){
            coordinateViewModel.slideDown1(layout);
        }
        else if (getIntent().hasExtra(PLACES_LIST)) {
            places = getIntent().getParcelableExtra(PLACES_LIST);
            if (imageEdit.getVisibility() == View.GONE) {
                if (places.getLatitude() == 0.0 && places.getLongitude() == 0.0) {
                    volleyViewModel.connector.liveOptions.setValue(null);
                    points.clear();
                    stepsWayPoint.clear();
                    stepsNames.clear();
                    stepsInstruction.clear();
                    updateDuration.clear();
                    updateDistance.clear();
                    volleyViewModel.setLiveDuration(null);
                    volleyViewModel.setLiveDistace(null);
                    volleyViewModel.setLiveStepsDirection(null);
                    volleyViewModel.setLiveStepsName(null);
                    volleyViewModel.setLiveWayPoint(null);
                    if (polyline != null) {
                        polyline.remove();
                    }

                    coordinateViewModel.weatherRepository.liveDirections.setValue(null);
                    super.onBackPressed();
                }
                else {
                    imageEdit.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.GONE);
                    relativeWeather.setVisibility(View.VISIBLE);
                }
                if (currentMaker != null) {
                    currentMaker.remove();
                    isAllowed = false;
                }
            }
            else {
                    volleyViewModel.connector.liveOptions.setValue(null);
                    points.clear();
                    updateList.clear();
                    updateDuration.clear();
                    updateDistance.clear();
                volleyViewModel.setLiveDuration(null);
                volleyViewModel.setLiveDistace(null);
                volleyViewModel.setLiveStepsDirection(null);
                volleyViewModel.setLiveStepsName(null);
                volleyViewModel.setLiveWayPoint(null);
                if (polyline != null) {
                    polyline.remove();
                }
                coordinateViewModel.weatherRepository.liveDirections.setValue(null);
                    super.onBackPressed();
            }
        }
        else if (getIntent().hasExtra(FIRST_ADDRESS)){
            Intent intent = new Intent(this, PlanSettingActivity.class);
            intent.putExtra(FIRST_ADDRESS_LATITUDE, schedule);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(SECOND_ADDRESS)){
            Intent intent = new Intent(this, PlanSettingActivity.class);
            intent.putExtra(SECOND_ADDRESS_LATITUDE, schedule);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(EMPTY_SEARCH_MAP)) {
            places = getIntent().getParcelableExtra(EMPTY_SEARCH_MAP);
            Intent intent = new Intent(this, AddingFavouriteActivity.class);
            intent.putExtra(EMPTY_SEARCH, places);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(EMPTY_SEARCH)) {
            places = getIntent().getParcelableExtra(EMPTY_SEARCH);
            Intent intent = new Intent(this, AddingFavouriteActivity.class);
            intent.putExtra(EMPTY_SEARCH, places);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(EMPTY_NAVIGATION_MAP)) {
            Log.d(TAG, "checking: done");
            places = getIntent().getParcelableExtra(EMPTY_NAVIGATION_MAP);
            Intent intent = new Intent(this, AddingFavouriteActivity.class);
            intent.putExtra(EMPTY_NAVIGATION, places);
            startActivity(intent);
            finish();
        }
        else if (getIntent().hasExtra(ADDING_FAVOURITE)) {
            places = getIntent().getParcelableExtra(ADDING_FAVOURITE);
            Intent intent = new Intent(this, AddingFavouriteActivity.class);
            intent.putExtra(PLACES_LIST_EDIT, places);
            startActivity(intent);
            finish();
        }
//        else if (getIntent().hasExtra(MAIN_SEARCH_LATITUDE)) {
//            Log.d(TAG, "onBackPressed: hmm");
//            finishAffinity();
//        }
//        else if (!searchEdit.isIconified()){
//            searchView.setIconified(true);
//        }
        else if (isSlided) {
            if (wrapLayout.getVisibility() == View.GONE) {
                coordinateViewModel.slideUp(wrapLayout);
                float i = 0.7f;
                while (i < 1.0f) {
                    i += 0.0001f;
                    relativeWeather.setAlpha(i);
                }

            } else {
                volleyViewModel.connector.liveOptions.setValue(null);
                points.clear();
                updateList.clear();
                updateDuration.clear();
                updateDistance.clear();
                volleyViewModel.setLiveDuration(null);
                volleyViewModel.setLiveDistace(null);
                volleyViewModel.setLiveStepsDirection(null);
                if (polyline != null) {
                    polyline.remove();
                }
                volleyViewModel.setLiveStepsName(null);
                volleyViewModel.setLiveWayPoint(null);
                coordinateViewModel.weatherRepository.liveDirections.setValue(null);
                super.onBackPressed();
            }
        }
        else {
            volleyViewModel.connector.liveOptions.setValue(null);
            points.clear();
            updateList.clear();
            updateDuration.clear();
            updateDistance.clear();
            volleyViewModel.setLiveDuration(null);
            volleyViewModel.setLiveDistace(null);
            volleyViewModel.setLiveStepsDirection(null);
            volleyViewModel.setLiveStepsName(null);
            volleyViewModel.setLiveWayPoint(null);
            if (polyline != null){
            polyline.remove();
            }
            if (showAds) {
                checkLastAction();
            }
            coordinateViewModel.weatherRepository.liveDirections.setValue(null);
            super.onBackPressed();
        }
    }

    public void loadAd() {
        if (mRewardedAd != null) {
            Activity activityContext = MapsActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }
    private void gettingResults(){
        startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Log.d(TAG, "onActivityResult: hopppp");
                List<LatLng> latLngs = new ArrayList<>();

                if (result.getData() != null && result.getData().hasExtra(LATITUDE_CATE_SUB)) {
                    cateBoo = false;
                    volleyViewModel.connector.loading.setValue(true);
                    String name = result.getData().getStringExtra(STRING_CATE_SUB);
                    subLocal.setText(name);
                    latLngCate = new LatLng(result.getData().getDoubleExtra(LATITUDE_CATE_SUB,0.0),result.getData().getDoubleExtra(LONGITUDE_CATE_SUB,0.0));
                    latLngs = new ArrayList<>();
                    latLngs.add(latLngCate);
                    zoomingOut(new LatLng(latSource, longSource), latLngCate);
                    addMarkers(latLngs);

                        fromCate = true;
                        volleyViewModel.setShowStart(true);
                        searchNext.setVisibility(View.GONE);
                        coordinateViewModel.slideDown1(nav);

                }
                else if (result.getData() != null && result.getData().hasExtra(LATITUDE_CATE_MAIN)) {
                    List<LatLng> list = (List<LatLng>) result.getData().getSerializableExtra(LATITUDE_CATE_MAIN);
                    latLngs.addAll(list);
                    addMarkers(latLngs);
                    volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.OVERVIEW);
                }
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category:
                Intent mapIntent = new Intent(this, CategoriesActivity.class);
                mapIntent.putExtra(LATITUDE_CATE_MAIN,latSource);
                mapIntent.putExtra(LONGITUDE_CATE_MAIN,longSource);
                startForResult.launch(mapIntent);
                break;
            case R.id.scheduled:
                Intent intent = new Intent(this, ScheduleUI.class);
                startActivity(intent);
                break;
            case R.id.favourite:
                if (isDatabaseEmpty) {
                    Log.d(TAG, "onNavigationItemSelected: database was null");
                    insertingDefault();
                } else {
                    Log.d(TAG, "onNavigationItemSelected: database was not null " + isDatabaseEmpty);
                }
                Intent scheduleActivity = new Intent(this, YourPlaceActivity.class);
                startActivity(scheduleActivity);
                break;
        }
        return true;
    }

    private void setPlaces() {
        placesViewModel.getPlaces.observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
                if (places != null) {
                    if (places.size() > 0 && places.size() != 1) {
                        isDatabaseEmpty = false;
                        if (getIntent().hasExtra(PLACES_LIST)) {
                            if (imageEdit.getVisibility() == View.GONE) {
                                Log.d(TAG, "onChanged: we dont need custom makers");
                            } else {
                                if (places.get(0).getCityName() != null) {
                                    LatLng homePosition = new LatLng(places.get(0).getLatitude(), places.get(0).getLongitude());
                                    homeMarker(homePosition);
                                }
                                if (places.get(1).getCityName() != null) {
                                    LatLng workPosition = new LatLng(places.get(1).getLatitude(), places.get(1).getLongitude());
                                    workMarker(workPosition);
                                }
                            }
                        }
                        if (getIntent().hasExtra(ADDING_FAVOURITE)) {

                        }
                        else {
                            for (Places places1 : places) {
                                if (places1.getContent() != null) {
                                    if (places1.getContent().substring(0, 4).equals("null") && !places1.getContent().substring(5).equals("null")) {
                                        Log.d(TAG, "onChanged: okay problem");
                                        if (places1.getCityName() != null) {
                                            places1.setContent(places1.getCityName());
                                            updatingWeather(places1);
                                        }
                                    } else if (places1.getContent().substring(0, 4).equals("null") && places1.getContent().substring(5).equals("null")) {
                                        if (places1.getCityName() != null) {
                                            places1.setContent(places1.getCityName());
                                            updatingWeather(places1);
                                        }
                                    } else {
                                        Log.d(TAG, "onChanged: fuck off");
                                    }
                                }
                            }
                            if (places.get(0).getCityName() != null) {
                                LatLng homePosition = new LatLng(places.get(0).getLatitude(), places.get(0).getLongitude());
                                homeMarker(homePosition);
                            }
                            if (places.get(1).getCityName() != null) {
                                LatLng workPosition = new LatLng(places.get(1).getLatitude(), places.get(1).getLongitude());
                                workMarker(workPosition);
                            }
                        }
                        Log.d(TAG, "onChanged: places size was not null " + places.size());
                    } else {
                        isDatabaseEmpty = true;
                        Log.d(TAG, "insertingDefault: list was null " + isDatabaseEmpty);
                    }
                }
            }
        });
    }
    private void addAndRemoveMakers() {
        placesViewModel.getPlaces.observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> placesList) {
                if (placesList != null && placesList.size() > 0){
                    if (placesList.get(0).getCityName() != null) {
                        homeMaker.remove();
                        workMaker.remove();
                                    LatLng homePosition = new LatLng(placesList.get(0).getLatitude(), placesList.get(0).getLongitude());
                                    homeMarker(homePosition);
                                }
                                if (placesList.get(1).getCityName() != null) {
                                    LatLng workPosition = new LatLng(placesList.get(1).getLatitude(), placesList.get(1).getLongitude());
                                    workMarker(workPosition);
                                }
                }
            }
        });
    }

    private void zoomOut(){
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.d(TAG, "onMapLoaded: this was called");
                if (points != null && points.size() > 0){
                    isZoom = false;
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(points.get(0));
                builder.include(points.get(points.size() - 1));
                LatLngBounds bounds = builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100), 2000, null);
                if (ContextCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            }

            }
        });

    }
    private void zoomingOut(LatLng begin, LatLng end){
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.d(TAG, "onMapLoaded: this was called");
                isZoom = false;
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(begin);
                builder.include(end);
                LatLngBounds bounds = builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100), 2000, null);
                if (ContextCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }

            }
        });
        volleyViewModel.connector.loading.setValue(false);

    }
    private void addMarkers(List<LatLng> markers){
        bigLoading.setVisibility(View.GONE);
        if (markers != null && markers.size() > 0){
            removeAllMarkers();
            for (int i = 0; i<markers.size();i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(markers.get(i));
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cate_pin));

                Marker mLocationMarker = mMap.addMarker(markerOptions); // add the marker to Map
                categoriesMarker.add(mLocationMarker); // add the marker to array

            }

        }

    }

    private void observeStepsInstruction(){
        volleyViewModel.getLiveStepsDirection().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (strings != null && strings.size() >0){
                    if (stepsInstruction.size() >0){
                        stepsInstruction.clear();
                    }
                    stepsInstruction.addAll(strings);
                }
            }
        });
    }
    private void observeDistance(){
        volleyViewModel.getLiveDistace().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                distanceTaken.setText(s);
            }
        });
    }
    private void observeDuration(){
        volleyViewModel.getLiveDuration().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                timeTaken.setText(s);
            }
        });
    }
    private void observeStepsWayPoint(){
        volleyViewModel.getLiveWayPoint().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                if (integers != null && integers.size() >0){
                    if (stepsWayPoint.size() > 0){
                        stepsWayPoint.clear();
                    }
                    stepsWayPoint.addAll(integers);
                }
            }
        });
    }
    private void observeStepsName(){
        volleyViewModel.getLiveStepsName().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (strings != null && strings.size() >0){
                    if (stepsNames.size()> 0){
                        stepsNames.clear();
                    }
                }
            }
        });
    }

    private void removeAllMarkers() {
        for (Marker mLocationMarker: categoriesMarker) {
            mLocationMarker.remove();
        }
        categoriesMarker.clear();
        volleyViewModel.connector.liveTraffic.setValue(null);

    }
    private void settingImage(){
            switch (travelForm) {
                case "motor": {
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.wheel_chair);
                    navMode.setPadding(15, 18, 18, 18);
                    navMode.setImageDrawable(drawable);
                }
                break;
                case "bike": {
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.bike);
                    navMode.setPadding(18, 18, 18, 18);
                    navMode.setImageDrawable(drawable);
                }
                break;
                case "car": {
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.car);
                    navMode.setPadding(1, 1, 1, 1);
                    navMode.setImageDrawable(drawable);
                }
                break;
                case "walk": {
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.walk);
                    navMode.setPadding(22, 22, 22, 22);
                    navMode.setImageDrawable(drawable);
                }
                break;
                case "bus": {
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.bus);
                    navMode.setPadding(1, 1, 1, 1);
                    navMode.setImageDrawable(drawable);
                }
                break;
                default: {
                    Drawable drawable = ContextCompat.getDrawable(MapsActivity.this, R.drawable.car);
                    navMode.setPadding(1, 1, 1, 1);
                    navMode.setImageDrawable(drawable);
                }
                break;

            }
    }
    private void observePolyline(){
        volleyViewModel.getPolylineOptions().observe(this, new Observer<PolylineOptions>() {
            @Override
            public void onChanged(PolylineOptions polylineOptions) {
             //   bigLoading.setVisibility(View.GONE);
                volleyViewModel.connector.loading.setValue(false);
                if (polylineOptions != null){
                    volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.START);
                    Log.d(TAG, "onChanged: polyline triggured" + polylineOptions.getPoints().size());
                    if (points.size() > 0){
                        points.clear();
                    }
                    points = polylineOptions.getPoints();
                    options = new PolylineOptions();
                    options.getPoints().clear();
                    if (polyline != null){
                        polyline.remove();
                    }
                    volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.START);
                    for (int i = 0; i < points.size(); i++){
                        options.add(points.get(i));
                    }
                        Log.d(TAG, "onChanged: was changed to start");

                    options.color(MapsActivity.this.getResources().getColor(R.color.tab_light));

                    options.width(17.0f);
                    settingImage();
                    polyline =mMap.addPolyline(options);
                    zoomOut();
                    bottomLayout.setVisibility(View.VISIBLE);

                }
                else{
                    Log.d(TAG, "onChanged: polyline was null");
                }
            }
        });
    }

    private void observeMapState(){
        volleyViewModel.getMapSate().observe(this, new Observer<VolleyViewModel.mapState>() {
            @Override
            public void onChanged(VolleyViewModel.mapState mapState) {
                volleyViewModel.connector.loading.setValue(false);
                if (mapState != null){
                   // bigLoading.setVisibility(View.GONE);
                    switch(mapState){
                        case START:{
                            if (points.size() >0) {
                                zoomOut();
                                start.setText(R.string.start);
                                if (climate != null && climate.getCurrent() != null) {
                                    relativeWeather.setVisibility(View.VISIBLE);
                                }
                                bottomLayout.setVisibility(View.VISIBLE);
                            }
                            else{
                                volleyViewModel.getMapSate().setValue(VolleyViewModel.mapState.NAVIGATE);
                            }
                        }
                        break;
                        case NAVIGATE:{
                            start.setText(R.string.navigate);
                        }
                        break;
                        case RECENTER:{
                            if (climate != null && climate.getCurrent() != null) {
                                relativeWeather.setVisibility(View.VISIBLE);
                            }
                            bottomLayout.setVisibility(View.VISIBLE);
                    if (lightMode){
                        Log.d(TAG, "onChanged: this is day");
                        if (options != null) {
                            options.color(MapsActivity.this.getResources().getColor(R.color.tab_light));
                        }
                    }
                    else{
                        MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.night_map);
                        mMap.setMapStyle(mapStyleOptions);
                        Log.d(TAG, "onChanged: this is day");
                        if (options != null) {
                            options.color(MapsActivity.this.getResources().getColor(R.color.nighting));
                        }
                    }
                            if (!(latSource ==0.0 && longSource == 0.0)){
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latSource, longSource)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latSource, longSource)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latSource, longSource), 16f));
                    }
                            if (climate.getCurrent() == null) {
                                isClicked = true;
                                setWeatherCurrent(latSource, longSource);
                            }
                            start.setText(R.string.overview);
                            observeCityName();
                        }
                        break;
                        case OVERVIEW:{
                            if (climate != null && climate.getCurrent() != null) {
                                relativeWeather.setVisibility(View.VISIBLE);
                            }
                            bottomLayout.setVisibility(View.VISIBLE);
                            zoomOut();
                            start.setText(R.string.recenter);
                            observeCityName();
                        }
                        break;
                    }
                }
            }
        });
    }
    private void observeCityName(){
        volleyViewModel.getCityViewModel().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null){
                    subLocal.setText(s);
                }
            }
        });
    }
    private void setLoading(){
        volleyViewModel.connector.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean){
                    bigLoading.setVisibility(View.VISIBLE);
                }
                else{
                    bigLoading.setVisibility(View.GONE);
                }
            }
        });
    }
    private void insertCurrent(Weather weather){
        currentNow.setId(1);
        currentViewModel.insertCurrent(weather,currentNow).observe(this, new Observer<Resource<Integer>>() {
           @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource != null){
                    if (integerResource.status == Resource.Status.ERROR){
                        Log.d(TAG, "onChanged: there was an error");
                    }
                    else if (integerResource.status == Resource.Status.SUCCESS){
                        Log.d(TAG, "onChanged: current inserted successffuly");
                        currentViewModel.currentInsertMode.setValue(CurrentViewModel.CurrentInsertMode.AVAILABLE);
                    }
                }
            }
        });
    }
    private void insertHourly(List<Weather> weather){
        hourlyViewModel.insertHourly(weather).observe(this, new Observer<Resource<List<Long>>>() {
            @Override
            public void onChanged(Resource<List<Long>> listResource) {
                if (listResource != null){
                    if (listResource.status == Resource.Status.ERROR){
                        Log.d(TAG, "onChanged: there was an error wiht hourly");
                    }
                    else if (listResource.status == Resource.Status.SUCCESS){
                        hourlyViewModel.hourlyInsertMode.setValue(HourlyViewModel.HourlyInsertMode.AVAILABLE);
                        Log.d(TAG, "onChanged: hourly inserted successfuly");
                    }
                }
            }
        });
    }
    private void insertDaily(List<Weather> weather){
        dailyViewModel.insertDaily(weather).observe(this, new Observer<Resource<List<Long>>>() {
            @Override
            public void onChanged(Resource<List<Long>> listResource) {
                if (listResource != null){
                    if (listResource.status == Resource.Status.ERROR){
                        Log.d(TAG, "onChanged: there was an error with daily");
                    }
                    else if (listResource.status == Resource.Status.SUCCESS){
                        dailyViewModel.dailyInsertMode.setValue(DailyViewModel.DailyInsertMode.AVAILABLE);
                        Log.d(TAG, "onChanged: daily inserted successfuly");
                    }
                }
            }
        });
    }

    private void retrievingHourly(List<Weather> weathers){
        hourlyViewModel.getRetrieveHourly().observe(this, new Observer<List<Hourly>>() {
            @Override
            public void onChanged(List<Hourly> hourlies) {
                if (hourlies != null && hourlies.size() >0){
                    deleteHourly(hourlies, weathers);
                }
                else{
                    insertHourly(weathers);
                }
            }
        });
    }
    private void retrievingDaily(List<Weather> weather){
        dailyViewModel.getRetrieveDaily().observe(this, new Observer<List<Daily>>() {
            @Override
            public void onChanged(List<Daily> hourlies) {
                if (hourlies != null && hourlies.size() > 0 ){
                    deleteDaily(hourlies,weather);
                }
                else{
                    insertDaily(weather);
                }
            }
        });
    }
    private void getLocationUpdate() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(50);
        locationRequest.setPriority(Priority.PRIORITY_LOW_POWER);

//instantiating the LocationCallBack
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        latSource = location.getLatitude();
                        longSource = location.getLongitude();

                        connectionLat = latSource;
                        connectionLon = longSource;

                        if (relativeWeather.getVisibility() == View.GONE){
                            mainViewModel.setConnection(true);
                        }
                        if (volleyViewModel.getMapSate().getValue() != null &&
                                volleyViewModel.getMapSate().getValue() == VolleyViewModel.mapState.RECENTER){
                            float zoom = mMap.getCameraPosition().zoom;
                            if (!(latSource ==0.0 && longSource == 0.0)){
                            new CameraPosition(new LatLng(latSource, longSource), zoom, 0.0f, 0.0f);
                            }
                        }
                        if (isZoom) {
                            float zoom = mMap.getCameraPosition().zoom;
                            if (!(latSource ==0.0 && longSource == 0.0)) {
                                new CameraPosition(new LatLng(latSource, longSource), zoom, 0.0f, 0.0f);
                            }
                           // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latSource, longSource), 16f));
                        }
                        else {
                            if (stepsInstruction.size() > 0) {
                                Location source = new Location("");
                                Location destination = new Location("");
                                Log.d(TAG, "onLocationResult: checking steps  size "+stepsWayPoint.size());
                                for (int i = 0; i < stepsWayPoint.size(); i++) {
                                    if (points.size() >0) {
                                        source.setLatitude(latSource);
                                        source.setLongitude(longSource);
                                        destination.setLatitude(points.get(stepsWayPoint.get(i)).latitude);
                                        destination.setLongitude(points.get(stepsWayPoint.get(i)).longitude);

                                        float interval = 15.0f;
                                        float distance = source.distanceTo(destination);
                                        if (interval > distance) {

                                            if (i != 0) {
                                                double cumulativeDistance = 0.0;
                                                double cumulativeDuration = 0.0;
                                                for (int h = 1; h < i; h++) {
                                                    cumulativeDistance += stepListDistance.get(h);
                                                    cumulativeDuration += stepListDuraiton.get(h);

                                                }
                                                volleyViewModel.setLiveDistace(convertingDistance(totalDistance - cumulativeDistance));
                                                volleyViewModel.setLiveDuration(convertingTime(totalDuration - cumulativeDuration));
                                            }
                                            String textFromHtml = Jsoup.parse(stepsInstruction.get(i)).text();
                                            try {
                                                Toast.makeText(MapsActivity.this, textFromHtml, Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Log.d(TAG, "onLocationResult: toast navigate " + e.toString());
                                            }
                                            if (navSpeakerOn.getVisibility() == View.VISIBLE && speechBoolean) {
                                                speechBoolean = false;
                                                tts = new TextToSpeech(MapsActivity.this, new TextToSpeech.OnInitListener() {
                                                    @Override
                                                    public void onInit(int i) {
                                                        if (i == TextToSpeech.SUCCESS) {
                                                            int result = tts.setLanguage(Locale.getDefault());
                                                            if (result == TextToSpeech.LANG_MISSING_DATA ||
                                                                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                                Log.e("error", "This Language is not supported");
                                                            }
                                                            else {
                                                                    speechBoolean = false;
                                                                    tts.speak(textFromHtml, TextToSpeech.QUEUE_FLUSH, null, null);


                                                                new Handler().postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        settingSpeech();
                                                                        setWeatherCurrent(latSource, longSource);

                                                                    }
                                                                }, 10000);

                                                             }
                                                        } else {
                                                            Log.e("error", "Initilization Failed!");
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    else {
                                        Log.d(TAG, "onLocationResult: no step located");
                                    }
                                }
                            }

                        }
                    }
                        }
                    }
                };
                int permission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                } else {
                    checkPermission(REQUIRED_SDK_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
                }
            }
    private void updateHourly(List<Weather> weathers, List<Hourly> hourlyList ){
        hourlyViewModel.updateHourly(weathers, hourlyList).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource.status == Resource.Status.ERROR){
                    Log.d(TAG, "onChanged: there was an error updating hourly");
                }
                else if (integerResource.status == Resource.Status.SUCCESS){
                    Log.d(TAG, "onChanged: hourly updated Successfully");
                }
            }
        });
    }
    private void updateDaily(List<Weather> weathers, List<Daily> hourlyList ){
        dailyViewModel.updateDaily(weathers, hourlyList).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource.status == Resource.Status.ERROR){
                    Log.d(TAG, "onChanged: there was an error updating daily");
                }
                else if (integerResource.status == Resource.Status.SUCCESS){
                    Log.d(TAG, "onChanged: daily updated Successfully");
                }
            }
        });
    }

    private void deleteCurrent(){
        currentViewModel.deletingCurrent(currentNow).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource != null){
                    if (integerResource.status == Resource.Status.SUCCESS){
                        Log.d(TAG, "onChanged: current was deleted seccessfully");
                    }
                    else{
                        Log.d(TAG, "onChanged: there was an error deleting current "+integerResource.message);
                    }
                }
            }
        });
    }
    private void deleteDaily(List<Daily> dailyList, List<Weather> weather){
        dailyViewModel.deleteDaily(dailyList).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource != null){
                    if (integerResource.status == Resource.Status.SUCCESS){
                        Log.d(TAG, "onChanged: daily was deleted seccessfully");
                        insertDaily(weather);
                    }
                    else{
                        Log.d(TAG, "onChanged: there was an error deleting daily "+integerResource.message);
                    }
                }
            }
        });
    }

    private void deleteHourly(List<Hourly> hourlyList, List<Weather> weathers){
        hourlyViewModel.deleteHourly(hourlyList).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource != null){
                    if (integerResource.status == Resource.Status.SUCCESS){
                        insertHourly(weathers);
                        Log.d(TAG, "onChanged: daily was deleted seccessfully");
                    }
                    else{
                        Log.d(TAG, "onChanged: there was an error deleting daily "+integerResource.message);
                    }
                }
            }
        });
    }
}











