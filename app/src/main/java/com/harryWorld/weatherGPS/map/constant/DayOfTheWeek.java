package com.harryWorld.weatherGPS.map.constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayOfTheWeek {
    public static String month(Calendar calendar){
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.getDefault());
        return  sdf.format(date);
    }
    public static String dating(Calendar calendar){
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(date);
    }
}
