package com.harryWorld.weatherGPS.map.recyclerVeiw.inside;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.harryWorld.weatherGPS.R;

import java.util.List;

public class InsideAdapter extends RecyclerView.Adapter<InsideAdapter.InsideVIewHolder> {

    InsideVIewHolder.InsideListener listener;
    List<List<String>> categoriesList;
    Context context;

    public InsideAdapter(InsideVIewHolder.InsideListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public InsideVIewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inside,parent,false);
        return new InsideVIewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull InsideVIewHolder holder, int position) {

        holder.textView.setText(categoriesList.get(position).get(0));
        holder.text1.setText(categoriesList.get(position).get(1));
        holder.text2.setText(categoriesList.get(position).get(2));
        holder.text3.setText(categoriesList.get(position).get(3));
        holder.text4.setText(categoriesList.get(position).get(4));
        holder.text5.setText(categoriesList.get(position).get(5));
        if (position == 0){
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.emergency);
            holder.image.setImageDrawable(drawable);
        }
        else if (position == 1){
            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.restaurant);
            holder.image.setImageDrawable(drawable);
        }
        else if (position == 2){
            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.entertainment);
            holder.image.setImageDrawable(drawable);
        }
        else if (position == 3){
            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.service);
            holder.image.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class InsideVIewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView image;
        TextView text1, text2, text3, text4, text5;
        InsideListener insideListener;

        public InsideVIewHolder(@NonNull @io.reactivex.annotations.NonNull View itemView, InsideListener insideListener) {
            super(itemView);
            this.insideListener = insideListener;
            textView = itemView.findViewById(R.id.categories_main);
            image = itemView.findViewById(R.id.image_categories);
            text1 = itemView.findViewById(R.id.text_1);
            text2 = itemView.findViewById(R.id.text_2);
            text3 = itemView.findViewById(R.id.text_3);
            text4 = itemView.findViewById(R.id.text_4);
            text5 = itemView.findViewById(R.id.text_5);
            text1.setOnClickListener(this);
            text2.setOnClickListener(this);
            text3.setOnClickListener(this);
            text4.setOnClickListener(this);
            text5.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.text_1:{
                    insideListener.getText1(getAdapterPosition());
                }
                break;
                case R.id.text_2:{
                    insideListener.getText2(getAdapterPosition());
                }
                break;
                case R.id.text_3:{
                    insideListener.getText3(getAdapterPosition());
                }
                break;
                case R.id.text_4:{
                    insideListener.getText4(getAdapterPosition());
                }
                break;
                case R.id.text_5:{
                    insideListener.getText5(getAdapterPosition());
                }
                break;
            }
        }
        public interface InsideListener{
            void getText1(int position);
            void getText2(int position);
            void getText3(int position);
            void getText4(int position);
            void getText5(int position);
        }
    }
    public void setCategories(List<List<String>> categoriesList){
        this.categoriesList = categoriesList;
        notifyDataSetChanged();
    }

}
