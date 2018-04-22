package com.carlosrd.movieapp.data.local.model.converters;

import com.carlosrd.movieapp.data.local.model.GenreEntity;
import com.carlosrd.movieapp.data.local.model.MovieEntity;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.model.MovieGenre;

import java.util.ArrayList;
import java.util.List;

public class MovieEntityDataMapper {


    private static MovieEntityDataMapper INSTANCE;

    public static MovieEntityDataMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieEntityDataMapper();
        }
        return INSTANCE;
    }

    private MovieEntityDataMapper() {}

    // Data -> Domain
    public ArrayList<Movie> transform(List<MovieEntity> movies, List<GenreEntity> movieGenres){

        ArrayList<Movie> list = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++ ){

            ArrayList<MovieGenre> genres = new ArrayList<>();
            boolean readingGenres = false; // Para saber cuando dejar de leer generos (estan ordenados)

            for (int j = 0; j < movieGenres.size(); j++){

                if (movieGenres.get(j).getMovieId() == movies.get(i).getId()){
                    genres.add(new MovieGenre(
                            movieGenres.get(j).getId(),
                            movieGenres.get(j).getName())
                    );
                    readingGenres = true;
                } else {
                    if (readingGenres)
                        break; // No seguimos, leyendo, pues no hay mas (los recogimos ordenados)
                }


            }

            list.add(
                    new Movie(
                            movies.get(i).getId(),
                            movies.get(i).isAdult(),
                            movies.get(i).getBackdropPath(),
                            movies.get(i).getBudget(),
                            genres,
                            movies.get(i).getHomepage(),
                            movies.get(i).getOriginalLanguage(),
                            movies.get(i).getOriginalTitle(),
                            movies.get(i).getOverview(),
                            movies.get(i).getPosterPath(),
                            movies.get(i).getReleaseDate(),
                            movies.get(i).getRevenue(),
                            movies.get(i).getRuntime(),
                            movies.get(i).getStatus(),
                            movies.get(i).getTagline(),
                            movies.get(i).getTitle(),
                            true)
            );

        }


        return list;

    }

    // Data -> Domain
    public Movie transform(MovieEntity movieEntity, List<GenreEntity> genresEntity) {

        // Convertimos la lista de generos
        ArrayList<MovieGenre> genres = new ArrayList<>();

        for (int i = 0; i < genresEntity.size(); i++){
            genres.add(new MovieGenre(
                    genresEntity.get(i).getId(),
                    genresEntity.get(i).getName())
            );
        }

        return new Movie(movieEntity.getId(),
                movieEntity.isAdult(),
                movieEntity.getBackdropPath(),
                movieEntity.getBudget(),
                genres,
                movieEntity.getHomepage(),
                movieEntity.getOriginalLanguage(),
                movieEntity.getOriginalTitle(),
                movieEntity.getOverview(),
                movieEntity.getPosterPath(),
                movieEntity.getReleaseDate(),
                movieEntity.getRevenue(),
                movieEntity.getRuntime(),
                movieEntity.getStatus(),
                movieEntity.getTagline(),
                movieEntity.getTitle(),
                true
            );




    }

    // Domain -> Data
    public MovieEntity transform(Movie movie){

        return new MovieEntity(movie.getId(),
                movie.isAdult(),
                movie.getBackdropPath(),
                movie.getBudget(),
                movie.getHomepage(),
                movie.getOriginalLanguage(),
                movie.getOriginalTitle(),
                movie.getOverview(),
                movie.getPosterPath(),
                movie.getReleaseDate(),
                movie.getRevenue(),
                movie.getRuntime(),
                movie.getStatus(),
                movie.getTagline(),
                movie.getTitle()
        );

    }
}
