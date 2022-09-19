package com.example.android.moviereviews.network

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "display_title")
    val displayTitle: String?,
    @Json(name = "multimedia")
    val multimedia: Multimedia?,
    @Json
    val link: Link?,
    @Json(name = "summary_short")
    val summaryShort: String?,
)

data class Multimedia(
    @Json val type: String,
    @Json(name = "src") val imgSrcUrl: String,
)

data class Link(
    @Json val url: String,
)


