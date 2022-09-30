package com.example.android.moviereviews.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {
    @Query("select * from databasemovies")
    fun getMovies(): LiveData<List<DatabaseMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll (movie: List<DatabaseMovies>)
}

@Database(entities = [DatabaseMovies::class], version = 1, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
}

private lateinit var INSTANCE: MoviesDatabase

fun getDatabase(context: Context): MoviesDatabase {
    synchronized(MoviesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MoviesDatabase::class.java,
                "movies").build()
        }
    }
    return INSTANCE
}