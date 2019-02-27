package edu.cascadia.mobile.apps.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.cascadia.mobile.apps.movies.ViewModel.MainViewModel;
import edu.cascadia.mobile.apps.movies.database.MovieDao;
import edu.cascadia.mobile.apps.movies.database.movieDatabase;
import edu.cascadia.mobile.apps.movies.model.MovieEntity;
import edu.cascadia.mobile.apps.movies.ui.MoviesAdapter;
import edu.cascadia.mobile.apps.movies.utilities.SampleData;

public class MainActivity extends AppCompatActivity {
 //have to initiaize the recycleview before viewmode
   // RecyclerView mRecyclerView;
    MoviesAdapter mMoviesAdapter;
    private movieDatabase mDatabase;
    private MainViewModel mMainViewModel;
    private List<MovieEntity> moviesData = new ArrayList<>();

    //butterknife annotations to define view references
    //to bind at runtime, add  line ButterKnife.bind(this); in onCreate
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //to bind the nRecycleview at runtime
        ButterKnife.bind(this);
        //don not need the next line because butterknife binding gets rid of the need
     //   mRecyclerView = findViewById(R.id.recycler_view);

//gets the data from the viewmodel
      //  moviesData.addAll(mMainViewModel.mMovies);
        //Get Database
        mDatabase = movieDatabase.getInstance(this);
        mDatabase.movieDao().addAll(SampleData.getMovies());
        //have to initiaize the recycleview before viewmode
        initRecyclerView();
        initViewModel();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editMovie = new Intent("edu.cascadia.mobile.apps.movies.EditMovie");
                startActivity(editMovie);
            }
        });

    }

    private void initViewModel() {
//need to get an observer
        //type of data the LiveData will wrap
         final Observer<List<MovieEntity>> movieObserver = new Observer<List<MovieEntity>>() {
             //method will be automatically whenever the data is updated
             //recieve the movie data
             @Override
             public void onChanged(@Nullable List<MovieEntity> movieEntities) {
                 moviesData.clear();
                 moviesData.addAll(movieEntities);
                 if(mMoviesAdapter == null) {
                     mMoviesAdapter = new MoviesAdapter(moviesData, MainActivity.this);
                     mRecyclerView.setAdapter(mMoviesAdapter);
                 }
                 else{
                     mMoviesAdapter.notifyDataSetChanged();
                 }
             }
         };
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //do this to subscribe to the data
        mMainViewModel.mMovies.observe(this, movieObserver);
    }

    private void initRecyclerView(){
        //to make sure every item in recyclervies has the same size
        mRecyclerView.setHasFixedSize(true);
        //recyclerview can appear as a list- linearLayout or tile
        //create a layout manager and set the recycler view to the linearlayout
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  mRecyclerView.setAdapter(new MoviesAdapter(mDatabase.movieDao().getMovies(),this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
