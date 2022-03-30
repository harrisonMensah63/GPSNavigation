package com.harryWorld.navigationGPS.weather.recyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.weather.api.Climate;
import com.harryWorld.navigationGPS.weather.api.forecast.ForecastHourly;
import com.harryWorld.navigationGPS.weather.utils.Hourly;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.ArrayList;
import java.util.List;


public class HourlyAdapter extends Adapter<HourlyAdapter.MyViewHolder> {
    private static final String TAG = "HourlyAdapter";
    Context c;
    private Climate myHourlyWeather = new Climate();
    MyViewHolder.WeatherListener weatherListener;


    public HourlyAdapter(Context c, MyViewHolder.WeatherListener weatherListener) {
        this.c = c;
        this.weatherListener = weatherListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(c).inflate(R.layout.hourly_layout, parent, false);
        return new MyViewHolder(view,weatherListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

      //  Log.d(TAG, "onBindViewHolder: checking time "+myHourlyWeather.get(position).getHourlyTime());
        String time = myHourlyWeather.getForecast().getForecastday().get(0).getHour().get(position).getTime().substring(11,16);


        holder.rainPer.setText(myHourlyWeather.getForecast().getForecastday().get(0).getHour().get(position).getProbability()+"%");
        holder.rainIcon.setIconResource(c.getString(R.string.wi_umbrella));
        holder.rainIcon.setIconSize(28);
        holder.hourlyTime.setText(time);
        holder.hourlyDescription.setText(String.valueOf(myHourlyWeather.getForecast().getForecastday().get(0).getHour().get(position).getCondition().getDescription()));
        holder.temp.setText(Math.round(myHourlyWeather.getForecast().getForecastday().get(0).getHour().get(position).getTemp())+"°C");
     //   Log.d(TAG, "onBindViewHolder: checking hourly adapter data "+myHourlyWeather.get(position).getHourlyPop());
    }

    @Override
    public int getItemCount() {
        if (myHourlyWeather != null){
            if (myHourlyWeather.getForecast().getForecastday().get(0).getHour() != null) {
                return myHourlyWeather.getForecast().getForecastday().get(0).getHour().size();
            }
            return 0;
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView hourlyTime, hourlyDescription, rainPer, temp;
        WeatherIconView rainIcon;
        WeatherListener mWeatherListener;

        public MyViewHolder(@NonNull View itemView, HourlyAdapter.MyViewHolder.WeatherListener weatherListener) {
            super(itemView);
            mWeatherListener = weatherListener;
            rainPer = itemView.findViewById(R.id.rain_percentage);
            hourlyDescription = itemView.findViewById(R.id.hourly_description);
            temp = itemView.findViewById(R.id.hourly_temp);
            hourlyTime = itemView.findViewById(R.id.hourly_time);
            rainIcon = itemView.findViewById(R.id.rain_percentage_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mWeatherListener.getPosition(getAdapterPosition());
        }

        public interface WeatherListener {
            void getPosition(int position);
        }
    }
    public void setMyHourlyWeather(Climate myHourlyWeather){
        this.myHourlyWeather = myHourlyWeather;
        notifyDataSetChanged();
    }

    public ForecastHourly getPosition(int position){
        return myHourlyWeather.getForecast().getForecastday().get(0).getHour().get(position);
    }
}
