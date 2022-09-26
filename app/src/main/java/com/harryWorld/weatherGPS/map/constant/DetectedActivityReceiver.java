package com.harryWorld.weatherGPS.map.constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.BuildConfig;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

public class DetectedActivityReceiver extends BroadcastReceiver {
    public static final String RECEIVER_ACTION = BuildConfig.APPLICATION_ID + ".DetectedActivityReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
            for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                transitionType(event.getTransitionType());
                activityType(event.getActivityType());
            }
        }
    }


    private void transitionType(int transition) {
         switch (transition) {
            case  ActivityTransition.ACTIVITY_TRANSITION_ENTER :{

            }
            case ActivityTransition.ACTIVITY_TRANSITION_EXIT :{

            }
        }
    }

    private void activityType(int activity) {
         switch (activity) {
             case DetectedActivity.IN_VEHICLE:{

             }
             case DetectedActivity.STILL :{

             }
            case DetectedActivity.WALKING :{}

        }
    }
}
