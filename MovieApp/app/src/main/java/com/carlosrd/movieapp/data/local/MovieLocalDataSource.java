package com.carlosrd.movieapp.data.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.carlosrd.movieapp.data.local.model.GenreEntity;
import com.carlosrd.movieapp.data.local.model.MovieEntity;
import com.carlosrd.movieapp.data.local.model.converters.GenreEntityDataMapper;
import com.carlosrd.movieapp.data.local.model.converters.MovieEntityDataMapper;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;

import java.util.List;

public class MovieLocalDataSource implements MovieRepository {

    private static volatile MovieLocalDataSource INSTANCE;

    private MovieDao mMovieDao;

    // Prevent direct instantiation.
    private MovieLocalDataSource(@NonNull MovieDao movieDao) {
        mMovieDao = movieDao;
    }

    public static MovieLocalDataSource getInstance(@NonNull MovieDao movieDao) {
        if (INSTANCE == null) {
            synchronized (MovieLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieLocalDataSource(movieDao);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public void loadPopularMovies(LoadPopularMoviesCallback callback) { }

    @Override
    public void loadFavoriteMovies(final LoadFavouriteMoviesCallback callback) {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                List<MovieEntity> movieList = mMovieDao.getFavouriteMovies();
                List<GenreEntity> movieGenresList =  mMovieDao.getFavouriteMovieGenres();
                if (movieList.isEmpty())
                    callback.onNoDataAvailable();
                else
                    callback.onMoviesLoaded(
                            MovieEntityDataMapper.getInstance()
                                    .transform(movieList, movieGenresList));
            }
        };

        new Thread(runnable).start();
    }

    @Override
    public void loadMovieDetails(final long movieId, final LoadMovieDetailsCallback callback) {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                MovieEntity movieDetails = mMovieDao.getMovieById(movieId);

                if (movieDetails == null) {
                    callback.onNoDataAvailable();
                } else {

                    List<GenreEntity> movieGenres = mMovieDao.getMovieGenres(movieId);

                    callback.onMovieDetailsLoaded(
                            MovieEntityDataMapper.getInstance().transform(movieDetails, movieGenres));
                }
            }
        };

        new Thread(runnable).start();
    }

    @Override
    public void saveAsFavoriteMovie(long movieId) { }

    @Override
    public void saveAsFavoriteMovie(final Movie movie) {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                MovieEntity movieEntity = MovieEntityDataMapper.getInstance().transform(movie);
                List<GenreEntity> genres = GenreEntityDataMapper.getInstance().transform(movie);

                mMovieDao.insertFavoriteMovie(movieEntity,genres);

            }
        };

        new Thread(runnable).start();
    }

    @Override
    public void deleteAsFavoriteMovie(final long movieId) {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                mMovieDao.deleteFavoriteMovieById(movieId);

            }
        };

        new Thread(runnable).start();

    }

}
