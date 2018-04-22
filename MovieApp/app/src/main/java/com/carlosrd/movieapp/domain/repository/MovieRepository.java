package com.carlosrd.movieapp.domain.repository;

import com.carlosrd.movieapp.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

public interface MovieRepository {

    interface LoadPopularMoviesCallback {

        void onMoviesLoaded(ArrayList<Movie> popularMoviesList);

        void onError(int statusCode);

    }

    interface LoadFavouriteMoviesCallback {

        void onMoviesLoaded(List<Movie> favouriteMoviesList);

        void onNoDataAvailable();

        void onError(String message);

    }

    interface LoadMovieDetailsCallback {

        void onMovieDetailsLoaded(Movie movie);

        void onNoDataAvailable();

        void onError(int statusCode);

    }

    void loadPopularMovies(LoadPopularMoviesCallback callback);

    void loadFavoriteMovies(LoadFavouriteMoviesCallback callback);

    void loadMovieDetails(long movieId, LoadMovieDetailsCallback callback);

    void saveAsFavoriteMovie(long movieId);

    void saveAsFavoriteMovie(Movie movie);

    void deleteAsFavoriteMovie(long movieId);

}
