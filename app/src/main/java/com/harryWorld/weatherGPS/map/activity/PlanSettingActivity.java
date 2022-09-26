package com.harryWorld.weatherGPS.map.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.harryWorld.weatherGPS.MapsActivity;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.constant.DatePickerFragment;
import com.harryWorld.weatherGPS.map.constant.DayOfTheWeek;
import com.harryWorld.weatherGPS.map.constant.TImePicker;
import com.harryWorld.weatherGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.weatherGPS.schedule.Alarm;
import com.harryWorld.weatherGPS.schedule.Schedule;
import com.harryWorld.weatherGPS.weather.utils.Resource;
import com.harryWorld.weatherGPS.weather.viewModels.MainViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

import java.text.DateFormat;
import java.util.Calendar;

import static com.harryWorld.weatherGPS.map.constant.Constant.FIRST_ADDRESS;
import static com.harryWorld.weatherGPS.map.constant.Constant.FIRST_ADDRESS_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.FROM_SEARCH_FIRST;
import static com.harryWorld.weatherGPS.map.constant.Constant.FROM_SEARCH_SECOND;
import static com.harryWorld.weatherGPS.map.constant.Constant.SECOND_ADDRESS;
import static com.harryWorld.weatherGPS.map.constant.Constant.SECOND_ADDRESS_LATITUDE;
import static com.harryWorld.weatherGPS.map.constant.Constant.TITLE_NAME;

public class PlanSettingActivity extends AppCompatActivity implements
        View.OnClickListener, TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    private static final String TAG = "PlanSettingActivity";
    private int days;
    private TextView daysText, monthText, fingerTop, fingerBottom;
    private TextView firstAddressName, secondAddressName;
    private TextView currentTime;
    private TextView save, buttonTime;
    private EditText editTitle;
    private RelativeLayout relativeLayout;
    private MainViewModel mainViewModel;

    private boolean firstName;
    private int minute, hour, day, month, year;
    private DayOfTheWeek week = new DayOfTheWeek();
    Alarm alarm = new Alarm();
    Schedule schedule = new Schedule();


    private PlacesViewModel placesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_setting);

        placesViewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        daysText = findViewById(R.id.days_text);
        monthText = findViewById(R.id.month_text);
        fingerTop = findViewById(R.id.finger_plan);
        fingerBottom = findViewById(R.id.finger_plan_second);
        firstAddressName = findViewById(R.id.entering_address_plan);
        secondAddressName = findViewById(R.id.entering_address_plan_second);
        relativeLayout = findViewById(R.id.plan_layout);
        currentTime = findViewById(R.id.current_time);
        save = findViewById(R.id.save_plan);
        buttonTime = findViewById(R.id.button_time);
        editTitle = findViewById(R.id.edit_title);
        days=0;

        getWindow().setStatusBarColor(getResources().getColor(R.color.tab_dark));
        setDaysText();
        settingHint();
        mightyFunction();
    }

    private void mightyFunction(){
        if (getIntent().hasExtra(FIRST_ADDRESS_LATITUDE)){
            schedule = getIntent().getParcelableExtra(FIRST_ADDRESS_LATITUDE);
            if (schedule != null){
                editTitle.setText(schedule.getName());
                Log.d(TAG, "mightyFunction: nooooow "+schedule.getFromLongitudeValue());
                if (schedule.getFromDestination() != null && schedule.getFromDestination().trim().length() > 0) {
                    firstAddressName.setText(schedule.getFromDestination());
                }
                if (schedule.getFinalDestination() != null &&schedule.getFinalDestination().trim()
                        != null && !schedule.getFinalDestination().trim().equals("")){
                    secondAddressName.setText(schedule.getFinalDestination());
                }
            }
        }
        if (getIntent().hasExtra(SECOND_ADDRESS_LATITUDE)){
            schedule = getIntent().getParcelableExtra(SECOND_ADDRESS_LATITUDE);
            if (schedule != null){
                editTitle.setText(schedule.getName());
                if (schedule.getFinalDestination() != null && schedule.getFinalDestination().trim().length() > 0) {
                    secondAddressName.setText(schedule.getFinalDestination());
                }
                if (schedule.getFromDestination() != null && schedule.getFromDestination().trim()
                        != null && !schedule.getFromDestination().trim().equals("")){
                    firstAddressName.setText(schedule.getFromDestination());
                }
            }
        }
        if (getIntent().hasExtra(FROM_SEARCH_FIRST)) {
            schedule = getIntent().getParcelableExtra(FROM_SEARCH_FIRST);
            if (schedule != null) {
                editTitle.setText(schedule.getName());
                if (schedule.getFromDestination() != null && !schedule.getFromDestination().trim().equals("")) {
                    firstAddressName.setText(schedule.getFromDestination());
                }
                if (schedule.getFinalDestination() != null && !schedule.getFinalDestination().trim().equals("")) {
                    secondAddressName.setText(schedule.getFinalDestination());
                }
            }
        }
        else if (getIntent().hasExtra(FROM_SEARCH_SECOND)){
            schedule = getIntent().getParcelableExtra(FROM_SEARCH_SECOND);
            if (schedule != null) {
                editTitle.setText(schedule.getName());
                if (schedule.getFromDestination() != null && !schedule.getFromDestination().trim().equals("")) {
                    firstAddressName.setText(schedule.getFromDestination());
                }
                if (schedule.getFinalDestination() != null && !schedule.getFinalDestination().trim().equals("")) {
                    secondAddressName.setText(schedule.getFinalDestination());
                }
            }
        }
    }

    private void insertAlarm(Alarm alarm){
        placesViewModel.insertingAlarm(alarm).observe(this, new Observer<Resource<Long>>() {
            @Override
            public void onChanged(Resource<Long> longResource) {
                if (longResource.status == Resource.Status.SUCCESS){
                    Log.d(TAG, "onChanged: alarm was inserted properly");
                }
                else if (longResource.status == Resource.Status.ERROR){
                    Log.d(TAG, "onChanged: there was an error inserting alarm "+longResource.message);
                }
            }
        });
    }
    private void updateAlarm(Alarm alarm){
        placesViewModel.updatingAlarm(alarm).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource.status == Resource.Status.SUCCESS){
                    Log.d(TAG, "onChanged: alarm was updating properly");
                }
                else if (integerResource.status == Resource.Status.ERROR){
                    Log.d(TAG, "onChanged: there was an error updating alarm "+integerResource.message);
                }
            }
        });
    }
    private void insertSchedule(Schedule schedule){
        placesViewModel.insertingSchedule(schedule).observe(this, new Observer<Resource<Long>>() {
            @Override
            public void onChanged(Resource<Long> longResource) {
                if (longResource != null){
                    if (longResource.status == Resource.Status.SUCCESS){
                        Log.d(TAG, "onChanged: schedule was inserted successfully");
                    }
                    else{
                        Log.d(TAG, "onChanged: schedule failed to be inserted "+longResource.message);
                    }
                }
            }
        });
    }
    private void updateSchedule(Schedule schedule){
        placesViewModel.updatingSchedule(schedule).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (schedule != null){
                    Log.d(TAG, "onChanged: updating was success "+integerResource.data);
                }
            }
        });
    }

    private void getSchedule(){
        Alarm alarm = new Alarm();
        alarm.setAlarmName(editTitle.getText().toString());
        alarm.setTime(currentTime.getText().toString());
        schedule.setName(editTitle.getText().toString());
        schedule.setDayOfTheWeek(daysText.getText().toString());
        schedule.setTime(currentTime.getText().toString());
        schedule.setDate(monthText.getText().toString());

        Calendar c = Calendar.getInstance();
        if (day != 0){
            schedule.setDay(day);
            schedule.setMonth(month);
            schedule.setYear(year);

            alarm.setDay(day);
            alarm.setMonth(month);
            alarm.setYear(year);
        }
        else {
            schedule.setYear(c.get(Calendar.YEAR));
            schedule.setMonth(c.get(Calendar.MONTH));
            schedule.setDay(c.get(Calendar.DAY_OF_MONTH));

            alarm.setYear(c.get(Calendar.YEAR));
            alarm.setMonth(c.get(Calendar.MONTH));
            alarm.setDay(c.get(Calendar.DAY_OF_MONTH));
        }

        if (minute != 0){
            schedule.setMinute(minute);
            schedule.setHour(hour);

            alarm.setMinute(minute);
            alarm.setHour(hour);
        }
        else {
            schedule.setMinute(c.get(Calendar.MINUTE));
            schedule.setHour(c.get(Calendar.HOUR_OF_DAY));

            alarm.setMinute(c.get(Calendar.MINUTE));
            alarm.setHour(c.get(Calendar.HOUR_OF_DAY));
        }
        choosing(schedule);
        insertAlarm(alarm);
    }
    private void choosing(Schedule schedule){
        if (getIntent().hasExtra("schedule_ui")){
            updateSchedule(schedule);
        }
        else {
            insertSchedule(schedule);
        }
        Log.d(TAG, "choosing: checking latitude from destination "+schedule.getFromLatitudeValue());
    }
    private void setDaysText(){
        fingerTop.setOnClickListener(this);
        fingerBottom.setOnClickListener(this);
        firstAddressName.setOnClickListener(this);
        secondAddressName.setOnClickListener(this);
        currentTime.setOnClickListener(this);
        save.setOnClickListener(this);
        buttonTime.setOnClickListener(this);
        editTitle.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.finger_plan:{
                if (schedule == null){
                    schedule = new Schedule();
                }
                schedule.setName(editTitle.getText().toString());
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra(FIRST_ADDRESS,schedule);
                startActivity(intent);
                finish();
            }
            break;
            case R.id.finger_plan_second:{
                if (schedule == null){
                    schedule = new Schedule();
                }
                schedule.setName(editTitle.getText().toString());
                Intent intent = new Intent(this,MapsActivity.class);
                intent.putExtra(TITLE_NAME,editTitle.getText().toString());
                intent.putExtra(SECOND_ADDRESS,schedule);
                startActivity(intent);
                finish();
            }
            break;
            case R.id.entering_address_plan:{
                if (schedule == null){
                    schedule = new Schedule();
                }
                schedule.setName(editTitle.getText().toString());
                Intent intent = new Intent(PlanSettingActivity.this,SearchActivity.class);
                intent.putExtra(FROM_SEARCH_FIRST,schedule);
                startActivity(intent);
                finish();
            }
            break;
            case R.id.entering_address_plan_second:{
                if (schedule == null){
                    schedule = new Schedule();
                }
                schedule.setName(editTitle.getText().toString());
                Intent intent = new Intent(PlanSettingActivity.this,SearchActivity.class);
                intent.putExtra(FROM_SEARCH_SECOND,schedule);
                startActivity(intent);
                finish();
            }
            break;
            case R.id.save_plan:{
                if (schedule == null){
                    schedule = new Schedule();
                }
                if (editTitle.getText().toString().trim().length() == 0) {
                    forceShow(this);
                    Toast.makeText(this,
                            R.string.schedule_title_empty,
                            Toast.LENGTH_LONG).show();
                }
                else if (schedule.getFromLatitudeValue() == 0.0 && schedule.getFromLongitudeValue() == 0.0
                || schedule.getFinalLongitudeValue() == 0.0 && schedule.getFinalLatitudeValue() == 0.0){
                    Toast.makeText(this,
                            R.string.enter_address_name,
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: there was a problem with address");
                }
                else{
                    if (firstAddressName.getText().toString().equals(getString(R.string.enter_address))
                     || secondAddressName.getText().toString().equals(getString(R.string.enter_address))){
                        Toast.makeText(this,
                                R.string.enter_address_name,
                                Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "onClick: there was no problem");
                    editTitle.setText(editTitle.getText().toString());
                    getSchedule();
                    finish();
                }
            }
            break;
            case R.id.button_time:{
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),getString(R.string.date_picker));
            }
            break;
            case R.id.edit_title:{
                Log.d(TAG, "onClick: enable soft keyboard");
                if (!isSoftKeyEnabled(this)) {
                    forceShow(this);
                }

            }
            break;
            case R.id.plan_layout:{
                forceHide(this,editTitle);
                Log.d(TAG, "onClick: hide soft key");
            }
            break;

        }
    }
    private boolean isSoftKeyEnabled(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }
    public static void forceShow(@NonNull Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    public static void forceHide(@NonNull Activity activity, @NonNull EditText editText) {
        if (activity.getCurrentFocus() == null || !(activity.getCurrentFocus() instanceof EditText)) {
            editText.requestFocus();
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 20){
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    if (place.getLatLng() != null) {
                        double lat = place.getLatLng().latitude;
                        double lon = place.getLatLng().longitude;
                        alarm.setFirstLatitude(lat);
                        alarm.setFirstLongitude(lon);
                        String name = place.getName();
                        firstAddressName.setText(name);
                    }
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                }
                else {
                    Log.d(TAG, "onActivityResult: data was null");
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                if (data != null) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);
                    if (status.getStatusMessage() != null) {
                        Log.i(TAG, status.getStatusMessage());
                    }
                    else {
                        Log.d(TAG, "onActivityResult: status message was null");
                    }
                }
            }
//            else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
            return;
        }
        else if (requestCode == 21){
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    if (place.getLatLng() != null) {
                        double lat = place.getLatLng().latitude;
                        double lon = place.getLatLng().longitude;
                        alarm.setSecondLatitude(lat);
                        alarm.setSecondLongitude(lon);
                        String name = place.getName();
                        secondAddressName.setText(name);
                    }
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                if (data != null) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);
                    if (status.getStatusMessage() != null) {
                        Log.i(TAG, status.getStatusMessage());
                    }
                    else{
                        Log.d(TAG, "onActivityResult: status message was null");
                    }
                }
                else{
                    Log.d(TAG, "onActivityResult: data was null");
                }
            }
//            else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.minute = minute;
        hour = hourOfDay;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        currentTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        day = dayOfMonth;
        this.month = month;
        this.year  = year;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        if (year >=year1){
            if (year1 == year){
                if (month >= month1){
                    if (day >= day1){
                        daysText.setText(DayOfTheWeek.dating(calendar));
                        monthText.setText(DayOfTheWeek.month(calendar)+" "+dayOfMonth);
                        DialogFragment timePicker = new TImePicker();
                        timePicker.show(getSupportFragmentManager(),getString(R.string.time_picker));

                        Schedule schedule = new Schedule();
                        schedule.setFromDestination(String.valueOf(firstAddressName.getText()));
                        schedule.setFinalDestination(String.valueOf(secondAddressName.getText()));
                        schedule.setDayOfTheWeek(String.valueOf(daysText.getText()));
                        schedule.setDate(String.valueOf(monthText.getText()));
                        schedule.setTime(String.valueOf(currentTime.getText()));
                        schedule.setDay(dayOfMonth);
                        schedule.setMonth(month);
                        schedule.setYear(year);
                        schedule.setHour(hour);
                        schedule.setMinute(minute);
                        Log.d(TAG, "onDateSet: checkking "+schedule.getHour()+" : "+schedule.getMinute());
                    }
                }
                else{
                    Toast.makeText(this,
                            R.string.choose_valid_date,
                            Toast.LENGTH_SHORT).show();
                }
            }
            else {
                daysText.setText(DayOfTheWeek.dating(calendar));
                monthText.setText(DayOfTheWeek.month(calendar)+" "+dayOfMonth);
                DialogFragment timePicker = new TImePicker();
                timePicker.show(getSupportFragmentManager(), String.valueOf(R.string.time_picker));

                Schedule schedule = new Schedule();
                schedule.setFromDestination(String.valueOf(firstAddressName.getText()));
                schedule.setFinalDestination(String.valueOf(secondAddressName.getText()));
                schedule.setDayOfTheWeek(String.valueOf(daysText.getText()));
                schedule.setDate(String.valueOf(monthText.getText()));
                schedule.setTime(String.valueOf(currentTime.getText()));
                schedule.setDay(dayOfMonth);
                schedule.setMonth(month);
                schedule.setYear(year);
                schedule.setHour(Integer.parseInt(String.valueOf(currentTime.getText()).trim().substring(0,2)));
                schedule.setMinute(Integer.parseInt(String.valueOf(currentTime.getText()).trim().substring(5)));
                Log.d(TAG, "onDateSet: checkking "+schedule.getHour()+" : "+schedule.getMinute());
            }
        }
        else {
            Toast.makeText(this, R.string.choose_valid_date, Toast.LENGTH_SHORT).show();
        }

    }

    private void settingHint(){
            editTitle.setHint(R.string.schedule_name);
        }
}







