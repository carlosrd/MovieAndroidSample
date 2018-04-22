package com.carlosrd.movieapp.data.remote.model.converters;

import com.carlosrd.movieapp.data.remote.model.ConfigurationResponse;
import com.carlosrd.movieapp.data.remote.model.MovieDetailsResponse;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.model.MovieGenre;

import java.util.ArrayList;

public class MovieDetailsResponseMapper {

    private static MovieDetailsResponseMapper INSTANCE;

    public static MovieDetailsResponseMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieDetailsResponseMapper();
        }
        return INSTANCE;
    }

    private MovieDetailsResponseMapper() {}

    // Data -> Domain
    public Movie transform(MovieDetailsResponse response, ConfigurationResponse configuration) {

        // Convertimos la lista de generos
        ArrayList<MovieGenre> genres = new ArrayList<>();

        for (int i = 0; i < response.getGenres().size(); i++){
            genres.add(new MovieGenre(
                    response.getGenres().get(i).getId(),
                    response.getGenres().get(i).getName()
                    )
            );
        }

        // Parseamos las URLS de imagenes
        String posterPath = configuration.getImagesConfig().getSecureBaseURL();
        posterPath += configuration.getImagesConfig().getPosterSizes().get(4);
        posterPath += response.getPosterPath();

        String backdropPath = configuration.getImagesConfig().getSecureBaseURL();
        backdropPath += configuration.getImagesConfig().getBackdropSizes().get(1);
        backdropPath += response.getBackdropPath();

        return new Movie(response.getId(),
                response.isAdult(),
                backdropPath,
                response.getBudget(),
                genres,
                response.getHomepage(),
                response.getOriginalLanguage(),
                response.getOriginalTitle(),
                response.getOverview(),
                posterPath,
                response.getReleaseDate(),
                response.getRevenue(),
                response.getRuntime(),
                response.getStatus(),
                response.getTagline(),
                response.getTitle(),
                false);

    }


}
