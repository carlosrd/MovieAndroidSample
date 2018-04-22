package com.carlosrd.movieapp.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieGenresResponse {

    @SerializedName("genres")
    private ArrayList<MovieGenre> genres;

    public MovieGenresResponse(ArrayList<MovieGenre> genres) {
        this.genres = genres;
    }

    public List<MovieGenre> getGenres() {
        return genres;
    }

    public class MovieGenre {

        @SerializedName("id")
        private long id;
        @SerializedName("name")
        private String name;

        public MovieGenre(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
