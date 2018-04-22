package com.carlosrd.movieapp.presentation.presenters.base;


import com.carlosrd.movieapp.domain.executor.Executor;
import com.carlosrd.movieapp.domain.executor.MainThread;

/**
 * Clase base para los Presenters que se comunican con las Interactors. Mantiene referencias al Executor y al MainThread
 * necesarios para que los Interactors se ejecuten en segundo plano
 */

/**
 * This is a base class for all presenters which are communicating with interactors. This base class will hold a
 * reference to the Executor and MainThread objects that are needed for running interactors in a background thread.
 */
public abstract class AbstractPresenter {
    protected Executor   mExecutor;
    protected MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}
