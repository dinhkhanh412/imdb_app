package com.koro.movies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("vote_average") val voteAverage: Double,
    val overview: String? = null,
    val genres: List<Genre>? = null,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("homepage") val homepage: String? = null
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)

@Serializable
data class TrendingResponse(
    val results: List<Movie>
)

@Serializable
data class SearchResponse(
    val results: List<Movie>
)
