package com.harryWorld.navigationGPS.schedule;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.harryWorld.navigationGPS.R;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;

public class ScheduleActivity extends AppCompatActivity {

    private static final String TAG = "ScheduleActivity";

    // map embedded in the map fragment
    private Map map = null;

    // map fragment embedded in this activity
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;

    /**
     * permissions request code
     */
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            for (int index = permissions.length - 1; index >= 0; --index) {
                if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                    // exit the app if one permission is not granted
                    Toast.makeText(this, "Required permission '" + permissions[index]
                            + "' not granted, exiting", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
            }
            // all permissions were granted
        }
    }


    public  int getDayNumberNew(LocalDate date) {
        long timeStamp = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamp);


        return c.get(Calendar.DAY_OF_WEEK);
    }
}
