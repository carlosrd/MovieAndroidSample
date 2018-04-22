package com.carlosrd.movieapp.presentation.presenters.impl;

import com.carlosrd.movieapp.R;
import com.carlosrd.movieapp.domain.executor.Executor;
import com.carlosrd.movieapp.domain.executor.MainThread;
import com.carlosrd.movieapp.domain.interactors.GetFavouriteMoviesInteractor;
import com.carlosrd.movieapp.domain.interactors.GetPopularMoviesInteractor;
import com.carlosrd.movieapp.domain.interactors.impl.GetFavouriteMoviesInteractorImpl;
import com.carlosrd.movieapp.domain.interactors.impl.GetPopularMoviesInteractorImpl;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;
import com.carlosrd.movieapp.presentation.model.converters.MovieListItemMapper;
import com.carlosrd.movieapp.presentation.presenters.MoviesPresenter;
import com.carlosrd.movieapp.presentation.presenters.base.AbstractPresenter;
import com.carlosrd.movieapp.presentation.ui.fragments.MovieListFragment;

import java.util.ArrayList;
import java.util.List;

public class MoviesPresenterImpl extends AbstractPresenter implements MoviesPresenter,
        GetPopularMoviesInteractor.Callback,
        GetFavouriteMoviesInteractor.Callback{

    private MoviesPresenter.View mView;

    private MovieRepository mMovieRepository;

    private MovieListFragment.MoviesListTypeFilter mTypeFilter =
            MovieListFragment.MoviesListTypeFilter.POPULAR_MOVIES;

    public MoviesPresenterImpl(Executor executor,
                               MainThread mainThread,
                               View view,
                               MovieRepository repository) {
        super(executor, mainThread);
        mView = view;
        mMovieRepository = repository;

        mView.setPresenter(this);

    }

    @Override
    public void resume() {

        // Recargamos las favoritas por si hubo cambios
        if (mTypeFilter == MovieListFragment.MoviesListTypeFilter.FAVORITE_MOVIES) {

            GetFavouriteMoviesInteractor interactorFav = new GetFavouriteMoviesInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mMovieRepository
            );

            // run the interactor
            interactorFav.execute();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

        mView = null;
        mMovieRepository = null;

    }

    @Override
    public void onError(String message) {
        mView.hideProgress();
        mView.showError(message);
    }

    @Override
    public void loadMovies() {

        mView.showProgress();

        switch(mTypeFilter){
            case POPULAR_MOVIES:        // initialize the interactor
                GetPopularMoviesInteractor interactor = new GetPopularMoviesInteractorImpl(
                        mExecutor,
                        mMainThread,
                        this,
                        mMovieRepository
                );
                // run the interactor
                interactor.execute();
                break;

            case FAVORITE_MOVIES:
                GetFavouriteMoviesInteractor interactorFav = new GetFavouriteMoviesInteractorImpl(
                        mExecutor,
                        mMainThread,
                        this,
                        mMovieRepository
                );

                // run the interactor
                interactorFav.execute();
                break;
        }

    }

    @Override
    public void openMovieDetails(long movieId) {
        // NEW USE CASE OR CALL VIEW Y YA? PORQUE PARA QUE PEDIR LOS DATOS, SE PIDEN LUEGO
        mView.showMovieDetails(movieId);
    }

    @Override
    public void setTypeFilter(MovieListFragment.MoviesListTypeFilter typeFilter) {
        mTypeFilter = typeFilter;
    }

    @Override
    public MovieListFragment.MoviesListTypeFilter getTypeFilter() {
        return mTypeFilter;
    }

    // Popular Movies Callbacks

    @Override
    public void onMoviesRetrieved(ArrayList<Movie> moviesList) {

        mView.hideProgress();
        mView.showMoviesList(MovieListItemMapper.getInstance().transform(moviesList));

    }

    @Override
    public void onRetrievalFailed(int statusCode) {
        mView.hideProgress();
        if (statusCode == -1){
            mView.showError("Cannot connect to the server. \nPlease try again later...");
        } else {
            mView.showError("Server returned " + statusCode + " error");
        }
    }

    // Favorite Callabacks

    @Override
    public void onFavoriteMoviesRetrieved(List<Movie> moviesList) {

        mView.hideProgress();
        mView.showMoviesList(MovieListItemMapper.getInstance().transform(moviesList));

    }

    @Override
    public void onNoFavouriteMoviesSaved() {
        mView.hideProgress();
        mView.showNoResults();
    }

    @Override
    public void onRetrievalFailed(String message) {
        mView.hideProgress();
        mView.showError(message);
    }




}
