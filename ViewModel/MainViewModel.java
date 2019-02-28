//this class will manage the persistant data the MovieEntity
//gets data from repository
package edu.cascadia.mobile.apps.movies.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import edu.cascadia.mobile.apps.movies.database.AppRepository;
import edu.cascadia.mobile.apps.movies.model.MovieEntity;
import edu.cascadia.mobile.apps.movies.utilities.SampleData;

public class MainViewModel extends AndroidViewModel {

    //for now get the ifo from sampledata
//public List<MovieEntity> mMovies = SampleData.getMovies();
    //changed when made repository to
    //wrapping in LiveData
    public LiveData<List<MovieEntity>> mMovies;
    public AppRepository mAppRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(application.getApplicationContext());//the constuctor now takes a context
        //now get the mMovies from the repository
        mMovies = mAppRepository.mMovies;
    }

    //makes a call to the reopsitory to addSampleData
    //will be called in the MainActivity for the optionsmenu for add_sample_data
    //need to implement method in AppRepository
    public void addSampleData() {
        mAppRepository.addSampleData();
    }
}
