package com.koro.movies.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class ProductionCompany(
    @SerialName("id") val id: Int? = null,
    @SerialName("logo_path") val logoPath: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("origin_country") val originCountry: String? = null,
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1") val iso3166_1: String? = null,
    @SerialName("name") val name: String? = null,
)

@Serializable
data class SpokenLanguage(
    @SerialName("english_name") val englishName: String? = null,
    @SerialName("iso_639_1") val iso639_1: String? = null,
    @SerialName("name") val name: String? = null,
)

@Serializable
data class MovieDetail(
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("budget") val budget: Int? = null,
    @SerialName("genres") val genres: List<Genre>? = emptyList(),
    @SerialName("homepage") val homepage: String? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = emptyList(),
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompany>? = emptyList(),
    @SerialName("production_countries") val productionCountries: List<ProductionCountry>? = emptyList(),
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("revenue") val revenue: Int? = null,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguage>? = emptyList(),
    @SerialName("status") val status: String? = null,
    @SerialName("tagline") val tagline: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
)
