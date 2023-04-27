package com.example.fyp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeHolder> {
    private final Context context;
    private final List<AnimeMainModule> animeList;

    public AnimeAdapter(Context context, List<AnimeMainModule> animes) {
        this.context = context;
        animeList = animes;
    }

    @NonNull
    @Override
    public AnimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new AnimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeHolder holder, int position) {

        AnimeMainModule anime = animeList.get(position);
        holder.title.setText(anime.getOriginalTitle());
        holder.vote.setText(anime.getVoteAverage());
        holder.count.setText(anime.getVoteCount());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + anime.getPosterPath()).into(holder.animeImg);

        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("title", anime.getOriginalTitle());
            bundle.putString("vote", anime.getVoteAverage());
            bundle.putString("animeImg", anime.getPosterPath());
            bundle.putString("count", anime.getVoteCount());

            intent.putExtras(bundle);

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class AnimeHolder extends RecyclerView.ViewHolder {



        private CardView cardView;
        ImageView animeImg;
        TextView title, vote, count;
        ConstraintLayout constraintLayout;

        public AnimeHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            animeImg = itemView.findViewById(R.id.animeImg);
            title = itemView.findViewById(R.id.animeTitle);
            vote = itemView.findViewById(R.id.releasedDate);
            count = itemView.findViewById(R.id.voteCount);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}