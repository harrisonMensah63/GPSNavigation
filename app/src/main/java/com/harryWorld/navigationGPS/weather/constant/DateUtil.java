package com.harryWorld.navigationGPS.weather.constant;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil {

    private static final String TAG = "DateUtil";

    public static String getCurrentTimeStamp() throws Exception{
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE",Locale.getDefault()); //MUST USE LOWERCASE 'y'. API 23- can't use uppercase
            return dateFormat.format(new Date());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Couldn't format the date into MM-yyyy");
        }
    }

    public static String getMonthFromNumber(String monthNumber){
        Calendar cal = Calendar.getInstance();

        int month = Integer.parseInt(monthNumber);
        cal.set(Calendar.DAY_OF_MONTH,month);
        int dow = cal.get(Calendar.DAY_OF_MONTH);

        return new SimpleDateFormat("MMM", Locale.getDefault()).format(cal.getTime());    }

    public static String getDateFormat(List<Integer> timeStamp){
        long date1 = 0;
        for (int i = 0; i<timeStamp.size();i++){
             date1 =  timeStamp.get(i)*1000;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
        Date dt = new Date(date1);
        return dateFormat.format(dt);
    }
    public static String getDailyDateFormat(List<Integer> timeStamp){
        long date1 = 0;
        for (int i = 0; i<timeStamp.size();i++){
            date1 =  timeStamp.get(i)*1000;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
      //  TimeZone.setDefault(TimeZone.);
        Date dt = new Date(date1);
        String string = dateFormat.format(dt);

//        String strings = new ArrayList<>();
//        strings.add(string);
        Log.d(TAG, "getDailyDateFormat: lets see sunrise "+string);
        return string;
    }

    public static String getDay(int day, int month, int year) {

        Calendar cal = Calendar.getInstance();

            cal.set(year,month-1,day);

        int dow = cal.get(Calendar.DAY_OF_WEEK);

        Log.d(TAG, "getDay: dowwwwwwwwwwwwwwwwwwww "+dow);
       return new SimpleDateFormat("EE", Locale.getDefault()).format(cal.getTime());
    }
}

