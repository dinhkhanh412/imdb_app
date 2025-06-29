package com.koro.movies.presenter.model

data class MovieViewItem(
    val id: String,
    val posterPath: String,
    val name: String,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
)
