package com.carlosrd.movieapp.data.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "favorites_movies_genres",
        primaryKeys = {"genre_id","movie_id"},
        foreignKeys = @ForeignKey(entity = MovieEntity.class,
                parentColumns = "id",
                childColumns = "movie_id",
                onDelete=CASCADE))
public class GenreEntity {


    @ColumnInfo(name = "genre_id")
    private long id;

    @ColumnInfo(name = "genre_name")
    private String name;

    @ColumnInfo(name = "movie_id")
    private long movieId;

    public GenreEntity(long id, String name, long movieId) {
        this.id = id;
        this.name = name;
        this.movieId = movieId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getMovieId() {
        return movieId;
    }
}
