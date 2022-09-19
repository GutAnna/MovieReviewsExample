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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.moviereviews.network.Api
import com.example.android.moviereviews.network.Movie
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
const val apiKey = "XATOxt0y6FMjPGBu7sdO0G6XKPkBSOcp"

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _navigateToDetail = MutableLiveData<String>()
    val navigateToDetail
        get() = _navigateToDetail

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val response = Api.retrofitService.getResponse(apiKey)
                _movies.value = response.movies!!
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _movies.value = listOf()
            }
        }
    }
    fun onMovieClicked(url: String) {
        _navigateToDetail.value = url
    }

    fun onMovieDetailNavigated() {
        _navigateToDetail.value = null
    }
}
