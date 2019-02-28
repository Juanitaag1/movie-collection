//This class is a singleton so one instance will be shared with all of the app's
//Activities and fragments
//stores data reference will decide where the data is coming from
package edu.cascadia.mobile.apps.movies.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.cascadia.mobile.apps.movies.model.MovieEntity;
import edu.cascadia.mobile.apps.movies.utilities.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;
    //get an Executor because all room datab operations must be executed on a background thread
    //use only one Executor
    Executor executor = Executors.newSingleThreadExecutor();
    //instance of database through Room library
    //inorder to get this reference need a context and pass it to roomlibrary
    //so changed the original way the instance was instantiated and made the getInstancefx that takes a context
    //was private static final AppRepository ourInstance= new AppRepository();
    //public static AppRepository getInstance(){ return ourInstance;}
    //also changed the private constructor to take a context
    //have to change in Viewmodel to add a context in the AppRepository constuctor

    private movieDatabase mDb;
    //for now get the info from sampledata
    public LiveData<List<MovieEntity>> mMovies;


    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

//constructor to get the infor and initialize the database and MovieEntity that is wrapped in LiveData
    private AppRepository(Context context) {
        //create the database reference first then use it to get the data
        mDb = movieDatabase.getInstance(context);
        mMovies = getAllMovies();
        //was mMovies.SampleData.getMovies(); to get the movies from the sampleData class
    }

    //Returns LiveData object
    //returns database context table
    //this is where Repository decided where the list is coming from
    //dont need executer because when retrieve liveData object using room
    //room does background threading automatically
    private LiveData<List<MovieEntity>> getAllMovies(){
        return mDb.movieDao().getAll();
    }

    //will be called in the MainViewModel's addSampleData and the MainViewModel's addSampleData
    //will be called in the MainActivity's addSampleData's fx to add sampledata whent the addsample data
    //option is chosen in the options menu
//inserts data
    //need to run db operations in background thread so use Executor object
    public void addSampleData() {
     //get a reference to the db through room library mDb
     executor.execute(new Runnable() {
         @Override
         public void run() {
      mDb.movieDao().addAll(SampleData.getMovies());
         }
     });

    }

    //deletes all notes
    //method in Dao deleteAll that returns an integer
    //Rule_ whenever a query inside a Dao returns a LiveData Object-the room library will handle the
    //background threading for you, but if returning anything else you need to handle the background
    //threading explicitly so need Executor object
    public void deleteAllMovies() {
       executor.execute(new Runnable() {
           @Override
           public void run() {
              mDb.movieDao().deleteAll();
           }
       });
    }
}
