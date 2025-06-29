package com.koro.movies.presenter.model

data class MovieDetailUiModel(
    val backdropPath: String? = null,
    val budget: Int? = null,
    val genres: List<String>? = emptyList(),
    val homepage: String? = null,
    val id: Int? = null,
    val imdbId: String? = null,
    val title: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val movieDetailInfos: List<ItemMovieInfoUiModel>? = emptyList(),
    val productionCompanies: List<ItemProductionCompanyUiModel>? = emptyList(),
    val releaseDate: String? = null,
    val runtime: Int? = null,
)
