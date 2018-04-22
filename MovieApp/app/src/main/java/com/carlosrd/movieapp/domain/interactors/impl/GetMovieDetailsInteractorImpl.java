package com.carlosrd.movieapp.domain.interactors.impl;

import com.carlosrd.movieapp.domain.executor.Executor;
import com.carlosrd.movieapp.domain.executor.MainThread;
import com.carlosrd.movieapp.domain.interactors.GetMovieDetailsInteractor;
import com.carlosrd.movieapp.domain.interactors.base.AbstractInteractor;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;

public class GetMovieDetailsInteractorImpl extends AbstractInteractor implements GetMovieDetailsInteractor {

    private GetMovieDetailsInteractor.Callback mCallback;
    private MovieRepository mRepository;

    private long mMovieId;

    public GetMovieDetailsInteractorImpl(Executor threadExecutor,
                                         MainThread mainThread,
                                         Callback callback,
                                         MovieRepository repository,
                                         long movieId) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
        mMovieId = movieId;
    }

    @Override
    public void run() {

        mRepository.loadMovieDetails(mMovieId, new MovieRepository.LoadMovieDetailsCallback() {

            @Override
            public void onMovieDetailsLoaded(final Movie movie) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onMoviesDetailsRetrieved(movie);
                    }
                });
            }

            @Override
            public void onNoDataAvailable() {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onNoLocalDataAvailable();
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
