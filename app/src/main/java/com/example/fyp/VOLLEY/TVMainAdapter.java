package com.example.fyp.VOLLEY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fyp.R;

import java.util.List;

public class TVMainAdapter extends RecyclerView.Adapter<TVMainAdapter.AnimeHolder> {
    private final Context context;
    private final List<TVModule> animeList;

    public TVMainAdapter(Context context, List<TVModule> animes) {
        this.context = context;
        animeList = animes;
    }

    @NonNull
    @Override
    public AnimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carousel, parent, false);
        return new AnimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeHolder holder, int position) {

        TVModule anime = animeList.get(position);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + anime.getPosterPath()).into(holder.animeImg);

        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailTVActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("title", anime.getOriginalTitle());
            bundle.putString("vote", anime.getVoteAverage());
            bundle.putString("animeImg", anime.getPosterPath());
            bundle.putString("posterImg", anime.getBackdropPath());
            bundle.putString("releaseDate", anime.getReleaseDatePath());
            bundle.putString("count", anime.getVoteCount());
            bundle.putString("overview", anime.getOverviewPath());;
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class AnimeHolder extends RecyclerView.ViewHolder {



        CardView cardView;
        ImageView animeImg;
        ConstraintLayout constraintLayout;

        public AnimeHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardViewCarousel);
            animeImg = itemView.findViewById(R.id.animeImgCarousel);
            constraintLayout = itemView.findViewById(R.id.recycler_view_carousel);
        }
    }
}
