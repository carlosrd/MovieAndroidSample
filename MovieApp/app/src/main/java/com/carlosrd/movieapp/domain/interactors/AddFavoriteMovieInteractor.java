package com.carlosrd.movieapp.domain.interactors;

import com.carlosrd.movieapp.domain.interactors.base.Interactor;

public interface AddFavoriteMovieInteractor extends Interactor {

    interface Callback {

        void onFavoriteMovieAdded();

    }

}
