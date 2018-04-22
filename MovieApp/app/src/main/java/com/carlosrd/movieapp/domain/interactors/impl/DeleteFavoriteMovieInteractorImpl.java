package com.carlosrd.movieapp.domain.interactors.impl;

import com.carlosrd.movieapp.domain.executor.Executor;
import com.carlosrd.movieapp.domain.executor.MainThread;
import com.carlosrd.movieapp.domain.interactors.AddFavoriteMovieInteractor;
import com.carlosrd.movieapp.domain.interactors.DeleteFavoriteMovieInteractor;
import com.carlosrd.movieapp.domain.interactors.base.AbstractInteractor;
import com.carlosrd.movieapp.domain.repository.MovieRepository;

public class DeleteFavoriteMovieInteractorImpl extends AbstractInteractor implements DeleteFavoriteMovieInteractor {

    private DeleteFavoriteMovieInteractor.Callback mCallback;
    private MovieRepository mRepository;

    private long mMovieId;

    public DeleteFavoriteMovieInteractorImpl(Executor threadExecutor,
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

        mRepository.deleteAsFavoriteMovie(mMovieId);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFavoriteMovieDeleted();
            }
        });

    }

}

