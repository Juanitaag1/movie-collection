//This class is a singleton so one instance will be shared with all of the app's
//Activities and fragments
//stores data reference will decide where the data is coming from
package edu.cascadia.mobile.apps.movies.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import edu.cascadia.mobile.apps.movies.model.MovieEntity;
import edu.cascadia.mobile.apps.movies.utilities.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;

    //instance of database
    private movieDatabase mDb;
    //for now get the ifo from sampledata
    public LiveData<List<MovieEntity>> mMovies;


    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }


    private AppRepository(Context context) {
        //create the reference first
        mDb = movieDatabase.getInstance(context);
        mMovies = getAllMovies();
    }

    //Returns LiveData object
    //returns database context table
    //this is where Repository decided where the list is coming from
    //dont need executer because when retrieve liveData object using room
    //room does background threading automatically
    private LiveData<List<MovieEntity>> getAllMovies(){
        return mDb.movieDao().getAll();
    }
}
