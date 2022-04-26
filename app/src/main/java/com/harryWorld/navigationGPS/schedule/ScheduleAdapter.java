package com.harryWorld.navigationGPS.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private MyViewHolder.ScheduleListener listener;
    private List<Schedule> scheduleList = new ArrayList<>();

    public ScheduleAdapter(MyViewHolder.ScheduleListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_view_layout,parent,false);
        return new MyViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (scheduleList.get(position).getFromDestination() != null &&
                scheduleList.get(position).getFromDestination() != null) {
            String[] result = scheduleList.get(position).getFromDestination().split(",", 2);
            String first = result[0];

            String[] results = scheduleList.get(position).getFinalDestination().split(",", 2);
            String second = results[0];
            holder.scheduleName.setText(scheduleList.get(position).getName());
            holder.dayOfWeek.setText(first + " " + "-" + " " + second);
            holder.date.setText(scheduleList.get(position).getDate());
            holder.time.setText(scheduleList.get(position).getTime());
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
        ScheduleListener listener;

        public MyViewHolder(@NonNull View itemView,ScheduleListener listener) {
            super(itemView);
            scheduleName = itemView.findViewById(R.id.schedule_name);
            dayOfWeek = itemView.findViewById(R.id.day_of_week);
            date = itemView.findViewById(R.id.layout_date);
            time = itemView.findViewById(R.id.layout_time);
            this.listener =listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.getPosition(getAdapterPosition());
        }

        public interface ScheduleListener{
             void getPosition(int position);
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
        notifyDataSetChanged();
    }
}
