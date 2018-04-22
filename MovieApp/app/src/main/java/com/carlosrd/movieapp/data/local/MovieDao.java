package com.carlosrd.movieapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.carlosrd.movieapp.data.local.model.GenreEntity;
import com.carlosrd.movieapp.data.local.model.MovieEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MovieDao {


    @Query("SELECT * FROM favorites_movies")
    List<MovieEntity> getFavouriteMovies();

    @Query("SELECT * FROM favorites_movies WHERE id = :movieId")
    MovieEntity getMovieById(long movieId);

    @Query("SELECT * FROM favorites_movies_genres ORDER BY movie_id")
    List<GenreEntity> getFavouriteMovieGenres();

    @Query("SELECT * FROM favorites_movies_genres WHERE movie_id = :movieId")
    List<GenreEntity> getMovieGenres(long movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(MovieEntity movie, List<GenreEntity> movieGenres);

    @Query("DELETE FROM favorites_movies WHERE id = :movieId")
    void deleteFavoriteMovieById(long movieId);

}
