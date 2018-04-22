package com.carlosrd.movieapp.presentation.model.converters;

import com.carlosrd.movieapp.domain.model.Movie;
import com.carlosrd.movieapp.presentation.model.MovieListItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieListItemMapper {

    private static MovieListItemMapper INSTANCE;

    public static MovieListItemMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieListItemMapper();
        }
        return INSTANCE;
    }

    private MovieListItemMapper() {}


    public ArrayList<MovieListItem> transform(List<Movie> movieList){

        ArrayList<MovieListItem> list = new ArrayList<>();

        for (int i = 0; i < movieList.size(); i++ ){

            String genres = "";

            for (int j = 0; j < movieList.get(i).getMovieGenres().size(); j++){
                genres += movieList.get(i).getMovieGenres().get(j).getName() + ", ";
            }

            genres = genres.substring(0, genres.length() - 2);

            Calendar cal = Calendar.getInstance();


            String year = movieList.get(i).getReleaseDate().substring(0,4);

            list.add(
                    new MovieListItem(
                            movieList.get(i).getId(),
                            movieList.get(i).getTitle(),
                            genres,
                            year,
                            movieList.get(i).getPosterPath()
                    ));

        }


        return list;

    }
}
