package com.carlosrd.movieapp.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carlosrd.movieapp.R;
import com.carlosrd.movieapp.presentation.model.MovieListItem;
import com.carlosrd.movieapp.presentation.presenters.MoviesPresenter;
import com.carlosrd.movieapp.presentation.ui.activities.MovieDetailsActivity;
import com.carlosrd.movieapp.presentation.ui.adapters.MoviesRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListFragment extends Fragment implements MoviesPresenter.View {

    public static final String MOVIE_ID = "MOVIE_ID";

    public enum MoviesListTypeFilter {POPULAR_MOVIES, FAVORITE_MOVIES};

    private static final String ARG_LIST_TYPE = "LIST_TYPE";

    @BindView(R.id.frag_movieslist_recycler_view) RecyclerView mMoviesRecyclerView;
    private MoviesRecyclerAdapter mRecyclerAdapter;

    @BindView(R.id.frag_movieslist_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.frag_movieslist_lbl_message) TextView mLblMessage;

    private MoviesPresenter mFragmentPresenter;

    public MovieListFragment() { }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MovieListFragment newInstance(MoviesListTypeFilter typeList) {

        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST_TYPE, typeList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);

        ButterKnife.bind(this,rootView);

        // Setear layout de la lista
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mMoviesRecyclerView.setLayoutManager(mLayoutManager);

        // Cuando se han cargado las vistas, cargamos las peliculas
        mFragmentPresenter.loadMovies();

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentPresenter.resume();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

        mMoviesRecyclerView.setVisibility(View.GONE);
        mLblMessage.setVisibility(View.VISIBLE);
        mLblMessage.setText(message);

    }

    @Override
    public void setPresenter(MoviesPresenter presenter) {
        mFragmentPresenter = presenter;
    }

    @Override
    public void showMoviesList(ArrayList<MovieListItem> popularMoviesList) {

        mLblMessage.setVisibility(View.GONE);

        mMoviesRecyclerView.setVisibility(View.VISIBLE);

        // Especificamos un adaptador (y tambien oyente para evento OnClick de cada elemento)
        mRecyclerAdapter = new MoviesRecyclerAdapter(popularMoviesList,
                new MoviesRecyclerAdapter.MovieListItemClickListener() {

                    @Override
                    public void onMovieClicked(long movieId) {
                        mFragmentPresenter.openMovieDetails(movieId);
                    }

                });


        // Setear el adaptador al RecyclerView
        mMoviesRecyclerView.setAdapter(mRecyclerAdapter);

    }

    @Override
    public void showMovieDetails(long movieId) {

        Intent i = new Intent(getActivity(), MovieDetailsActivity.class);
        i.putExtra(MOVIE_ID, movieId);
        startActivity(i);

    }

    @Override
    public void showNoResults() {
        mMoviesRecyclerView.setVisibility(View.GONE);
        mLblMessage.setVisibility(View.VISIBLE);
        mLblMessage.setText(getString(R.string.frag_movilist_lbl_no_favorites));
    }

}
