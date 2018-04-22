package com.carlosrd.movieapp.domain.interactors.impl;

import com.carlosrd.movieapp.domain.executor.Executor;
import com.carlosrd.movieapp.domain.executor.MainThread;
import com.carlosrd.movieapp.domain.interactors.GetFavouriteMoviesInteractor;
import com.carlosrd.movieapp.domain.interactors.base.AbstractInteractor;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;

import java.util.List;

public class GetFavouriteMoviesInteractorImpl extends AbstractInteractor implements GetFavouriteMoviesInteractor {

    private GetFavouriteMoviesInteractorImpl.Callback mCallback;
    private MovieRepository mRepository;

    public GetFavouriteMoviesInteractorImpl(Executor threadExecutor,
                                            MainThread mainThread,
                                            Callback callback,
                                            MovieRepository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }


    @Override
    public void run() {

        mRepository.loadFavoriteMovies(new MovieRepository.LoadFavouriteMoviesCallback() {

            @Override
            public void onMoviesLoaded(final List<Movie> favouriteMoviesList) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onFavoriteMoviesRetrieved(favouriteMoviesList);
                            }
                });
            }

            @Override
            public void onNoDataAvailable() {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onNoFavouriteMoviesSaved();
                    }
                });
            }

            @Override
            public void onError(final String message) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onRetrievalFailed(message);
                    }
                });
            }
        });

    }


}
