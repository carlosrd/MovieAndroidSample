package com.carlosrd.movieapp.presentation.presenters;

import com.carlosrd.movieapp.presentation.model.MovieDetails;
import com.carlosrd.movieapp.presentation.presenters.base.BasePresenter;
import com.carlosrd.movieapp.presentation.ui.BaseView;

public interface MovieDetailsPresenter extends BasePresenter {

    interface View extends BaseView {

        void showMovieDetails(MovieDetails movieDetails);

        void showMovieCheckedAsFavorite();

        void showMovieUncheckedAsFavorite();

    }

    void loadMovieDetails();

    void onFavoriteButtonClicked();
}
