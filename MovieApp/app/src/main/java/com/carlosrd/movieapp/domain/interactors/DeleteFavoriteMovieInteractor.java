package com.carlosrd.movieapp.domain.interactors;

import com.carlosrd.movieapp.domain.interactors.base.Interactor;

public interface DeleteFavoriteMovieInteractor extends Interactor {

    interface Callback {

        void onFavoriteMovieDeleted();

    }

}
