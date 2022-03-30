package com.harryWorld.navigationGPS.map.recyclerVeiw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harryWorld.navigationGPS.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private static final String TAG = "CategoriesAdapter";
    private Context context;


    private List<String> categoriesList;

    public CategoriesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.categories_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(categoriesList.get(position));
        if (categoriesList.get(position).equals("Emergency")) {
            holder.imageView.setImageResource(R.drawable.emergency);

        }
        else if (categoriesList.get(position).equals("Eateries")){
            holder.imageView.setImageResource(R.drawable.restaurant);
        }
        else if (categoriesList.get(position).equals("Entertainment")){
            holder.imageView.setImageResource(R.drawable.entertainment);
        }
        else if (categoriesList.get(position).equals("Services")){
            holder.imageView.setImageResource(R.drawable.service);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;



        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.image_categories);
            textView = itemView.findViewById(R.id.text_categories1);
        }
    }

    public void setCategoriesList(List<String> categoriesList){
        this.categoriesList = categoriesList;
        notifyDataSetChanged();
    }


}
