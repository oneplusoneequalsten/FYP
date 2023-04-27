package com.example.fyp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    ProgressBar progressBar;
    private List<AnimeMainModule> animeList;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBae);
        progressBar.setVisibility(View.VISIBLE);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        animeList = new ArrayList<>();
        fetchAnimes();

        CircleImageView upComingCircle = findViewById(R.id.upComingCircle);
        CircleImageView latestCircle = findViewById(R.id.latestCircle);
        CircleImageView topCircle = findViewById(R.id.topCircle);

        upComingCircle.setOnClickListener(view -> {
            Intent intent = new Intent(this, UpcomingActivity.class);
            startActivity(intent);
        });

        latestCircle.setOnClickListener(view -> {
            Intent intent = new Intent(this, LatestActivity.class);
            startActivity(intent);
        });

        topCircle.setOnClickListener(view -> {
            Intent intent = new Intent(this, TopActivity.class);
            startActivity(intent);
        });

        TextView viewAll = findViewById(R.id.viewAll);
        viewAll.setOnClickListener(view -> {
            Intent intent = new Intent(this, ViewActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), SearchViewActivity.class));
                    finish();
                    return true;
                case R.id.bottom_play:
                    startActivity(new Intent(getApplicationContext(), PlayActivity.class));
                    finish();
                    return true;
                case R.id.bottom_bookmark:
                    startActivity(new Intent(getApplicationContext(), BookmarkActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void fetchAnimes() {

        String url = "https://api.themoviedb.org/3/movie/popular?api_key=46fd53a3c0ad52d081ffe85fb24fccd0&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");

                for (int i = 0; i < jsonArray.length() - 15; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String original_title = jsonObject.getString("original_title");
                    String poster_path = jsonObject.getString("poster_path");
                    String vote_average = jsonObject.getString("vote_average");
                    String vote_count = jsonObject.getString("vote_count");
                    AnimeMainModule anime = new AnimeMainModule(original_title, poster_path, vote_average, vote_count);
                    animeList.add(anime);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AnimeAdapter adapter = new AnimeAdapter(MainActivity.this, animeList);
            recyclerView.setAdapter(adapter);
        }, error -> {
            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        });
        requestQueue.add(jsonObjectRequest);
    }
}