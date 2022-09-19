package com.example.android.moviereviews.network

import com.squareup.moshi.Json

data class MoviesResponse(
    @Json(name = "results")
    val movies: List<Movie>?,
)




