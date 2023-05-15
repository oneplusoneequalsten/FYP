package com.example.fyp.MAIN_ACTIVITIES;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookmarkActivity extends AppCompatActivity {

    CircleImageView facebook;
    CircleImageView instagram;
    CircleImageView discord;
    CircleImageView github;
    TextView checkForUpdates;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_about);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), SearchViewActivity.class));
                    finish();
                    return true;
                case R.id.bottom_play:
                    startActivity(new Intent(getApplicationContext(), TVActivity.class));
                    finish();
                    return true;
                case R.id.bottom_about:
                    return true;
            }
            return false;
        });

        checkForUpdates = findViewById(R.id.checkForUpdates);
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        discord = findViewById(R.id.discord);
        github = findViewById(R.id.github);

        checkForUpdates.setOnClickListener(view -> {
            Toast.makeText(this, "No new updates available", Toast.LENGTH_SHORT).show();
        });

        facebook.setOnClickListener(view -> {
            String url= "https://www.facebook.com/profile.php?id=100068263002574";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        instagram.setOnClickListener(view -> {
            String url= "https://www.instagram.com/aniket.pptx/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        discord.setOnClickListener(view -> {
            String url= "https://discord.com/channels/1107797317186232333/1107797318352244809";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        github.setOnClickListener(view -> {
            String url= "https://github.com/widok0";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}