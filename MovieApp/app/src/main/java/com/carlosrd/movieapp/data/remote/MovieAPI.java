package com.carlosrd.movieapp.data.remote;

import com.carlosrd.movieapp.data.remote.model.ConfigurationResponse;
import com.carlosrd.movieapp.data.remote.model.MovieDetailsResponse;
import com.carlosrd.movieapp.data.remote.model.MovieGenresResponse;
import com.carlosrd.movieapp.data.remote.model.PopularMoviesResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MovieAPI {

    String API_BASE_URL = "https://api.themoviedb.org/3/";

    String API_KEY = "b66ffea8276ce576d60df52600822c88";

    // Consultar peliculas populares
    @GET("movie/popular")
    Call<PopularMoviesResponse> getPopularMovies(
            @QueryMap Map<String, String> options
    );

    // Consultar detalles de pelicula
    @GET("movie/{movie_id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("movie_id") long id,
                                               @QueryMap Map<String, String> options
    );

    // Consultar lista maestra de generos de peliculas
    @GET("genre/movie/list")
    Call<MovieGenresResponse> getMovieGenresList(
            @QueryMap Map<String, String> options
    );

    // Consultar configuracion
    @GET("configuration")
    Call<ConfigurationResponse> getAPIConfig(@Query("api_key") String apiKey);

}
