package edu.cascadia.mobile.apps.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.cascadia.mobile.apps.movies.R;
import edu.cascadia.mobile.apps.movies.model.MovieEntity;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final List<MovieEntity> mMovies;
    private final Context mContext;

    public MoviesAdapter(List<MovieEntity> mMovies, Context mContext) {
        this.mMovies = mMovies;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: pass movie data to the intent

                Intent editMovie = new Intent("edu.cascadia.mobile.apps.movies.EditMovie");
                parent.getContext().startActivity(editMovie);
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieEntity movie = mMovies.get(position);

        holder.mTextView.setText(movie.getTitle() + ": " + movie.getDirector());

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public ViewHolder(View itemView){
            super(itemView);
            mTextView = itemView.findViewById(R.id.movie_text);
        }
    }
}
