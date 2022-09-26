package com.harryWorld.weatherGPS.map.activity.customCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harryWorld.weatherGPS.R;
import com.harryWorld.weatherGPS.map.retrofit.Geocode;
import com.harryWorld.weatherGPS.map.retrofit.pictures.Results;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomCateAdapter extends RecyclerView.Adapter<CustomCateAdapter.CustomCateViewHolder> {

    OnItemClicked onItemClicked;
    private Context context;
    private List<Geocode> geocodeList;
    private List<Results> results;
    private String address;

    public CustomCateAdapter(OnItemClicked onItemClicked, Context context) {
        this.onItemClicked = onItemClicked;
        this.context = context;
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public CustomCateViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_categories_list,parent,false);
        return new CustomCateViewHolder(view,onItemClicked);
    }

    private void glide(ImageView image, int stringId){
        if (results != null && results.size() > 0){
            int randomNum = ThreadLocalRandom.current().nextInt(0, results.size());
            Glide.with(context)
                    .load(results.get(randomNum).getLink().getPic()) // image url
                    .placeholder(stringId) // any placeholder to load at start
                    .error(stringId)  // any image in case of error
                    .centerCrop()
                    .into(image);
        }
        else {
            image.setImageResource(stringId);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull CustomCateViewHolder holder, int position) {
        holder.subText.setText(geocodeList.get(position).getAddress().getLabel());
        holder.mainText.setText(geocodeList.get(position).getPoi().getName());
        if (geocodeList.get(position).getPoi().getPhone() != null){
            holder.phone.setText(geocodeList.get(position).getPoi().getPhone());
        }
        else{
            holder.phone.setText("unavailable");
        }

        if (geocodeList.get(position).getPoi().getPhone() != null) {
            holder.phone.setText(geocodeList.get(position).getPoi().getPhone());
        }
            if (address.equals(context.getString(R.string.police))) {
                glide(holder.cardImage,R.drawable.police);
            }
            else if (address.equals(context.getString(R.string.hospital))) {
                glide(holder.cardImage,R.drawable.hospital);
            }
            else if (address.equals(context.getString(R.string.pharmacy))) {
                glide(holder.cardImage,R.drawable.pharmacy);
            }
            else if (address.equals(context.getString(R.string.gas_station))) {
                glide(holder.cardImage,R.drawable.gas_station);
            }
            else if (address.equals(context.getString(R.string.car_repair))) {
                glide(holder.cardImage, R.drawable.car_repair);
        }
        else if (address.equals(context.getString(R.string.restaurant))){
                glide(holder.cardImage,R.drawable.restaurant_cate);
            }
            else if (address.equals(context.getString(R.string.coffee))) {
                glide(holder.cardImage,R.drawable.coffee);
            }
            else if (address.equals(context.getString(R.string.bars))) {
                glide(holder.cardImage,R.drawable.bar);
            }
            else if (address.equals(context.getString(R.string.cafeteria))) {
                glide(holder.cardImage,R.drawable.teashop);
            }
            else if (address.equals(context.getString(R.string.teashop))) {
                glide(holder.cardImage,R.drawable.teashop);
        }
        else if (address.equals(context.getString(R.string.movies))){
                glide(holder.cardImage,R.drawable.movies);
            }
            else if (address.equals(context.getString(R.string.nightlife))) {
                glide(holder.cardImage,R.drawable.nightlife);
            }
            else if (address.equals(context.getString(R.string.park))) {
                glide(holder.cardImage,R.drawable.park);
            }
            else if (address.equals(context.getString(R.string.libraries))) {
                glide(holder.cardImage,R.drawable.libraries);
            }
            else if (address.equals(context.getString(R.string.museums))) {
                glide(holder.cardImage,R.drawable.museum);
            }
        else if (address.equals(context.getString(R.string.supermarket))){
                glide(holder.cardImage,R.drawable.supermarket);
            }
            else if (address.equals(context.getString(R.string.atms))) {
                glide(holder.cardImage,R.drawable.atm);
            }
            else if (address.equals(context.getString(R.string.car_wash))) {
                glide(holder.cardImage,R.drawable.car_washing);
            }
            else if (address.equals(context.getString(R.string.parking))) {
                glide(holder.cardImage,R.drawable.parking);
            }
            else if (address.equals(context.getString(R.string.electric_vehicle_charging))) {
                glide(holder.cardImage,R.drawable.car_charging);
            }


    }

    @Override
    public int getItemCount() {
        if (geocodeList != null) {
            return geocodeList.size();
        }
        return 0;
    }

    static class CustomCateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClicked onItemClicked;
        TextView phone, website;
        TextView mainText, subText;
        ImageView cardImage;
        LinearLayout linearLayout1, linearLayout2;

        public CustomCateViewHolder(@NonNull View itemView, OnItemClicked onItemClicked) {
            super(itemView);
            this.onItemClicked = onItemClicked;
            phone = itemView.findViewById(R.id.phone_number);
            website = itemView.findViewById(R.id.website);
            linearLayout1 = itemView.findViewById(R.id.linear_1);
            linearLayout2 = itemView.findViewById(R.id.linear_2);
            mainText = itemView.findViewById(R.id.main_text);
            subText = itemView.findViewById(R.id.sub_text);
            cardImage = itemView.findViewById(R.id.card_image);

            itemView.setOnClickListener(this);
            linearLayout1.setOnClickListener(this);
            linearLayout2.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.linear_1:{
                    onItemClicked.onPhoneViewClicked(getAdapterPosition());
                }
                break;
                case R.id.linear_2:{
                    onItemClicked.onWebsiteViewClick(getAdapterPosition());
                }
                break;
                default:{
                    onItemClicked.onViewClick(getAdapterPosition());
                }
                break;
            }
        }
    }

    public void setGeocodeList(List<Geocode> geocodeList) {
        this.geocodeList = geocodeList;
        notifyDataSetChanged();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}






