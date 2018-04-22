package com.carlosrd.movieapp.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRepositoryImpl implements MovieRepository {

    private static MovieRepositoryImpl INSTANCE;

    private final MovieRepository mMovieRemoteDataSource;
    private final MovieRepository mMovieLocalDataSource;

    private Map<Long, Movie> mCachedMovies;

    // Prevent direct instantiation
    private MovieRepositoryImpl(MovieRepository movieRemoteDataSource,
                                MovieRepository movieLocalDataSource) {

        this.mMovieRemoteDataSource = movieRemoteDataSource;
        this.mMovieLocalDataSource = movieLocalDataSource;

    }

    public static MovieRepositoryImpl getInstance(MovieRepository movieRemoteDataSource,
                                                  MovieRepository movieLocalDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource);
        }
        return INSTANCE;

    }


    @Override
    public void loadPopularMovies(final LoadPopularMoviesCallback callback) {

        mMovieRemoteDataSource.loadPopularMovies(new LoadPopularMoviesCallback() {
            @Override
            public void onMoviesLoaded(ArrayList<Movie> popularMoviesList) {
                refreshCache(popularMoviesList);
                callback.onMoviesLoaded(popularMoviesList);
            }

            @Override
            public void onError(int statusCode) {

                callback.onError(statusCode);
            }
        }

        );


    }

    @Override
    public void loadFavoriteMovies(final LoadFavouriteMoviesCallback callback) {

        mMovieLocalDataSource.loadFavoriteMovies(callback);

    }

    @Override
    public void loadMovieDetails(final long movieId, final LoadMovieDetailsCallback callback) {

        // Probamos a cargar desde local por si esta marcada como favorita
        mMovieLocalDataSource.loadMovieDetails(movieId, new LoadMovieDetailsCallback() {
            @Override
            public void onMovieDetailsLoaded(Movie movie) {
                // Pasamos pelicula guardada en local
                callback.onMovieDetailsLoaded(movie);

            }

            @Override
            public void onNoDataAvailable() {
                // Si no hay datos, entonces los cargamos desde la API
                mMovieRemoteDataSource.loadMovieDetails(movieId, new LoadMovieDetailsCallback() {
                            @Override
                            public void onMovieDetailsLoaded(Movie movie) {

                                // Actualizamos las peliculas cacheadas con mas datos
                                if (mCachedMovies.containsKey(movieId)){
                                    mCachedMovies.remove(movieId);
                                    mCachedMovies.put(movieId, movie);
                                }

                                // Devolvemos pelicula cargada
                                callback.onMovieDetailsLoaded(movie);

                            }

                            @Override
                            public void onNoDataAvailable() { }

                            @Override
                            public void onError(int statusCode) {
                                callback.onError(statusCode);
                            }
                        });
            }

            @Override
            public void onError(int statusCode) { }

        });

    }

    @Override
    public void saveAsFavoriteMovie(long movieId) {

        Movie movie = getMovieWithId(movieId);

        if (movie != null)
            saveAsFavoriteMovie(movie);
    }

    @Override
    public void saveAsFavoriteMovie(Movie movie) {

        mMovieLocalDataSource.saveAsFavoriteMovie(movie);

    }

    @Override
    public void deleteAsFavoriteMovie(long movieId) {

        mMovieLocalDataSource.deleteAsFavoriteMovie(movieId);


    }

    private void refreshCache(List<Movie> movieList) {

        if (mCachedMovies == null) {
            mCachedMovies = new HashMap<>();
        }

        mCachedMovies.clear();

        for (Movie movie : movieList) {
            mCachedMovies.put(movie.getId(), movie);
        }

    }

    private Movie getMovieWithId(long id) {
        if (mCachedMovies == null || mCachedMovies.isEmpty()) {
            return null;
        } else {
            return mCachedMovies.get(id);
        }
    }

}
