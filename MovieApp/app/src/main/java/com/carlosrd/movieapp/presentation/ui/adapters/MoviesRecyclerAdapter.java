package com.carlosrd.movieapp.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlosrd.movieapp.R;
import com.carlosrd.movieapp.presentation.model.MovieListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesRecyclerAdapter
        extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    // INTERFACE (CALLBACK)
    // ********************************************************************************

    // Interface para capturar eventos onClick
    public interface MovieListItemClickListener {

        void onMovieClicked(long movieId);

    }

    // ATRIBUTOS
    // ********************************************************************************

    private ArrayList<MovieListItem> moviesList;
    private MoviesRecyclerAdapter.MovieListItemClickListener listener;


    // VIEWHOLDER
    // ********************************************************************************

    // Clase que representa los elementos de lista
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.list_item_movie_img_movie) ImageView movieImage;
        @BindView(R.id.list_item_movie_lbl_title) TextView movieTitle;
        @BindView(R.id.list_item_movie_lbl_subtitle) TextView movieSubtitle;
        @BindView(R.id.list_item_movie_lbl_small) TextView movieSmall;

        public ViewHolder(View item) {
            super(item);

            ButterKnife.bind(this,item);
            item.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            listener.onMovieClicked(moviesList.get(getAdapterPosition()).getId());

        }

    }

    // CONSTRUCTORAS (ADAPTADOR)
    // ********************************************************************************


    public MoviesRecyclerAdapter(ArrayList<MovieListItem> moviesList,
                                 MoviesRecyclerAdapter.MovieListItemClickListener listener) {

        this.moviesList = moviesList;
        this.listener = listener;

    }

    // VIEWHOLDER LIFECYCLE
    // ********************************************************************************

    @Override
    public MoviesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);

        MoviesRecyclerAdapter.ViewHolder vh = new MoviesRecyclerAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(MoviesRecyclerAdapter.ViewHolder holder, int position) {

        // Si la URL no está vacía descargamos imagen
        if (!moviesList.get(position).getImageURL().equals("")) {
            Picasso.get()
                    .load(moviesList.get(position).getImageURL())
                    .resize(100, 150)
                    .centerCrop()
                    .placeholder(R.drawable.movie_placeholder)
                    .into(holder.movieImage);
        } else {
            // Si no, cargamos imagen dummy
            Picasso.get()
                    .load(R.drawable.movie_placeholder)
                    .resize(74, 74)
                    .centerCrop()
                    .placeholder(R.drawable.movie_placeholder)
                    .into(holder.movieImage);
        }

        holder.movieTitle.setText(Html.fromHtml(moviesList.get(position).getTitle()));
        holder.movieSubtitle.setText(moviesList.get(position).getGenres());
        holder.movieSmall.setText(moviesList.get(position).getYear());

    }

    // MÉTODOS
    // ********************************************************************************

    @Override
    public int getItemCount() {

        return moviesList.size();

    }

    /**
     * Actualiza el Dataset de este adaptador
     * @param recipeList    Lista con los datos actualizados
     */
    public void updateDataSet(ArrayList<MovieListItem> recipeList){
        this.moviesList = recipeList;
        notifyDataSetChanged();
    }

}



