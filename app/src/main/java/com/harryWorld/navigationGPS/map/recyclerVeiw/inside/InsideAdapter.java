package com.harryWorld.navigationGPS.map.recyclerVeiw.inside;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;

import java.util.List;

public class InsideAdapter extends RecyclerView.Adapter<InsideAdapter.InsideVIewHolder> {

    InsideVIewHolder.InsideListener listener;
    List<String> categoriesList;

    public InsideAdapter(InsideVIewHolder.InsideListener listener) {
        this.listener = listener;
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

        holder.textView.setText(categoriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class InsideVIewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        InsideListener insideListener;

        public InsideVIewHolder(@NonNull @io.reactivex.annotations.NonNull View itemView, InsideListener insideListener) {
            super(itemView);
            this.insideListener = insideListener;
            textView = itemView.findViewById(R.id.items_now);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            insideListener.getInside(getAdapterPosition());
        }
        public interface InsideListener{
            void getInside(int position);
        }
    }
    public void setCategories(List<String> categoriesList){
        this.categoriesList = categoriesList;
        notifyDataSetChanged();
    }

}
