package com.harryWorld.navigationGPS.map.recyclerVeiw;

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

import com.harryWorld.navigationGPS.R;
import com.harryWorld.navigationGPS.map.utils.Places;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private Context context;
    private List<Places> placesList;
    private SearchViewHolder.SearchListener listener;

    public SearchAdapter(Context context, SearchViewHolder.SearchListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.search_adapter_layout,parent,false);
        return new SearchViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {

        double latitude = placesList.get(position).getLatitude();
        latitude = Math.round(latitude*100000);
        latitude = latitude/100000;

        double longitude = placesList.get(position).getLongitude();
        longitude = Math.round(longitude*100000);
        longitude = longitude/100000;
        holder.cord.setText(latitude+","+longitude);

        Drawable drawable = ContextCompat.getDrawable(context,R.color.tab_light);
        holder.image.setImageDrawable(drawable);
            if(position == 0){
                holder.image.setImageResource(R.drawable.ic_home);
                holder.name.setText(R.string.home);
            }
            else if (position == 1){
                holder.image.setImageResource(R.drawable.ic_work);
                holder.name.setText(R.string.work);
            }
            else {
                holder.name.setText(placesList.get(position).getTitle());
                holder.image.setImageResource(R.drawable.star_24);
            }
    }

    @Override
    public int getItemCount() {
        if (placesList != null){
            return placesList.size();
        }
        return 0;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private ImageView image;
        private TextView name;
        private TextView cord;
        private SearchListener listener;
        public SearchViewHolder(@NonNull View itemView, SearchListener listener) {
            super(itemView);
            this.listener = listener;
            image = itemView.findViewById(R.id.search_image);
            name = itemView.findViewById(R.id.name);
            cord = itemView.findViewById(R.id.cord);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.getPosition(getAdapterPosition());
        }
        public interface SearchListener{
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
}
