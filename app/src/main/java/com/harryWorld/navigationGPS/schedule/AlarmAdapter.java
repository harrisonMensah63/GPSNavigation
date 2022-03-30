package com.harryWorld.navigationGPS.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private List<Alarm> alarmList;
    private ViewHolder.AlarmListener listener;
    private Boolean isOn;

    public AlarmAdapter(ViewHolder.AlarmListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_layout,parent,false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (alarmList.get(position).isAlarm()){
            holder.imageOn.setVisibility(View.VISIBLE);
            holder.imageOff.setVisibility(View.GONE);
        }
        else{
            holder.imageOff.setVisibility(View.VISIBLE);
            holder.imageOn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (alarmList != null) {
            return alarmList.size();
        }
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AlarmListener listener;

        private ImageView imageOn, imageOff;
        private AlarmAdapter adapter;
        public ViewHolder(@NonNull View itemView, AlarmListener listener) {
            super(itemView);
            this.listener = listener;
            imageOff = itemView.findViewById(R.id.alarm_off);
            imageOn = itemView.findViewById(R.id.alarm_on);
            adapter = new AlarmAdapter(listener);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.getAlarmPosition(getAdapterPosition());
        }

        public interface AlarmListener{
            void getAlarmPosition(int position);
        }
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
        notifyDataSetChanged();
    }
    public List<Alarm> getAlarmList() {
        return alarmList;
    }
}





