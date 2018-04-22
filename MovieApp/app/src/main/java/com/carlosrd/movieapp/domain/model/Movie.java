package com.carlosrd.movieapp.domain.model;

import java.util.ArrayList;

public class Movie {

    private long id;
    boolean adult;
    private String backdropPath;
    private int budget;
    private ArrayList<MovieGenre> movieGenres;
    private String homepage;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private int revenue;
    private int runtime;
    private String status;
    private String tagline;
    private String title;

    private boolean favorite;

    public Movie(long id, boolean adult, String backdropPath, int budget, ArrayList<MovieGenre> movieGenres,
                 String homepage, String originalLanguage, String originalTitle, String overview,
                 String posterPath, String releaseDate, int revenue, int runtime, String status,
                 String tagline, String title, boolean favorite) {

        this.id = id;
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.budget = budget;
        this.movieGenres = movieGenres;
        this.homepage = homepage;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.title = title;

        this.favorite = favorite;

    }

    public long getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public ArrayList<MovieGenre> getMovieGenres() {
        return movieGenres;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
