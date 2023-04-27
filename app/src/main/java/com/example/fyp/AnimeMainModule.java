package com.example.fyp;

public class AnimeMainModule {

    private final String original_title;
    private final String poster_path;
    private final String vote_average;
    private final String vote_count;

    public AnimeMainModule(String original_title, String poster_path, String vote_average, String vote_count) {
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getOriginalTitle() {
        return original_title;
    }
    public String getPosterPath() {
        return poster_path;
    }
    public String getVoteAverage() {
        return vote_average;
    }
    public String getVoteCount() {
        return vote_count;
    }
}