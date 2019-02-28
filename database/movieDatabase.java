package edu.cascadia.mobile.apps.movies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.graphics.Movie;

import edu.cascadia.mobile.apps.movies.model.MovieEntity;

//there will only be one instance of the db that can be referenced anywhere in the app
//will always be synchronized
@Database(entities = {MovieEntity.class}, version = 2, exportSchema = false)
public abstract class movieDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "MovieDatabase.db";
    //always in main memory because marked as volitle
    public static volatile  movieDatabase instance;
    //need an object to synchonize on it can be almost anything
    //synch to make sure 2 parts of program dont try to make the database at same time

    private static final Object LOCK = new Object();

//for each dao have to have one of these methods
    public abstract MovieDao movieDao();

//returns an instance of movieDatabase
    public static movieDatabase getInstance(Context context){
        //check if null and if null synch
        if (instance == null){
            synchronized (LOCK){
                //check if null
                if (instance == null)
                    //class want to create the db from and db name
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            movieDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
            }
        }
        return instance;
    }
}
