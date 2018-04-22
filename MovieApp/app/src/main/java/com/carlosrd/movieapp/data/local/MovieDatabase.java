package com.carlosrd.movieapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.carlosrd.movieapp.data.local.model.GenreEntity;
import com.carlosrd.movieapp.data.local.model.MovieEntity;

/**
 * The Room Database that contains the Favourite Movies table.
 */
@Database(entities = {MovieEntity.class, GenreEntity.class}, version = 2)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase INSTANCE;

    public abstract MovieDao movieDao();

    private static final Object sLock = new Object();

    public static MovieDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, "Movies.db")
                        .build();
            }
            return INSTANCE;
        }
    }

}
