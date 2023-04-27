package com.example.fyp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String searchQuery = "https://gogoanime.consumet.stream/anime-details";

        ImageView animeImg = findViewById(R.id.poster_image);
        TextView releasedDate = findViewById(R.id.releasedDate);
        TextView animeTitle = findViewById(R.id.animeTitle);
        Bundle bundle = getIntent().getExtras();

        String mTitle = bundle.getString("title");
        String mAnimeImg = bundle.getString("animeImg");
        String mReleasedDateEp = bundle.getString("releasedDate");

        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + mAnimeImg).into(animeImg);
        releasedDate.setText(mReleasedDateEp);
        animeTitle.setText(mTitle);
    }
}