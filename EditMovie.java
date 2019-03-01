package edu.cascadia.mobile.apps.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.cascadia.mobile.apps.movies.ViewModel.EditMovieViewModel;
import edu.cascadia.mobile.apps.movies.database.movieDatabase;;
import edu.cascadia.mobile.apps.movies.model.MovieEntity;

public class EditMovie extends AppCompatActivity {
    private movieDatabase mDatabase;
    private EditMovieViewModel mEditMovieViewModel;

    //getting a reference to the editTextView of the movie title for binding to display the data
    @BindView(R.id.edit_title)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = movieDatabase.getInstance(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO manage movie update by setting movie id if avaliable
                int movieId = 0;

                //Add Movie
                EditText eTitle = findViewById(R.id.edit_title);
                EditText eDirector = findViewById(R.id.edit_director);
                EditText eYear = findViewById(R.id.edit_year);
                EditText eRuntime = findViewById(R.id.edit_runtime);
                int runtime = Integer.parseInt(eRuntime.getText().toString());

                MovieEntity mMovie = new MovieEntity(
                        movieId,
                        eTitle.getText().toString(),
                        eDirector.getText().toString(),
                        eYear.getText().toString(),
                        runtime
                        );

                mDatabase.movieDao().addOrUpdate(mMovie);


                Snackbar.make(view, "Movie Added to Database", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //binds the editview of the title in the xml
        ButterKnife.bind(this);
        initViewModel();
    }

    //initializes the EditMovieViewModel
    //sets up the observer
    private void initViewModel() {
        mEditMovieViewModel = ViewModelProviders.of(this).get(EditMovieViewModel.class);
       mEditMovieViewModel.mLiveMovie.observe(this, new Observer<MovieEntity>() {
           @Override
           public void onChanged(@Nullable MovieEntity movieEntity) {
               if (movieEntity != null) {
                   mTextView.setText(movieEntity.getTitle());
               }
           }
       });
    }

    
}
