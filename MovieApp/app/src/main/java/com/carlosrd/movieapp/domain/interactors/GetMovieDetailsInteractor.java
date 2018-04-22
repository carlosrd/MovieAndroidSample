package com.carlosrd.movieapp.domain.interactors;

import com.carlosrd.movieapp.domain.interactors.base.Interactor;
import com.carlosrd.movieapp.domain.model.Movie;

public interface GetMovieDetailsInteractor extends Interactor {

    interface Callback {

        void onMoviesDetailsRetrieved(Movie movie);

        void onRetrievalFailed(int statusCode);

        void onNoLocalDataAvailable();

    }

}
