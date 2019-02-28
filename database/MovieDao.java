package edu.cascadia.mobile.apps.movies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.cascadia.mobile.apps.movies.model.MovieEntity;

@Dao
public interface MovieDao {

    //Create (and Update)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addOrUpdate(MovieEntity movie);

    //can also use an array to add a list of movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAll(List<MovieEntity> movies);

    //Delete
    @Delete
    void remove(MovieEntity movie);

    @Delete
   void removeAll(List<MovieEntity> movies);

    
    @Query("DELETE FROM movie")
    int deleteAll();


    //Read
    @Query("select * from movie where id = :id")//id here and in param must match Room does matching
    MovieEntity getMovie(int id);
    @Query("select * from movie order by title asc")
    //List<MovieEntity> getMovies();
    //change to to return a livedata object
    LiveData<List<MovieEntity>> getAll();

    //Count
    @Query("select COUNT(*) from movie")
    int getCount();

}
