package com.example.fyp.MAIN_ACTIVITIES;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fyp.R;
import com.example.fyp.VOLLEY.AutoScrollAdapter;
import com.example.fyp.VOLLEY.RecyclerMainModule;
import com.example.fyp.VOLLEY.RecyclerViewAdapter;
import com.example.fyp.VOLLEY.TVMainAdapter;
import com.example.fyp.VOLLEY.TVModule;
import com.example.fyp.VOLLEY.VolleySingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TVActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private List<TVModule> animeList;
    private List<RecyclerMainModule> movieList;

    RecyclerView carousel;

    RecyclerView popularMovies;
    AutoScrollAdapter autoScrollAdapter;
    LinearLayoutManager linearLayoutManager;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        popularMovies = findViewById(R.id.popularMovies);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularMovies.setLayoutManager(linearLayoutManager);

        carousel = findViewById(R.id.carousel);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        carousel.setLayoutManager(linearLayoutManager);

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setQueryHint("Click in the center of search bar");
        searchView.setOnClickListener(view -> {
            Intent intent = new Intent(this, SearchViewActivity.class);
            startActivity(intent);
        });

        animeList = new ArrayList<>();
        movieList = new ArrayList<>();
        fetchAnimesCarousel();
        fetchAnimes();
        setRV();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_play);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), SearchViewActivity.class));
                    finish();
                    return true;
                case R.id.bottom_play:
                    return true;
                case R.id.bottom_about:
                    startActivity(new Intent(getApplicationContext(), BookmarkActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void fetchAnimes() {

        String url = "https://api.themoviedb.org/3/tv/popular?api_key=46fd53a3c0ad52d081ffe85fb24fccd0&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String original_title = jsonObject.getString("name");
                    String title = jsonObject.getString("original_name");
                    String poster_path = jsonObject.getString("backdrop_path");
                    String backdrop_path = jsonObject.getString("poster_path");
                    String release_date = jsonObject.getString("first_air_date");
                    String overview = jsonObject.getString("overview");
                    String vote_average = jsonObject.getString("vote_average");
                    String vote_count = jsonObject.getString("vote_count");
                    RecyclerMainModule anime = new RecyclerMainModule(original_title, title, poster_path, backdrop_path, release_date, overview, vote_average, vote_count);
                    movieList.add(anime);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(TVActivity.this, movieList);
            popularMovies.setAdapter(adapter);
        }, error -> {
            Toast.makeText(TVActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void fetchAnimesCarousel() {

        String url = "https://api.themoviedb.org/3/tv/airing_today?api_key=46fd53a3c0ad52d081ffe85fb24fccd0&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");

                for (int i =1; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String original_title = jsonObject.getString("name");
                    String title = jsonObject.getString("original_name");
                    String poster_path = jsonObject.getString("poster_path");
                    String backdrop_path = jsonObject.getString("backdrop_path");
                    String release_date = jsonObject.getString("first_air_date");
                    String overview = jsonObject.getString("overview");
                    String vote_average = jsonObject.getString("vote_average");
                    String vote_count = jsonObject.getString("vote_count");
                    TVModule anime = new TVModule(original_title, title, poster_path, backdrop_path, release_date, overview, vote_average, vote_count);
                    animeList.add(anime);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TVMainAdapter adapter = new TVMainAdapter(TVActivity.this, animeList);
            carousel.setAdapter(adapter);
        }, error -> Toast.makeText(TVActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);
    }

    private void setRV() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        carousel.setLayoutManager(linearLayoutManager);

        autoScrollAdapter = new AutoScrollAdapter(this);
        carousel.setAdapter(autoScrollAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(carousel);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (autoScrollAdapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(carousel, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (autoScrollAdapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(carousel, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}