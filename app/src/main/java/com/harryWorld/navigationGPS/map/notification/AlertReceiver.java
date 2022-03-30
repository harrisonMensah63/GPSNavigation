package com.harryWorld.navigationGPS.map.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.harryWorld.navigationGPS.MapsActivity;
import com.harryWorld.navigationGPS.R;

import static com.harryWorld.navigationGPS.schedule.ScheduleUI.CHANNELID1;
import static com.harryWorld.navigationGPS.schedule.ScheduleUI.CHANNELNAME1;

public class AlertReceiver extends BroadcastReceiver {
    private static final String TAG = "AlertReceiver";
    private String title, message;
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: on receive alerted");
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        int position = intent.getIntExtra("position",0);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


        Intent activityIntent = new Intent(context,MapsActivity.class);
        activityIntent.putExtra("position_alarm",position);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,activityIntent,0);
        NotificationCompat.Builder notify = new NotificationCompat.Builder(context,CHANNELID1)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(uri)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.alarm_off)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(position, notify.build());


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void creatingChannel(Context context){
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
        getManager(context).createNotificationChannel(nc);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public NotificationManager getManager(Context c){

        if (manager == null){
            manager = (NotificationManager) c.getSystemService(NotificationManager.class);
        }
        return manager;
    }
}
