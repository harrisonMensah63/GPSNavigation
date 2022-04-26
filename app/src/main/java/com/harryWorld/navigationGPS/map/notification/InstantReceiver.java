package com.harryWorld.navigationGPS.map.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.harryWorld.navigationGPS.MapsActivity;
import com.harryWorld.navigationGPS.R;

import static com.harryWorld.navigationGPS.schedule.ScheduleUI.CHANNELID2;


public class InstantReceiver extends BroadcastReceiver {
    private static final String TAG = "InstantReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("instant_title");
        String message = intent.getStringExtra("instant_message");
        int position = intent.getIntExtra("instant_position",0);
        long time = intent.getLongExtra("instant_long",0L);

        Intent activityIntent = new Intent(context, MapsActivity.class);
        activityIntent.putExtra("position_alarm",position);
        PendingIntent contentIntent = PendingIntent.getActivity(context,position,activityIntent,0);


        NotificationCompat.Builder notify = new NotificationCompat.Builder(context,CHANNELID2)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setSound(null)
                .setTimeoutAfter(time)
                .setSmallIcon(R.drawable.alarm_off)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(position, notify.build());
    }

}



