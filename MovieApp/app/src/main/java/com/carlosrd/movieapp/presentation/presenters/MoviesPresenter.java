package com.carlosrd.movieapp.presentation.presenters;

import com.carlosrd.movieapp.presentation.model.MovieListItem;
import com.carlosrd.movieapp.presentation.presenters.base.BasePresenter;
import com.carlosrd.movieapp.presentation.ui.BaseView;
import com.carlosrd.movieapp.presentation.ui.fragments.MovieListFragment;

import java.util.ArrayList;


public interface MoviesPresenter extends BasePresenter {

    interface View extends BaseView {

        void setPresenter(MoviesPresenter presenter);

        void showMoviesList(ArrayList<MovieListItem> popularMoviesList);

        void showMovieDetails(long movieId);

        void showNoResults();

        void showError(String message);

    }

    void loadMovies();

    void openMovieDetails(long movieId);

    void setTypeFilter(MovieListFragment.MoviesListTypeFilter typeFilter);

    MovieListFragment.MoviesListTypeFilter getTypeFilter();

}
