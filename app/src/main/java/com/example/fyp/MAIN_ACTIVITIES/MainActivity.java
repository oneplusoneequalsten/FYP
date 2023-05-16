package com.example.fyp.MAIN_ACTIVITIES;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fyp.AUTHENTICATION.LoginActivity;
import com.example.fyp.MAIN_PAGE_ACTIVITIES.NowPlayingActivity;
import com.example.fyp.MAIN_PAGE_ACTIVITIES.TopActivity;
import com.example.fyp.MAIN_PAGE_ACTIVITIES.UpcomingActivity;
import com.example.fyp.MAIN_PAGE_ACTIVITIES.ViewActivity;
import com.example.fyp.R;
import com.example.fyp.VOLLEY.AnimeMainAdapter;
import com.example.fyp.VOLLEY.AnimeMainModule;
import com.example.fyp.VOLLEY.VolleySingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    RequestQueue requestQueue;


    FirebaseFirestore firebaseFirestore;

    ProgressBar progressBar;

    String userId;

    TextView user;
    private List<AnimeMainModule> animeList;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
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
            Intent intent = new Intent(this, NowPlayingActivity.class);
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

        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setQueryHint("Click in the center of search bar");
        searchView.setOnClickListener(view -> {
            Intent intent = new Intent(this, SearchViewActivity.class);
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

    private void fetchAnimes() {

        String url = "https://api.themoviedb.org/3/movie/popular?api_key=46fd53a3c0ad52d081ffe85fb24fccd0&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");

                for (int i = 0; i < jsonArray.length() - 15; i++) {
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
            AnimeMainAdapter adapter = new AnimeMainAdapter(MainActivity.this, animeList);
            recyclerView.setAdapter(adapter);
        }, error -> {
            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (firebaseAuth == null || currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            firebaseFirestore = FirebaseFirestore.getInstance();
            user = findViewById(R.id.user);
            userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

            DocumentReference documentReference = firebaseFirestore.collection("User").document(userId);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    assert documentSnapshot != null;
                    user.setText(documentSnapshot.getString("name"));
                }
            });
        }
    }
}