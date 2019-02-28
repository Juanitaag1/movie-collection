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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    //click handler for fab using butterknife
    //when click the fab taken to the editmovie activity
    @OnClick(R.id.fab)
   void fabClickHandler(){
        Intent intent = new Intent(this, EditMovie.class);//the context and class to oper
        startActivity(intent);
    }

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

        //gets the movie from the sampleData then loops through
       // moviesData.addAll(SampleData.getMovies());
       //gets the data from the viewmodel
        //for each movie logcat output
    /*  moviesData.addAll(mMainViewModel.mMovies);
        for(MovieEntity movie : moviesData){
            Log.i("movie-collection", movie.toString());
        }*/

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
         //ViewModelProviders class handels the instantiation and thatis how the
        //application component is passed in in the constructor in the viewmodel class
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //do this to subscribe to the data
        mMainViewModel.mMovies.observe(this, movieObserver);
    }

    private void initRecyclerView(){
        //to make sure every item in recyclervies has the same height
        mRecyclerView.setHasFixedSize(true);
        //recyclerview can appear as a list- linearLayout or tile layout
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
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //create a chain of calls to add the data
    //when the add sample data item option is called in the menu
    //calls addSampleData fx from viewmodel - need to implement & it will make same call to the repository
    private void addSampleData() {
        mMainViewModel.addSampleData();

    }
}
