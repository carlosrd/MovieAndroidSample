package com.carlosrd.movieapp.presentation.model;

import java.util.ArrayList;

public class MovieDetails {

    private boolean favorite;

    private String imageURL;

    private String title;

    private String originalTitle;

    private String genres;

    private String tagline;

    private String overview;

    private String runtime;

    private String releaseDate;

    /*
    this.budget = budget;

    this.homepage = homepage;
    this.originalLanguage = originalLanguage;

    this.revenue = revenue;
    this.status = status;

*/


    public MovieDetails(boolean favorite, String imageURL, String title, String originalTitle,
                        String genres, String tagline, String overview, String runtime, String releaseDate) {
        this.favorite = favorite;
        this.imageURL = imageURL;
        this.title = title;
        this.originalTitle = originalTitle;
        this.genres = genres;
        this.tagline = tagline;
        this.overview = overview;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
    }


    public boolean isFavorite() {
        return favorite;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getGenres() {
        return genres;
    }

    public String getTagline() {
        return tagline;
    }

    public String getOverview() {
        return overview;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
