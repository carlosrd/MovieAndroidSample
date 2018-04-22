package com.carlosrd.movieapp.presentation.ui.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlosrd.movieapp.R;
import com.carlosrd.movieapp.data.local.MovieDatabase;
import com.carlosrd.movieapp.data.local.MovieLocalDataSource;
import com.carlosrd.movieapp.data.remote.MovieRemoteDataSource;
import com.carlosrd.movieapp.data.repository.MovieRepositoryImpl;
import com.carlosrd.movieapp.domain.executor.impl.ThreadExecutor;
import com.carlosrd.movieapp.presentation.model.MovieDetails;
import com.carlosrd.movieapp.presentation.presenters.MovieDetailsPresenter;
import com.carlosrd.movieapp.presentation.presenters.impl.MovieDetailsPresenterImpl;
import com.carlosrd.movieapp.threading.MainThreadImpl;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.carlosrd.movieapp.presentation.ui.fragments.MovieListFragment.MOVIE_ID;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsPresenter.View {

    private MovieDetailsPresenter mPresenter;

    @BindView(R.id.act_movie_details_toolbar) Toolbar mToolbar;
    @BindView(R.id.act_movie_details_toolbar_image) ImageView mImageToolbar;
    @BindView(R.id.act_movie_details_fab) FloatingActionButton mFavoriteFab;
    @BindView(R.id.act_movie_details_lbl_title) TextView mLblTitle;
    @BindView(R.id.act_movie_details_lbl_original_title) TextView mLblOriginalTitle;
    @BindView(R.id.act_movie_details_lbl_genres) TextView mLblGenres;
    @BindView(R.id.act_movie_details_lbl_tagline) TextView mLblTagline;
    @BindView(R.id.act_movie_details_lbl_overview) TextView mLblOverview;
    @BindView(R.id.act_movie_details_lbl_runtime) TextView mLblRuntime;
    @BindView(R.id.act_movie_details_lbl_release_date) TextView mLblReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        ButterKnife.bind(this);

        // Recibir pelicula a mostrar
        long movieId = getIntent().getLongExtra(MOVIE_ID, -1);

        setUpToolbar();

        mFavoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.onFavoriteButtonClicked();

            }
        });


        mPresenter = new MovieDetailsPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                MovieDetailsActivity.this,
                MovieRepositoryImpl.getInstance(MovieRemoteDataSource.getInstance(),
                        MovieLocalDataSource.getInstance(
                                MovieDatabase.getInstance(this).movieDao())),
                movieId
        );

        mPresenter.loadMovieDetails();

    }



    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showMovieDetails(MovieDetails movieDetails) {

        // Si la URL no está vacía descargamos imagen
        if (!movieDetails.getImageURL().equals("")) {
            Picasso.get()
                    .load(movieDetails.getImageURL())
                    .fit()
                    .placeholder(R.drawable.movie_placeholder)
                    .into(mImageToolbar);
        } else {
            // Si no, cargamos imagen dummy
            Picasso.get()
                    .load(R.drawable.movie_placeholder)
                    .resize(300, 300)
                    .centerCrop()
                    .placeholder(R.drawable.movie_placeholder)
                    .into(mImageToolbar);
        }

        mLblTitle.setText(movieDetails.getTitle());
        mLblOriginalTitle.setText(movieDetails.getOriginalTitle());

        mLblOverview.setText(movieDetails.getOverview());

        mLblGenres.setText(movieDetails.getGenres());

        mLblRuntime.setText(movieDetails.getRuntime());
        mLblReleaseDate.setText(movieDetails.getReleaseDate());

        if (movieDetails.getTagline().trim().equals(""))
            mLblTagline.setText("Resumen");
        else
            mLblTagline.setText(movieDetails.getTagline());

        if (movieDetails.isFavorite()){
            mFavoriteFab.setImageResource(R.drawable.icon_favorite_marked);
            mFavoriteFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.favorite_marked)));
        } else {
            mFavoriteFab.setImageResource(R.drawable.icon_favorite_unmarked);
            mFavoriteFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.favorite_unmarked)));
        }

        mFavoriteFab.setVisibility(View.VISIBLE);

    }


    @Override
    public void showMovieCheckedAsFavorite() {
        mFavoriteFab.setImageResource(R.drawable.icon_favorite_marked);
        mFavoriteFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.favorite_marked)));
    }

    @Override
    public void showMovieUncheckedAsFavorite() {
        mFavoriteFab.setImageResource(R.drawable.icon_favorite_unmarked);
        mFavoriteFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.favorite_unmarked)));

    }

    private void setUpToolbar(){


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Para que se ejecute la animación de forma inversa al cerrar la Activity
                supportFinishAfterTransition();
            }
        });

        // Eliminamos el Titulo de la Toolbar
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.act_movie_details_toolbar_layout);
        toolbarLayout.setTitle(" ");

    }


}
