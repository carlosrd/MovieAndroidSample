package com.carlosrd.movieapp.domain.interactors;


import com.carlosrd.movieapp.domain.interactors.base.Interactor;
import com.carlosrd.movieapp.domain.model.Movie;

import java.util.ArrayList;


public interface GetPopularMoviesInteractor extends Interactor {

    interface Callback {

        void onMoviesRetrieved(ArrayList<Movie> moviesList);

        void onRetrievalFailed(int statusCode);

    }

}
