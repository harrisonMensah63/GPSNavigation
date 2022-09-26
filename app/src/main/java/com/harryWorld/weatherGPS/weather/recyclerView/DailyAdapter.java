package com.harryWorld.weatherGPS.weather.recyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.weather.constant.DateUtil;
import com.harryWorld.weatherGPS.weather.utils.Daily;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.MyViewHolder> {
    private static final String TAG = "DailyAdapter";
    Context c;
     List<Daily> myDailyWeather;
    MyViewHolder.WeatherListener weatherListener;

    public DailyAdapter(Context c, MyViewHolder.WeatherListener weatherListener) {
        this.c = c;
        this.weatherListener = weatherListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(c).inflate(R.layout.layout_daily,parent,false);
        return new MyViewHolder(view,weatherListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String month = myDailyWeather.get(position).getDailyTime().substring(5,7);
        String day = myDailyWeather.get(position).getDailyTime().substring(8,10);
        String year = myDailyWeather.get(position).getDailyTime().substring(0,4);

        Log.d(TAG, "onBindViewHolder: lets see day "+day);
        int day1 = Integer.parseInt(day);
        int month1 = Integer.parseInt(month);
        int year1 = Integer.parseInt(year);

        month = DateUtil.getMonthFromNumber(month);
        String date = month + " " + day;
        String gettingDaysOfWeek = DateUtil.getDay(day1,month1,year1);

        Log.d(TAG, "onBindViewHolder: day of week "+gettingDaysOfWeek);
        holder.rainIcon.setIconResource(c.getString(R.string.wi_umbrella));
        holder.rainIcon.setIconColor(R.color.white);
        holder.cloudInfo.setIconResource(c.getString(R.string.wi_cloud));
        holder.cloudInfo.setIconSize(35);
        holder.cloudInfo.setIconColor(R.color.white);
        holder.rainIcon.setIconSize(28);
        holder.dailyDate.setText(date);
        holder.dailyTime.setText(gettingDaysOfWeek);
        holder.dailyMax.setText( Math.round(myDailyWeather.get(position).getMax_temp_daily())+"°");
        holder.dailyMin.setText( Math.round(myDailyWeather.get(position).getMin_temp_daily())+"°");
        holder.rainPercentage.setText(String.valueOf(Math.round(myDailyWeather.get(position).getDailyPop()))+"%");
        holder.cloudInfoText.setText(myDailyWeather.get(position).getDailyDescription().getWeather());
        //Log.d(TAG, "onBindViewHolder: checking daily adapter "+myDailyWeather.size());
    }

    @Override
    public int getItemCount() {
        if (myDailyWeather != null) {
            return myDailyWeather.size();
        }
        return 0;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dailyDate,dailyTime,dailyMax,dailyMin,rainPercentage,cloudInfoText;
        WeatherIconView rainIcon,cloudInfo;
        WeatherListener mWeatherListener;
        public MyViewHolder(@NonNull View itemView, WeatherListener weatherListener) {
            super(itemView);
            mWeatherListener = weatherListener;
            dailyDate = itemView.findViewById(R.id.daily_date);
            dailyTime = itemView.findViewById(R.id.daily_time);
            dailyMax = itemView.findViewById(R.id.daily_max);
            dailyMin = itemView.findViewById(R.id.daily_min);
            rainPercentage = itemView.findViewById(R.id.rain_per);
            cloudInfoText = itemView.findViewById(R.id.daily_cloud_text);
            rainIcon = itemView.findViewById(R.id.daily_icon);
            cloudInfo = itemView.findViewById(R.id.daily_cloud);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mWeatherListener.getPosition(getAdapterPosition());
        }

        public interface WeatherListener{
            void getPosition(int position);
        }
    }

    public void setMyDailyWeather(List<Daily> myDailyWeather){
        this.myDailyWeather = myDailyWeather;
        notifyDataSetChanged();
    }

    public Daily getPosition(int position){
        return myDailyWeather.get(position);
    }
}
