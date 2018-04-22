package com.carlosrd.movieapp.domain.interactors;


import com.carlosrd.movieapp.domain.interactors.base.Interactor;
import com.carlosrd.movieapp.domain.model.Movie;

import java.util.List;


public interface GetFavouriteMoviesInteractor extends Interactor {

    interface Callback {

        void onFavoriteMoviesRetrieved(List<Movie> moviesList);

        void onNoFavouriteMoviesSaved();

        void onRetrievalFailed(String message);

    }

}
