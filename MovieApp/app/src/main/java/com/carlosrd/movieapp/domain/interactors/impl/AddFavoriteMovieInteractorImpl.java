package com.carlosrd.movieapp.domain.interactors.impl;

import com.carlosrd.movieapp.domain.executor.Executor;
import com.carlosrd.movieapp.domain.executor.MainThread;
import com.carlosrd.movieapp.domain.interactors.AddFavoriteMovieInteractor;
import com.carlosrd.movieapp.domain.interactors.base.AbstractInteractor;
import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.domain.repository.MovieRepository;

import java.util.List;

public class AddFavoriteMovieInteractorImpl extends AbstractInteractor implements AddFavoriteMovieInteractor {

    private AddFavoriteMovieInteractorImpl.Callback mCallback;
    private MovieRepository mRepository;

    private long mMovieId;

    public AddFavoriteMovieInteractorImpl(Executor threadExecutor,
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

        mRepository.saveAsFavoriteMovie(mMovieId);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFavoriteMovieAdded();
            }
        });

    }

}
