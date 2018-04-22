package com.carlosrd.movieapp.presentation.model;

public class MovieListItem {

    private long id;

    private String title;

    private String genres;

    private String year;

    private String imageURL;

    public MovieListItem(long id, String title, String genres, String year, String imageURL) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.year = year;
        this.imageURL = imageURL;
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    public String getYear() {
        return year;
    }

    public String getImageURL() {
        return imageURL;
    }
    
}
