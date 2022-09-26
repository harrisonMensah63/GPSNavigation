package com.harryWorld.weatherGPS.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.weatherGPS.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private MyViewHolder.ScheduleListener listener;
    private MyViewHolder.AlarmListener alarmListener;
    private List<Schedule> scheduleList = new ArrayList<>();

    public ScheduleAdapter(MyViewHolder.ScheduleListener listener, MyViewHolder.AlarmListener alarmListener) {
        this.listener = listener;
        this.alarmListener = alarmListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_view_layout,parent,false);
        return new MyViewHolder(view,listener,alarmListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (scheduleList.get(position).getFromDestination() != null &&
                scheduleList.get(position).getFinalDestination() != null) {
            String[] result = scheduleList.get(position).getFromDestination().split(",", 2);
            String first = result[0];

            String[] results = scheduleList.get(position).getFinalDestination().split(",", 2);
            String second = results[0];
            holder.scheduleName.setText(scheduleList.get(position).getName());
            holder.dayOfWeek.setText(first + " " + "-" + " " + second);
            holder.date.setText(scheduleList.get(position).getDate());
            holder.time.setText(scheduleList.get(position).getTime());

            if (scheduleList.get(position).alarm != null && scheduleList.get(position).alarm.isAlarm()){
                holder.imageOn.setVisibility(View.VISIBLE);
                holder.imageOff.setVisibility(View.GONE);
            }
            else{
                holder.imageOff.setVisibility(View.VISIBLE);
                holder.imageOn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (scheduleList != null) {
            return scheduleList.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView scheduleName,dayOfWeek;
        TextView date,time;
        private ImageView imageOn, imageOff;
        ScheduleListener listener;
        LinearLayout alarmLayout;
        AlarmListener alarmListener;

        public MyViewHolder(@NonNull View itemView,ScheduleListener listener,AlarmListener alarmListener) {
            super(itemView);
            scheduleName = itemView.findViewById(R.id.schedule_name);
            dayOfWeek = itemView.findViewById(R.id.day_of_week);
            date = itemView.findViewById(R.id.layout_date);
            time = itemView.findViewById(R.id.layout_time);
            imageOff = itemView.findViewById(R.id.alarm_off);
            imageOn = itemView.findViewById(R.id.alarm_on);
            alarmLayout = itemView.findViewById(R.id.alarm_layout);
            this.listener =listener;
            this.alarmListener = alarmListener;

            itemView.setOnClickListener(this);
            alarmLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.alarm_layout){
                alarmListener.getAlarmPosition(getAdapterPosition());
            }
            else {
                listener.getPosition(getAdapterPosition());
            }
        }

        public interface ScheduleListener{
             void getPosition(int position);
        }
        public interface AlarmListener{
            void getAlarmPosition(int position);
        }
    }

    public Schedule getScheduleList(int position){
        return scheduleList.get(position);
    }
    public List<Schedule> getSchedule() {
        return scheduleList;
    }
    public void setScheduleList(List<Schedule> scheduleList){
        this.scheduleList = scheduleList;
    }
    public void updateAlarm(Alarm alarm,int position){
        scheduleList.get(position).alarm = alarm;
        notifyItemChanged(position);
    }
}
