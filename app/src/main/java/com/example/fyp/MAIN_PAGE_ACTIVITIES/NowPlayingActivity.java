package com.example.fyp.MAIN_PAGE_ACTIVITIES;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fyp.VOLLEY.AnimeMainAdapter;
import com.example.fyp.VOLLEY.AnimeMainModule;
import com.example.fyp.R;
import com.example.fyp.VOLLEY.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAll;
    private RequestQueue requestQueue;

    ProgressBar progressBar;
    private List<AnimeMainModule> animeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        progressBar = findViewById(R.id.progressBarAll);
        progressBar.setVisibility(View.VISIBLE);

        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(view -> onBackPressed());

        recyclerViewAll = findViewById(R.id.recyclerViewAll);
        recyclerViewAll.setHasFixedSize(true);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        animeList = new ArrayList<>();
        fetchAnimes();
    }

    private void fetchAnimes() {

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=46fd53a3c0ad52d081ffe85fb24fccd0&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String original_title = jsonObject.getString("original_title");
                    String title = jsonObject.getString("title");
                    String poster_path = jsonObject.getString("backdrop_path");
                    String backdrop_path = jsonObject.getString("poster_path");
                    String release_date = jsonObject.getString("release_date");
                    String overview = jsonObject.getString("overview");
                    String vote_average = jsonObject.getString("vote_average");
                    String vote_count = jsonObject.getString("vote_count");
                    AnimeMainModule anime = new AnimeMainModule(original_title, title, poster_path, backdrop_path, release_date, overview, vote_average, vote_count);
                    animeList.add(anime);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AnimeMainAdapter adapter = new AnimeMainAdapter(NowPlayingActivity.this, animeList);
            recyclerViewAll.setAdapter(adapter);
        }, error -> {
            Toast.makeText(NowPlayingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        });
        requestQueue.add(jsonObjectRequest);
    }
}