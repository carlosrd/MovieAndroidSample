package com.carlosrd.movieapp.data.remote;

import com.carlosrd.movieapp.data.remote.model.ConfigurationResponse;
import com.carlosrd.movieapp.data.remote.model.MovieDetailsResponse;
import com.carlosrd.movieapp.data.remote.model.MovieGenresResponse;
import com.carlosrd.movieapp.data.remote.model.PopularMoviesResponse;
import com.carlosrd.movieapp.data.remote.model.converters.MovieDetailsResponseMapper;
import com.carlosrd.movieapp.data.remote.model.converters.PopularMovieResponseMapper;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRemoteDataSource implements MovieRepository {

    public interface LoadConfigurationCallback{

        void onConfigurationLoaded();

        void onError(int statusCode);
    }


    public interface LoadMovieGenresCallback{

        void onMovieGenresLoaded();

        void onError(int statusCode);
    }

    private ConfigurationResponse mConfigurationCached;
    private Map<Long,String> mMovieGenresCached;

    private static MovieRemoteDataSource INSTANCE;

    public static MovieRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieRemoteDataSource();
        }
        return INSTANCE;
    }

    private MovieRemoteDataSource() {}



    @Override
    public void loadPopularMovies(final LoadPopularMoviesCallback callback) {

        // Cargamos la configuración si aun no lo hicimos
        // Una vez hecho, podemos continuar con la llamada a Popular Movies
        if (mConfigurationCached == null){

            loadAPIConfiguration(new LoadConfigurationCallback() {
                @Override
                public void onConfigurationLoaded() {

                    // Comprobamos si los generos se han cargado antes de pedir  las peliculas
                    if (mMovieGenresCached == null){

                        loadMovieGenres(new LoadMovieGenresCallback() {
                            @Override
                            public void onMovieGenresLoaded() {
                                loadPopularMoviesFromAPI(callback);
                            }

                            @Override
                            public void onError(int statusCode) {
                                callback.onError(statusCode);
                            }
                        });
                    } else {

                        // Si esta todo inicialiado cargar
                        loadPopularMoviesFromAPI(callback);
                    }

                }

                @Override
                public void onError(int statusCode) {
                    callback.onError(statusCode);
                }
            });

        } else {

            // Si la configuracion esta cargada, comprobamos si estan
            // cargados los generos de peliculas
            if (mMovieGenresCached == null){

                loadMovieGenres(new LoadMovieGenresCallback() {
                    @Override
                    public void onMovieGenresLoaded() {
                        loadPopularMoviesFromAPI(callback);
                    }

                    @Override
                    public void onError(int statusCode) {
                        callback.onError(statusCode);
                    }
                });
            } else {

                // Si esta todo inicialiado cargar
                loadPopularMoviesFromAPI(callback);

            }


        }


    }

    private void loadPopularMoviesFromAPI(final LoadPopularMoviesCallback callback){

        MovieAPI client = APIClient.createService(MovieAPI.API_BASE_URL, MovieAPI.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", MovieAPI.API_KEY);
        options.put("language", "es");

        Call<PopularMoviesResponse> call = client.getPopularMovies(options);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                // Llamada exitosa y con respuesta
                if (response.isSuccessful()) {

                    PopularMoviesResponse movieslist = response.body();

                    callback.onMoviesLoaded(
                            PopularMovieResponseMapper.getInstance().transform(movieslist,
                                    mMovieGenresCached, mConfigurationCached));

                } else {
                    callback.onError(response.code());
                }

            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {
                // Fallo en la llamada de red
                callback.onError(-1);

            }

        });
    }


    @Override
    public void loadFavoriteMovies(LoadFavouriteMoviesCallback callback) {
        // Se cargan localmente
    }

    @Override
    public void loadMovieDetails(final long movieId, final LoadMovieDetailsCallback callback) {

        // Cargamos la configuración si aun no lo hicimos
        // Una vez hecho, podemos continuar con la llamada a Popular Movies
        if (mConfigurationCached == null){

            loadAPIConfiguration(new LoadConfigurationCallback() {
                @Override
                public void onConfigurationLoaded() {
                    loadMovieDetailsFromAPI(movieId, callback);
                }

                @Override
                public void onError(int statusCode) {
                    callback.onError(statusCode);
                }
            });

        } else {

            loadMovieDetailsFromAPI(movieId, callback);

        }




    }

    private void loadMovieDetailsFromAPI(long movieId, final LoadMovieDetailsCallback callback){

        MovieAPI client = APIClient.createService(MovieAPI.API_BASE_URL, MovieAPI.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", MovieAPI.API_KEY);
        options.put("language", "es");

        Call<MovieDetailsResponse> call = client.getMovieDetails(movieId,options);
        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                // Llamada exitosa y con respuesta
                if (response.isSuccessful()) {

                    MovieDetailsResponse movie = response.body();

                    callback.onMovieDetailsLoaded(
                            MovieDetailsResponseMapper.getInstance().transform(movie, mConfigurationCached));

                } else {
                    callback.onError(response.code());
                }

            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                // Fallo en la llamada de red
                callback.onError(-1);
            }


        });

    }


    public void loadAPIConfiguration(final LoadConfigurationCallback callback) {

        MovieAPI client = APIClient.createService(MovieAPI.API_BASE_URL, MovieAPI.class);

        Call<ConfigurationResponse> call = client.getAPIConfig(MovieAPI.API_KEY);

        call.enqueue(new Callback<ConfigurationResponse>() {
            @Override
            public void onResponse(Call<ConfigurationResponse> call, Response<ConfigurationResponse> response) {
                // Llamada exitosa y con respuesta
                if (response.isSuccessful()) {

                    mConfigurationCached = response.body();
                    callback.onConfigurationLoaded();

                } else {
                    mConfigurationCached = null;
                    callback.onError(response.code());
                }

            }

            @Override
            public void onFailure(Call<ConfigurationResponse> call, Throwable t) {
                // Fallo en la llamada de red
                mConfigurationCached = null;
                callback.onError(-1);
            }


        });

    }

    public void loadMovieGenres(final LoadMovieGenresCallback callback) {

        MovieAPI client = APIClient.createService(MovieAPI.API_BASE_URL, MovieAPI.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", MovieAPI.API_KEY);
        options.put("language", "es");

        Call<MovieGenresResponse> call = client.getMovieGenresList(options);
        call.enqueue(new Callback<MovieGenresResponse>() {
            @Override
            public void onResponse(Call<MovieGenresResponse> call, Response<MovieGenresResponse> response) {
                // Llamada exitosa y con respuesta
                if (response.isSuccessful()) {

                    MovieGenresResponse movieGenresResponse = response.body();

                    mMovieGenresCached = new HashMap<>();

                    for (int i = 0; i < movieGenresResponse.getGenres().size(); i++) {
                        mMovieGenresCached.put(
                                movieGenresResponse.getGenres().get(i).getId(),
                                movieGenresResponse.getGenres().get(i).getName()
                        );
                    }

                    callback.onMovieGenresLoaded();

                } else {
                    mConfigurationCached = null;
                    callback.onError(response.code());
                }

            }

            @Override
            public void onFailure(Call<MovieGenresResponse> call, Throwable t) {
                // Fallo en la llamada de red
                mConfigurationCached = null;
                callback.onError(-1);
            }


        });

    }


    @Override
    public void saveAsFavoriteMovie(long movieId) {
        // Se hace en local
    }

    @Override
    public void saveAsFavoriteMovie(Movie movie) {
        // Se hace en local
    }

    @Override
    public void deleteAsFavoriteMovie(long movieId) {
        // Se hace en local
    }

}

