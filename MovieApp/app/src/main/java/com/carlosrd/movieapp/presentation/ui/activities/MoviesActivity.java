package com.carlosrd.movieapp.presentation.ui.activities;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.carlosrd.movieapp.R;
import com.carlosrd.movieapp.data.local.MovieDatabase;
import com.carlosrd.movieapp.data.local.MovieLocalDataSource;
import com.carlosrd.movieapp.data.remote.MovieRemoteDataSource;
import com.carlosrd.movieapp.data.repository.MovieRepositoryImpl;
import com.carlosrd.movieapp.domain.executor.impl.ThreadExecutor;
import com.carlosrd.movieapp.presentation.presenters.MoviesPresenter;
import com.carlosrd.movieapp.presentation.presenters.impl.MoviesPresenterImpl;
import com.carlosrd.movieapp.presentation.ui.fragments.MovieListFragment;
import com.carlosrd.movieapp.threading.MainThreadImpl;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends AppCompatActivity {

    @BindView(R.id.act_movies_toolbar) Toolbar mToolbar;
    @BindView(R.id.act_movies_container) ViewPager mViewPager;
    @BindView(R.id.act_movies_tabs) TabLayout mTabLayout;

    private MoviesPresenter mCurrentPresenter;

    private HashMap<Integer, MoviesPresenter> mFragmentsPresenters;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mFragmentsPresenters = new HashMap<>();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mCurrentPresenter = mFragmentsPresenters.get(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.setOffscreenPageLimit(MovieListFragment.MoviesListTypeFilter.values().length);

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            MovieListFragment.MoviesListTypeFilter filter =
                    MovieListFragment.MoviesListTypeFilter.POPULAR_MOVIES;

            switch (position){

               case 0:
                   filter = MovieListFragment.MoviesListTypeFilter.POPULAR_MOVIES;
                   break;
               case 1:
                   filter = MovieListFragment.MoviesListTypeFilter.FAVORITE_MOVIES;
                   break;

            }

            MovieListFragment mlf = MovieListFragment.newInstance(filter);

            MoviesPresenter presenter = new MoviesPresenterImpl(
                    ThreadExecutor.getInstance(),
                    MainThreadImpl.getInstance(),
                    mlf,
                    MovieRepositoryImpl.getInstance(MovieRemoteDataSource.getInstance(),
                            MovieLocalDataSource.getInstance(
                                    MovieDatabase.getInstance(MoviesActivity.this).movieDao())));

            presenter.setTypeFilter(filter);

            mFragmentsPresenters.put(position, presenter);

           if (position == 0)
               mCurrentPresenter = mFragmentsPresenters.get(0);

           return mlf;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {

                case 0:
                    return getString(R.string.act_movies_lbl_popular_movies);
                case 1:
                    return getString(R.string.act_movies_lbl_favorite_movies);

                default:
                    return "";
            }

        }

        @Override
        public int getCount() {
            return MovieListFragment.MoviesListTypeFilter.values().length;
        }
    }

}
