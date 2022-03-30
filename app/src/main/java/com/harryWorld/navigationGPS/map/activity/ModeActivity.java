package com.harryWorld.navigationGPS.map.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.harryWorld.navigationGPS.MapsActivity;
import com.harryWorld.navigationGPS.R;

public class ModeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String CAR ="car";
    public static final String BUS ="bus";
    public static final String BIKE ="bike";
    public static final String MOTOR ="motor";
    public static final String WALK ="walk";
    private ImageView car, bike, motor, bus, walk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        car = findViewById(R.id.choosing_car);
        motor = findViewById(R.id.choosing_motor);
        bike = findViewById(R.id.choosing_bike);
        bus = findViewById(R.id.choosing_Bus);
        walk = findViewById(R.id.choosing_walk);

        setOnClick();
    }

    private void setOnClick(){
        car.setOnClickListener(this);
        motor.setOnClickListener(this);
        bus.setOnClickListener(this);
        bike.setOnClickListener(this);
        walk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choosing_car:{
                Intent carIntent = new Intent(this, MapsActivity.class);
                carIntent.putExtra("mode_activity",CAR);
                startActivity(carIntent);
            }
            break;
            case R.id.choosing_Bus:{
                Intent busIntent = new Intent(this, MapsActivity.class);
                busIntent.putExtra("mode_activity",BUS);
                startActivity(busIntent);
            }
            break; case R.id.choosing_motor:{
                Intent motorIntent = new Intent(this, MapsActivity.class);
                motorIntent.putExtra("mode_activity",MOTOR);
                startActivity(motorIntent);
            }
            break; case R.id.choosing_bike:{
                Intent bikeIntent = new Intent(this, MapsActivity.class);
                bikeIntent.putExtra("mode_activity",BIKE);
                startActivity(bikeIntent);
            }
            break; case R.id.choosing_walk:{
                Intent walkIntent = new Intent(this, MapsActivity.class);
                walkIntent.putExtra("mode_activity",WALK);
                startActivity(walkIntent);
            }
            break;
        }
    }
}