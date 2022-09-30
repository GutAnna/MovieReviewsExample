/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.moviereviews.screens.overview

import android.app.Application
import androidx.lifecycle.*
import com.example.android.moviereviews.database.getDatabase
import com.example.android.moviereviews.repository.MovieRepository
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }


class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository(getDatabase(application))
    val movielist = movieRepository.movies

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _navigateToDetail = MutableLiveData<String>()
    val navigateToDetail
        get() = _navigateToDetail

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                movieRepository.refreshMovies()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                if(movielist.value.isNullOrEmpty())
                _status.value = ApiStatus.ERROR
            }
        }
    }
    fun onMovieClicked(url: String) {
        _navigateToDetail.value = url
    }

    fun onMovieDetailNavigated() {
        _navigateToDetail.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OverviewViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
