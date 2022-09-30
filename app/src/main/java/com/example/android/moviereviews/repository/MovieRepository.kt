package com.example.android.moviereviews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.moviereviews.database.MoviesDatabase
import com.example.android.moviereviews.database.asDomainModel
import com.example.android.moviereviews.domain.Movie
import com.example.android.moviereviews.network.Api
import com.example.android.moviereviews.network.apiKey
import com.example.android.moviereviews.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MoviesDatabase) {
    val movies: LiveData<List<Movie>> = Transformations.map(database.movieDao.getMovies()) {
        it.asDomainModel()
    }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val response = Api.retrofitService.getResponse(apiKey)
            database.movieDao.insertAll(response.asDatabaseModel())
        }
    }
}
