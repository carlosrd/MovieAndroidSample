package com.carlosrd.movieapp.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PopularMoviesResponse  {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private ArrayList<PopularMovie> popularMovieEntities;

    public PopularMoviesResponse(int page, int totalResults, int totalPages,
                                 ArrayList<PopularMovie> popularMovieEntities) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.popularMovieEntities = popularMovieEntities;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<PopularMovie> getPopularMovies() {
        return popularMovieEntities;
    }

    // ************************************************

    public class PopularMovie {

        @SerializedName("poster_path")
        private String posterPath;

        @SerializedName("adult")
        private boolean adult;

        @SerializedName("overview")
        private String overview;

        @SerializedName("release_date")
        private String releaseDate;

        @SerializedName("genre_ids")
        private ArrayList<Long> genreIds;

        @SerializedName("id")
        private long id;

        @SerializedName("original_language")
        private String originalLanguage;

        @SerializedName("original_title")
        private String originalTitle;

        @SerializedName("title")
        private String title;

        @SerializedName("backdrop_path")
        private String backdropPath;


        public PopularMovie(String posterPath, boolean adult, String overview, String releaseDate,
                                  ArrayList<Long> genreIds, long id, String originalLanguage,
                                  String originalTitle, String title, String backdropPath) {
            this.posterPath = posterPath;
            this.adult = adult;
            this.overview = overview;
            this.releaseDate = releaseDate;
            this.genreIds = genreIds;
            this.id = id;
            this.originalLanguage = originalLanguage;
            this.originalTitle = originalTitle;
            this.title = title;
            this.backdropPath = backdropPath;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public boolean isAdult() {
            return adult;
        }

        public String getOverview() {
            return overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public ArrayList<Long> getGenreIds() {
            return genreIds;
        }

        public long getId() {
            return id;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public String getTitle() {
            return title;
        }

        public String getBackdropPath() {
            return backdropPath;
        }

    }

}
