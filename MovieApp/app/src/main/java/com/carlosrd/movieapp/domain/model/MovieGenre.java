package com.carlosrd.movieapp.domain.model;

public class MovieGenre {

    private long id;

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
