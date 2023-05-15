package com.example.fyp.VOLLEY;

public class AnimeMainModule {

    private final String original_title;
    private final String poster_path;
    private final String backdrop_path;
    private final String release_date;
    private final String overview;
    private final String vote_average;
    private final String vote_count;

    public AnimeMainModule(String original_title, String poster_path, String backdrop_path, String release_date, String overview, String vote_average, String vote_count) {
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;

    }

    public String getOriginalTitle() {
        return original_title;
    }
    public String getPosterPath() {
        return poster_path;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }
    public String getReleaseDatePath() {
        return release_date;
    }

    public String getOverviewPath() {
        return overview;
    }
    public String getVoteAverage() {
        return vote_average;
    }
    public String getVoteCount() {
        return vote_count;
    }

}

