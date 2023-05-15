package com.example.fyp.OTHER_ACTIVITIES;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VideoViewActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        BottomNavigationView bottomNavigationViewVideo = findViewById(R.id.bottomNavigationVideo);
        bottomNavigationViewVideo.setSelectedItemId(R.id.bottom_watch);
        bottomNavigationViewVideo.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_info:
                    onBackPressed();
                    finish();
                    return true;
                case R.id.bottom_watch:
                    return true;
            }
            return false;
        });
    }
}