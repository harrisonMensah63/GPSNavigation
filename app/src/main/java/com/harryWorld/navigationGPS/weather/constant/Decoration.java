package com.harryWorld.navigationGPS.weather.constant;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Decoration extends RecyclerView.ItemDecoration {
    private int decoration;

    public Decoration(int decoration) {
        this.decoration = decoration;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = decoration ;
    }
}