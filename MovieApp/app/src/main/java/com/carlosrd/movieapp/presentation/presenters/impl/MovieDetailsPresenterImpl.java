package com.carlosrd.movieapp.presentation.presenters.impl;

import com.carlosrd.movieapp.domain.executor.Executor;
import com.carlosrd.movieapp.domain.executor.MainThread;
import com.carlosrd.movieapp.domain.interactors.AddFavoriteMovieInteractor;
import com.carlosrd.movieapp.domain.interactors.DeleteFavoriteMovieInteractor;
import com.carlosrd.movieapp.domain.interactors.GetMovieDetailsInteractor;
import com.carlosrd.movieapp.domain.interactors.impl.AddFavoriteMovieInteractorImpl;
import com.carlosrd.movieapp.domain.interactors.impl.DeleteFavoriteMovieInteractorImpl;
import com.carlosrd.movieapp.domain.interactors.impl.GetMovieDetailsInteractorImpl;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;
import com.carlosrd.movieapp.presentation.model.converters.MovieDetailsMapper;
import com.carlosrd.movieapp.presentation.presenters.MovieDetailsPresenter;
import com.carlosrd.movieapp.presentation.presenters.base.AbstractPresenter;

public class MovieDetailsPresenterImpl extends AbstractPresenter implements MovieDetailsPresenter,
        GetMovieDetailsInteractor.Callback,
        AddFavoriteMovieInteractor.Callback,
        DeleteFavoriteMovieInteractor.Callback {

    private MovieDetailsPresenter.View mView;

    private MovieRepository mMovieRepository;

    private long mMovieId;

    private boolean isFavorite;

    public MovieDetailsPresenterImpl(Executor executor,
                                      MainThread mainThread,
                                      View view,
                                      MovieRepository repository,
                                     long movieId) {
        super(executor, mainThread);
        mView = view;
        mMovieRepository = repository;
        mMovieId = movieId;
    }

    @Override
    public void resume() {

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

    }



    @Override
    public void loadMovieDetails() {

        mView.showProgress();

        GetMovieDetailsInteractor interactor = new GetMovieDetailsInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mMovieRepository,
                mMovieId
        );
        // run the interactor
        interactor.execute();

    }

    // Callbacks load
    @Override
    public void onMoviesDetailsRetrieved(Movie movie) {
        mView.hideProgress();

        isFavorite = movie.isFavorite();

        mView.showMovieDetails(MovieDetailsMapper.getInstance().transform(movie));

    }

    @Override
    public void onRetrievalFailed(int statusCode) {

    }

    @Override
    public void onNoLocalDataAvailable() {

    }


    @Override
    public void onFavoriteButtonClicked() {

        if (isFavorite){
            DeleteFavoriteMovieInteractor interactor = new DeleteFavoriteMovieInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mMovieRepository,
                    mMovieId
            );
            // run the interactor
            interactor.execute();
        } else {
            AddFavoriteMovieInteractor interactor = new AddFavoriteMovieInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mMovieRepository,
                    mMovieId
            );
            // run the interactor
            interactor.execute();
        }
    }

    @Override
    public void onFavoriteMovieAdded() {
        isFavorite = true;
        mView.showMovieCheckedAsFavorite();
    }

    @Override
    public void onFavoriteMovieDeleted() {
        isFavorite = false;
        mView.showMovieUncheckedAsFavorite();
    }

}
