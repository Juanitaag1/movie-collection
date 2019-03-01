package edu.cascadia.mobile.apps.movies.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import edu.cascadia.mobile.apps.movies.database.AppRepository;
import edu.cascadia.mobile.apps.movies.model.MovieEntity;

public class EditMovieViewModel extends AndroidViewModel {
    //mutableLiveData object that can change its value will publish the change to any observer in frag or Activity
    public MutableLiveData<MovieEntity> mLiveMovie = new MutableLiveData<>();
    private AppRepository mAppRepository;

    public EditMovieViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());
    }
}