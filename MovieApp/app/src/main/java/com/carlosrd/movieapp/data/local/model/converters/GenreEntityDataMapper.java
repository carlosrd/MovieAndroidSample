package com.carlosrd.movieapp.data.local.model.converters;

import com.carlosrd.movieapp.data.local.model.GenreEntity;
import com.carlosrd.movieapp.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class GenreEntityDataMapper {


    private static GenreEntityDataMapper INSTANCE;

    public static GenreEntityDataMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GenreEntityDataMapper();
        }
        return INSTANCE;
    }

    private GenreEntityDataMapper() {}

    // Domain -> Data
    public List<GenreEntity> transform(Movie movie){

        // Convertimos la lista de generos
        List<GenreEntity> genres = new ArrayList<>();

        for (int i = 0; i < movie.getMovieGenres().size(); i++)
            genres.add(new GenreEntity(
                    movie.getMovieGenres().get(i).getId(),
                    movie.getMovieGenres().get(i).getName(),
                    movie.getId()));

        return genres;

    }
}
