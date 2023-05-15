package com.example.fyp.VOLLEY;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.google.android.material.imageview.ShapeableImageView;

public class AutoScrollAdapter extends RecyclerView.Adapter<AutoScrollAdapter.ViewHolder> {

    View view;
    Context context;

    public AutoScrollAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_carousel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView shapeableImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImageView = itemView.findViewById(R.id.animeImg);
        }
    }
}