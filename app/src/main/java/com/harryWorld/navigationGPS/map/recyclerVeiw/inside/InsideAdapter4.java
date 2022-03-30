package com.harryWorld.navigationGPS.map.recyclerVeiw.inside;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;

import java.util.List;

public class InsideAdapter4 extends RecyclerView.Adapter<InsideAdapter4.InsideVIewHolder4> {

    InsideVIewHolder4.InsideListener4 listener4;
    List<String> categoriesList4;

    public InsideAdapter4(InsideVIewHolder4.InsideListener4 listener4) {
        this.listener4 = listener4;
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public InsideVIewHolder4 onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inside,parent,false);
        return new InsideVIewHolder4(view,listener4);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull InsideVIewHolder4 holder, int position) {
        holder.textView.setText(categoriesList4.get(position));
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class InsideVIewHolder4 extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        InsideListener4 insideListener4;

        public InsideVIewHolder4(@NonNull @io.reactivex.annotations.NonNull View itemView, InsideListener4 insideListener4) {
            super(itemView);
            this.insideListener4 = insideListener4;
            textView = itemView.findViewById(R.id.items_now);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            insideListener4.getInside4(getAdapterPosition());
        }

        public interface InsideListener4{
            void getInside4(int position);
        }
    }

    public void getCategoriesList4(List<String> categoriesList4){
        this.categoriesList4 = categoriesList4;
        notifyDataSetChanged();
    }
}
