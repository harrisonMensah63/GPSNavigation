package com.harryWorld.navigationGPS.map.recyclerVeiw;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.map.retrofit.Geocode;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private static final String TAG = "ListAdapter";
    private Context context;
    public static List<Geocode> prediction;
    private ListViewHolder.ListListener listListener;

    public ListAdapter(Context context, ListViewHolder.ListListener listListener) {
        this.context = context;
        this.listListener = listListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.search_list_layout,parent,false);
        return new ListViewHolder(view, listListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
        String county;
        String region;
        String country;
        if (prediction.get(position).getAddress().getCounty()== null){
            county = "";
        }
        else {
            county = prediction.get(position).getAddress().getCounty()+",";
        }
        if (prediction.get(position).getAddress().getRegion() == null){
            region = "";
           // continent = ","+prediction.get(position).getContinent();
        }
        else {
            region = prediction.get(position).getAddress().getRegion()+",";
           // continent = "";
        }
        if (prediction.get(position).getAddress().getCountry() == null){
            country = "";
            // continent = ","+prediction.get(position).getContinent();
        }
        else {
            country = prediction.get(position).getAddress().getCountry();
            // continent = "";
        }
        if (prediction.get(position).getAddress().getCounty() == null){
            holder.mainList.setText(region);
        }
        else {
            holder.mainList.setText(prediction.get(position).getAddress().getCounty());
        }
        holder.subList.setText(county+region+country);
        Log.d(TAG, "onBindViewHolder: getting position "+prediction.size());
    }

    @Override
    public int getItemCount() {
        if (prediction != null) {
            if (prediction.size() > 0) {
                if (prediction.size() > 4) {
                    return 5;
                }
                return prediction.size();
            }
        }
        return 0;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private TextView mainList;
        private TextView subList;
        private ListListener listener;
        public ListViewHolder(@NonNull View itemView, ListListener listener) {
            super(itemView);
            this.listener = listener;
            mainList = itemView.findViewById(R.id.main_list);
            subList = itemView.findViewById(R.id.sub_list);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            listener.getListPosition(prediction.get(getAdapterPosition()));
        }
        public interface ListListener{
            void getListPosition(Geocode prediction);
        }
    }

    public void setPrediction(List<Geocode> prediction) {
        this.prediction = prediction;
    }
}
