package com.harryWorld.navigationGPS.map.recyclerVeiw;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.map.utils.Places;

import java.util.ArrayList;
import java.util.List;

public class YourPlaceAdapter extends RecyclerView.Adapter<YourPlaceAdapter.MyViewHolder> {
    private static final String TAG = "YourPlaceAdapter";
    Context c;
    private List<Places> placesList = new ArrayList<>();
    MyViewHolder.WeatherListener weatherListener;


    public YourPlaceAdapter(Context c, MyViewHolder.WeatherListener weatherListener) {
        this.c = c;
        this.weatherListener = weatherListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(c).inflate(R.layout.places_layout, parent, false);
        return new MyViewHolder(view,weatherListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Drawable drawable = ContextCompat.getDrawable(c,R.color.tab_light);
        holder.image.setImageDrawable(drawable);
        if(position == 0){

            holder.image.setImageResource(R.drawable.ic_home);
            holder.title.setText(R.string.home);
        }
        else if (position == 1){
            holder.image.setImageResource(R.drawable.ic_work);
            holder.title.setText(R.string.work);
        }
        else {
            holder.image.setImageResource(R.drawable.star_24);
            holder.title.setText(placesList.get(position).getTitle());
        }
        holder.content.setText(placesList.get(position).getContent());
        Log.d(TAG, "onBindViewHolder: checking content "+placesList.get(position).getContent());
        Log.d(TAG, "onBindViewHolder: checking content "+placesList.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        if (placesList != null){
        return placesList.size();
        }
        else {
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, content;
        private ImageView image;
        private WeatherListener listener;


        public MyViewHolder(@NonNull View itemView, YourPlaceAdapter.MyViewHolder.WeatherListener weatherListener) {
            super(itemView);
            listener = weatherListener;
            title = itemView.findViewById(R.id.title_place);
            content = itemView.findViewById(R.id.content_place);
            image = itemView.findViewById(R.id.places_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.getPosition(getAdapterPosition());
        }

        public interface WeatherListener {
            void getPosition(int position);
        }
    }
    public void setPlacesList(List<Places> placesList){
        this.placesList = placesList;
        notifyDataSetChanged();
    }

    public List<Places> getPlacesList() {
        return placesList;
    }

    public Places getPosition(int position){
        return placesList.get(position);
    }
}