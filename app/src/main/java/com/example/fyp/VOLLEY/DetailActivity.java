package com.example.fyp.VOLLEY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fyp.R;
import com.example.fyp.OTHER_ACTIVITIES.VideoViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        BottomNavigationView bottomNavigationViewVideo = findViewById(R.id.bottomNavigationVideo);
        bottomNavigationViewVideo.setSelectedItemId(R.id.bottom_info);
        bottomNavigationViewVideo.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_info:
                    return true;
                case R.id.bottom_watch:
                    startActivity(new Intent(getApplicationContext(), VideoViewActivity.class));
                    finish();
                    return true;
            }
            return false;
        });

        Button backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(view -> onBackPressed());

        ImageView animeImg = findViewById(R.id.poster_image);
        TextView animeTitle = findViewById(R.id.animeTitle);
        TextView releaseDate = findViewById(R.id.releaseDate);
        TextView overview = findViewById(R.id.overview);
        TextView altTitleMain = findViewById(R.id.altTitleMain);
        TextView ratingMain = findViewById(R.id.ratingMain);
        ImageView posterImg = findViewById(R.id.poster_image_small);
        Bundle bundle = getIntent().getExtras();

        String mTitle = bundle.getString("title");
        String mReleaseDate = bundle.getString("releaseDate");
        String mOverview = bundle.getString("overview");
        String mAltTitle = bundle.getString("title");
        String mRating = bundle.getString("vote");
        String mAnimeImg = bundle.getString("animeImg");
        String mPosterImg = bundle.getString("posterImg");

        Glide.with(this).load("https://image.tmdb.org/t/p/original" + mAnimeImg).into(animeImg);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + mPosterImg).into(posterImg);
        animeTitle.setText(mTitle);
        releaseDate.setText(mReleaseDate);
        overview.setText(mOverview);
        altTitleMain.setText(mAltTitle);
        ratingMain.setText(mRating);
    }
}