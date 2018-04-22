package com.carlosrd.movieapp.domain.interactors.impl;

import com.carlosrd.movieapp.domain.executor.Executor;
import com.carlosrd.movieapp.domain.executor.MainThread;
import com.carlosrd.movieapp.domain.interactors.GetPopularMoviesInteractor;
import com.carlosrd.movieapp.domain.interactors.base.AbstractInteractor;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;

import java.util.ArrayList;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class GetPopularMoviesInteractorImpl extends AbstractInteractor implements GetPopularMoviesInteractor {

    private GetPopularMoviesInteractor.Callback mCallback;
    private MovieRepository mRepository;

    public GetPopularMoviesInteractorImpl(Executor threadExecutor,
                                          MainThread mainThread,
                                          Callback callback, MovieRepository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {

        mRepository.loadPopularMovies(new MovieRepository.LoadPopularMoviesCallback() {

            @Override
            public void onMoviesLoaded(final ArrayList<Movie> popularMoviesList) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onMoviesRetrieved(popularMoviesList);
                    }
                });

            }

            @Override
            public void onError(final int statusCode) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onRetrievalFailed(statusCode);
                    }
                });

            }
        });

    }


}
