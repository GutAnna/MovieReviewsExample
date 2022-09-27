package com.example.android.moviereviews.network

import com.example.android.moviereviews.database.DatabaseMovies
import com.squareup.moshi.Json

const val apiKey = "XATOxt0y6FMjPGBu7sdO0G6XKPkBSOcp"

data class MoviesResponse(
    @Json(name = "results")
    val movies: List<NetworkMovie>,
)

data class NetworkMovie(
    @Json(name = "display_title")
    val displayTitle: String,
    @Json(name = "multimedia")
    val multimedia: Multimedia,
    @Json
    val link: Link?,
    @Json(name = "summary_short")
    val summaryShort: String,
)

data class Multimedia(
    @Json val type: String,
    @Json(name = "src") val imgSrcUrl: String,
)

data class Link(
    @Json val url: String,
)


fun MoviesResponse.asDatabaseModel(): List<DatabaseMovies> {
    return movies.map {
        DatabaseMovies(
            displayTitle =  it.displayTitle,
            summaryShort = it.summaryShort,
            url = it.link?.url ?: "",
            imgSrcUrl = it.multimedia.imgSrcUrl
        )
    }
}


