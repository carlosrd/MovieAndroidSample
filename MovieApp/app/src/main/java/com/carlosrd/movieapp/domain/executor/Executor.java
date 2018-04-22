package com.carlosrd.movieapp.domain.executor;

import com.carlosrd.movieapp.domain.interactors.base.AbstractInteractor;

/**
 * Clase Exectuor encargada de ejecturar los Interactors en segundo plano
 * <p/>
 */
public interface Executor {

    /**
     * Este metodo debería inciar el Interactor. Debería ser llamado en un hilo en segundo
     * plano ya que hará operaciones largas
     * @param interactor Interactor a ejecutar
     */
    void execute(final AbstractInteractor interactor);
}
