package com.harryWorld.navigationGPS.schedule;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.MapsActivity;
import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.map.activity.PlanSettingActivity;
import com.harryWorld.navigationGPS.map.notification.AlertReceiver;
import com.harryWorld.navigationGPS.map.notification.InstantReceiver;
import com.harryWorld.navigationGPS.map.viewModels.PlacesViewModel;
import com.harryWorld.navigationGPS.weather.constant.Decoration;
import com.harryWorld.navigationGPS.weather.utils.Resource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.harryWorld.navigationGPS.map.constant.Constant.SCHEDULE_U_I;

public class ScheduleUI extends AppCompatActivity implements
        ScheduleAdapter.MyViewHolder.ScheduleListener, AlarmAdapter.ViewHolder.AlarmListener {

    public static final String CHANNELID1 = "channel_id1";
    public static final String CHANNELNAME1 = "channel_name1";
    public static final String CHANNELID2 = "channel_id2";
    public static final String CHANNELNAME2 = "channel_name2";

    private static final String TAG = "ScheduleUI";
    private RecyclerView recyclerView, recyclerViewAlarm;
    private PlacesViewModel viewModel;
    ScheduleAdapter adapter;
    AlarmAdapter alarmAdapter;
    NotificationManager manager;
    private boolean updateAlarm;
    private NotificationManagerCompat notificationManager;
    private int gettingSize;

    Notification notification;
    private final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_u_i);

        recyclerView = findViewById(R.id.schedule_recycler_view);
        recyclerViewAlarm = findViewById(R.id.schedule_recycler_view_alarm);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating);
        viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
        notificationManager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            creatingChannel();
            creatingChannel2();
        }
        setRecyclerView();
        setAlarmRecyclerView();
        retrieveSchedule();
        retrieveAlarm();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleUI.this, PlanSettingActivity.class);
                intent.putExtra("list_size",gettingSize);
                startActivity(intent);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void creatingChannel2(){
        Log.d(TAG, "creatingChannel: creating O initiated");
        NotificationChannel nc2 = new NotificationChannel(CHANNELID2, CHANNELNAME2, NotificationManager.IMPORTANCE_DEFAULT);
        nc2.enableLights(true);
        nc2.enableVibration(true);
        nc2.setImportance(NotificationManager.IMPORTANCE_HIGH);
        nc2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        nc2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        nc2.setLightColor(R.color.colorPrimary);
        getManager().createNotificationChannel(nc2);
        // getManager(context).
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public NotificationManager getManager(){

        if (manager == null){
            manager = (NotificationManager) getSystemService(NotificationManager.class);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void creatingChannel(){
        Log.d(TAG, "creatingChannel: over O was activted");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        AudioAttributes.Builder builder = new AudioAttributes.Builder();
        builder.setUsage(AudioAttributes.USAGE_NOTIFICATION);
        NotificationChannel nc = new NotificationChannel(CHANNELID1,
                CHANNELNAME1, NotificationManager.IMPORTANCE_DEFAULT);

        nc.enableLights(true);
        nc.enableVibration(true);
        nc.enableLights(true);
        nc.setSound(alarmSound, builder.build());
        nc.setImportance(NotificationManager.IMPORTANCE_HIGH);
        nc.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        nc.setLightColor(R.color.colorPrimary);
        getManager().createNotificationChannel(nc);
    }
    private void setNotificationChannel(Alarm alarm, String title, String message, int position){

        Intent activityIntent = new Intent(this,MapsActivity.class);
        activityIntent.putExtra("alarm_id",position);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,activityIntent,position);

        //.setSound(alarmSound)
        NotificationCompat.Builder compat = new NotificationCompat.Builder(this, CHANNELID1)
                .setContentTitle(title)
                .setContentText(message)
                //.setSound(alarmSound)
                .setColor(getResources().getColor(R.color.quantum_bluegrey100))
                .setSmallIcon(R.drawable.alarm_on)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM);
          notification = compat
                .build();

        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.YEAR,alarm.getYear());
        alarmCalendar.set(Calendar.MONTH,alarm.getMonth());
        alarmCalendar.set(Calendar.DAY_OF_MONTH,alarm.getDay());
        Date alarmDate = alarmCalendar.getTime();

        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        if (date.after(alarmDate)){
            compat.setSound(alarmSound);
        }
        notificationManager.notify(position,notification);

    }

    private void setRecyclerView(){
        adapter = new ScheduleAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        Decoration decoration = new Decoration(5);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    private void setAlarmRecyclerView(){
        alarmAdapter = new AlarmAdapter(this);
        recyclerViewAlarm.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        Decoration decoration = new Decoration(5);
        recyclerViewAlarm.addItemDecoration(decoration);
        recyclerViewAlarm.setAdapter(alarmAdapter);
    }

    private void deleteScheduleList(Schedule schedule, int position){
            adapter.getSchedule().remove(schedule);
            deletingAlarm(alarmAdapter.getAlarmList().get(position));
            adapter.notifyDataSetChanged();
            deleteSchedule(schedule);
        }
    private void deleteAlarmList(Alarm alarm){
        alarmAdapter.getAlarmList().remove(alarm);
        alarmAdapter.notifyDataSetChanged();
        deletingAlarm(alarm);
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            int position = viewHolder.getAdapterPosition();
            deleteScheduleList(adapter.getSchedule().get(position), position);
           // deleteAlarmList(alarmAdapter.getAlarmList().get(viewHolder.getAdapterPosition()));
        }
    };
    private void deleteSchedule(Schedule schedule){
        viewModel.deletingSchedule(schedule).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource != null){
                    if (integerResource.status == Resource.Status.SUCCESS){
                        Log.d(TAG, "onChanged: schedule was deleted successfully");
                    }
                    else {
                        Log.d(TAG, "onChanged: there was an error deleting schedule");
                    }
                }
            }
        });
    }
    private void retrieveSchedule(){
        viewModel.getSchedule.observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                if (schedules != null){
                    Log.d(TAG, "onChanged: checking schedule size "+schedules.size());
                    adapter.setScheduleList(schedules);
                }
            }
        });
    }


    private void insertAlarm(Alarm alarm){
        viewModel.insertingAlarm(alarm).observe(this, new Observer<Resource<Long>>() {
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
        viewModel.updatingAlarm(alarm).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource.status == Resource.Status.SUCCESS){
                    Log.d(TAG, "onChanged: alarm was updating properly");
                    alarmAdapter.notifyDataSetChanged();
                }
                else if (integerResource.status == Resource.Status.ERROR){
                    Log.d(TAG, "onChanged: there was an error updating alarm "+integerResource.message);
                }
            }
        });
    }
    private void deletingAlarm(Alarm alarm){
        viewModel.deletingAlarm(alarm).observe(this, new Observer<Resource<Integer>>() {
            @Override
            public void onChanged(Resource<Integer> integerResource) {
                if (integerResource.status == Resource.Status.SUCCESS){
                    Log.d(TAG, "onChanged: alarm was deleting properly");
                }
                else if (integerResource.status == Resource.Status.ERROR){
                    Log.d(TAG, "onChanged: there was an error deleting alarm "+integerResource.message);
                }
            }
        });
    }
    private void gettingClickAlarm(int position){
        Log.d(TAG, "onChanged: onClick alarm was retrieved");
            viewModel.getAlarm.observe(this, new Observer<List<Alarm>>() {
                @Override
                public void onChanged(List<Alarm> alarms) {
                    if (alarms != null) {
                        Alarm alarm = alarms.get(position);

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR,alarm.getYear());
                        c.set(Calendar.MONTH,alarm.getMonth());
                        c.set(Calendar.DAY_OF_MONTH,alarm.getDay());
                        if (alarms.size() != 0) {
                            if (updateAlarm) {
                                updateAlarm = false;
                                Log.d(TAG, "onChanged: alarm was true this minute "+alarm.getMinute());
                                Log.d(TAG, "onChanged: alarm was true this message "+alarm.getTime());

                                if (alarm.isAlarm()) {
                                    cancelAlarm(c,alarm);
                                    instantCancelAlarm(alarm,position);
                                    alarm.setAlarm(false);
                                } else {
                                    startAlarm(alarm, position);
                                    instantStartAlarm(alarm,position);
                                    alarm.setAlarm(true);
                                }
                                updateAlarm(alarm);
                            }
                            //   alarmAdapter.setAlarmList(alarms);
                        }
                    }
                }
            });
    }
//    private
    private void retrieveAlarm(){
        viewModel.getAlarm.observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                if (alarms != null){
                    if (alarms.size() != 0){
                     //   Log.d(TAG, "onChanged: alarm  was retrieved "+alarms.get(4).isAlarm());
                        for (Alarm alarm :alarms){
                           // checkingTime(alarm);
                            Calendar c = Calendar.getInstance();
                            calendar(c,alarm);
                            if (alarm.isAlarm()){
                                //startAlarm(c);
                              //  checkingTime(alarm,);
                                Log.d(TAG, "onChanged: alarm was started "+alarm.getId()+" "+alarm.isAlarm());
                            }
                            else{
                                Log.d(TAG, "onChanged: alarm was canceled "+alarm.getId());
                               // cancelAlarm(c);
                            }
                        }
                     alarmAdapter.setAlarmList(alarms);
                    }
                }
            }
        });
    }

    private void instantStartAlarm(Alarm alarm, int position){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            creatingChannel2();
        }
        Calendar c = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar(calendar,alarm);
        long time = calendar.getTimeInMillis();
        Intent intent = new Intent(this, InstantReceiver.class);
        intent.putExtra("instant_title", alarm.getAlarmName());
        intent.putExtra("instant_message",alarm.getTime());
        intent.putExtra("instant_position",position);
        intent.putExtra("instant_long",time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,position,intent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Log.d(TAG, "startAlarm: checking date time "+c.get(Calendar.MINUTE));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }
    private void instantCancelAlarm(Alarm alarm, int position){
        Intent activityIntent = new Intent(this,MapsActivity.class);
        activityIntent.putExtra("position_alarm",position);
        PendingIntent contentIntent = PendingIntent.getActivity(this,position,activityIntent,0);
        NotificationCompat.Builder notify = new NotificationCompat.Builder(this,CHANNELID2)
                .setContentTitle(alarm.getAlarmName())
                .setContentText(alarm.getTime())
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.alarm_off)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.cancel(position);
    }
    private void calendar(Calendar c, Alarm alarm){
        c.set(Calendar.DAY_OF_MONTH, alarm.getDay());
        c.set(Calendar.MONTH, alarm.getMonth());
        c.set(Calendar.YEAR, alarm.getYear());
        c.set(Calendar.MINUTE, alarm.getMinute());
        c.set(Calendar.HOUR_OF_DAY, alarm.getHour());
    }
    private void startAlarm(Alarm alarm, int position){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            creatingChannel();
        }
        int zero = 0;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,alarm.getYear());
        c.set(Calendar.MONTH,alarm.getMonth());
        c.set(Calendar.DAY_OF_MONTH,alarm.getDay());
        c.set(Calendar.MINUTE,alarm.getMinute());
        c.set(Calendar.HOUR_OF_DAY,alarm.getHour());
        c.set(Calendar.SECOND,zero);

        Log.d(TAG, "startAlarm: checking alarm time "+alarm.getMinute()+" "+c.get(Calendar.MINUTE));
        Date date = c.getTime();
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("title", alarm.getAlarmName());
        intent.putExtra("message",alarm.getTime());
        intent.putExtra("position",position);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,position,intent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.d(TAG, "startAlarm: checking date time "+c.get(Calendar.MINUTE));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,date.getTime(),pendingIntent);

    }
    private void cancelAlarm(Calendar c, Alarm alarm){
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,100,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
    }
    @Override
    public void getPosition(int position) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(SCHEDULE_U_I,adapter.getScheduleList(position));
        startActivity(intent);
    }

    @Override
    public void getAlarmPosition(int position) {
        Log.d(TAG, "getAlarmPosition: alarm is clicked");
        updateAlarm = true;
        gettingClickAlarm(position);
        adapter.notifyDataSetChanged();
    }
}