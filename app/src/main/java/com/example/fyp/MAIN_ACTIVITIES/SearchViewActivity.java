package com.example.fyp.MAIN_ACTIVITIES;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchViewActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_search);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                    return true;
                case R.id.bottom_search:
                    return true;
                case R.id.bottom_play:
                    startActivity(new Intent(getApplicationContext(), TVActivity.class));
                    finish();
                    return true;
                case R.id.bottom_about:
                    startActivity(new Intent(getApplicationContext(), BookmarkActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}