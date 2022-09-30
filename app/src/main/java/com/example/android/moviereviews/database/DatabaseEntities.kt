package com.example.android.moviereviews.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.moviereviews.domain.Movie

@Entity
data class DatabaseMovies(
    @PrimaryKey
    val url: String,
    val displayTitle: String,
    val imgSrcUrl: String,
    val summaryShort: String
    )

fun List<DatabaseMovies>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            url = it.url,
            displayTitle = it.displayTitle,
            imgSrcUrl = it.imgSrcUrl,
            summaryShort = it.summaryShort
        )
    }
}
