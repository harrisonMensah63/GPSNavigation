package com.harryWorld.navigationGPS.map.recyclerVeiw.inside;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;

import java.util.List;

public class InsideAdapter3 extends RecyclerView.Adapter<InsideAdapter3.InsideVIewHolder3> {

    InsideVIewHolder3.InsideListener3 listener3;
    List<String> categoriesList3;

    public InsideAdapter3(InsideVIewHolder3.InsideListener3 listener3) {
        this.listener3 = listener3;
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public InsideVIewHolder3 onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inside,parent,false);
        return new InsideVIewHolder3(view, listener3);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull InsideVIewHolder3 holder, int position) {

        holder.textView.setText(categoriesList3.get(position));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class InsideVIewHolder3 extends RecyclerView.ViewHolder
    implements View.OnClickListener {

        TextView textView;
        InsideListener3 insidelistener3;
        public InsideVIewHolder3(@NonNull @io.reactivex.annotations.NonNull View itemView, InsideListener3 insideListener3) {
            super(itemView);
            this.insidelistener3 = insideListener3;
            textView = itemView.findViewById(R.id.items_now);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            insidelistener3.getInside3(getAdapterPosition());
        }
        public interface InsideListener3{
            void getInside3(int position);
        }
    }

    public void getCategoriesList3(List<String> categoriesList3){
        this.categoriesList3 = categoriesList3;
        notifyDataSetChanged();
    }
}
