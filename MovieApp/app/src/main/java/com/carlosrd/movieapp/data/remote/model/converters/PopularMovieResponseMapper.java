package com.carlosrd.movieapp.data.remote.model.converters;

import com.carlosrd.movieapp.data.remote.model.ConfigurationResponse;
import com.carlosrd.movieapp.data.remote.model.PopularMoviesResponse;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.model.MovieGenre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PopularMovieResponseMapper {

    private static PopularMovieResponseMapper INSTANCE;

    public static PopularMovieResponseMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PopularMovieResponseMapper();
        }
        return INSTANCE;
    }

    private PopularMovieResponseMapper() {}


    public ArrayList<Movie> transform(PopularMoviesResponse response,
                                      Map<Long,String> genresMaster,
                                      ConfigurationResponse configuration){

        ArrayList<Movie> list = new ArrayList<>();

        for (int i = 0; i < response.getPopularMovies().size(); i++ ){

            // Parsear el array de IDs de generos
            ArrayList<MovieGenre> genres = new ArrayList<>();

            for (int j = 0; j < response.getPopularMovies().get(i).getGenreIds().size(); j++){
                genres.add(new MovieGenre(
                        response.getPopularMovies().get(i).getGenreIds().get(j),
                        genresMaster.get(response.getPopularMovies().get(i).getGenreIds().get(j)))
                );
            }

            // Parsear las URLS de imagenes
            String backdropPath = configuration.getImagesConfig().getSecureBaseURL();
            backdropPath += configuration.getImagesConfig().getBackdropSizes().get(1);
            backdropPath += response.getPopularMovies().get(i).getBackdropPath();

            String posterPath = configuration.getImagesConfig().getSecureBaseURL();
            posterPath += configuration.getImagesConfig().getPosterSizes().get(4);
            posterPath += response.getPopularMovies().get(i).getPosterPath();


            // Añadir pelicula
            list.add(
                    new Movie(
                            response.getPopularMovies().get(i).getId(),
                            response.getPopularMovies().get(i).isAdult(),
                            backdropPath,
                            0,
                            genres,
                            "",
                            response.getPopularMovies().get(i).getOriginalLanguage(),
                            response.getPopularMovies().get(i).getOriginalTitle(),
                            response.getPopularMovies().get(i).getOverview(),
                            posterPath,
                            response.getPopularMovies().get(i).getReleaseDate(),
                            0,
                            0,
                            "",
                            "",
                            response.getPopularMovies().get(i).getTitle(),
                            false)
            );

        }


        return list;

    }


}
