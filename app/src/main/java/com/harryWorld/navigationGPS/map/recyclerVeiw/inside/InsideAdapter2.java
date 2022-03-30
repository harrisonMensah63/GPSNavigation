package com.harryWorld.navigationGPS.map.recyclerVeiw.inside;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;

import java.util.List;

public class InsideAdapter2 extends RecyclerView.Adapter<InsideAdapter2.InsideVIewHolder2> {

    List<String> categoriesList2;
    InsideVIewHolder2.InsideListener2 listener2;

    public InsideAdapter2(InsideVIewHolder2.InsideListener2 listener2) {
        this.listener2 = listener2;
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public InsideVIewHolder2 onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inside,parent,false);
        return new InsideVIewHolder2(view, listener2);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull InsideVIewHolder2 holder, int position) {

        holder.textView.setText(categoriesList2.get(position));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class InsideVIewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        InsideListener2 insideListener2;

        public InsideVIewHolder2(@NonNull @io.reactivex.annotations.NonNull View itemView, InsideListener2 insideListener2) {
            super(itemView);
            this.insideListener2 = insideListener2;
            textView = itemView.findViewById(R.id.items_now);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            insideListener2.getInside2(getAdapterPosition());
        }
        public interface InsideListener2{
            void getInside2(int position);
        }
    }

    public void setCategoriesList2(List<String> categoriesList2){
        this.categoriesList2 = categoriesList2;
        notifyDataSetChanged();
    }
}
