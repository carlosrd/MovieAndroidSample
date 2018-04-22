package com.carlosrd.movieapp.presentation.model.converters;

import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.presentation.model.MovieDetails;

import java.util.ArrayList;

public class MovieDetailsMapper {

    private static MovieDetailsMapper INSTANCE;

    public static MovieDetailsMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieDetailsMapper();
        }
        return INSTANCE;
    }

    private MovieDetailsMapper() {}


    public MovieDetails transform(Movie movie){

        // Convertimos la lista de generos
        String genres = "";

        for (int i = 0; i < movie.getMovieGenres().size(); i++)
            genres += movie.getMovieGenres().get(i).getName() + ", ";

        genres = genres.substring(0,genres.length() - 2);

        String originalTitle = movie.getOriginalTitle() +
                " (" + movie.getReleaseDate().substring(0,4) + ")";

        return new MovieDetails(
                movie.isFavorite(),
                movie.getBackdropPath(),
                movie.getTitle(),
                originalTitle,
                genres,
                movie.getTagline(),
                movie.getOverview(),
                movie.getRuntime() + " min.",
                movie.getReleaseDate()
        );

    }


}
