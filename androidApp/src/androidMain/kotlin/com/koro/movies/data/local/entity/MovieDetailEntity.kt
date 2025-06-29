package com.koro.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koro.movies.model.Genre
import com.koro.movies.model.MovieDetail
import com.koro.movies.model.ProductionCompany
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.sql.Types

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return list?.joinToString(separator = ",") ?: ""
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return if (data.isEmpty()) emptyList() else data.split(",")
    }
}

class ProductionCompanyConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<ProductionCompanyEntity>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(json: String): List<ProductionCompanyEntity> {
        val type = object : TypeToken<List<ProductionCompanyEntity>>() {}.type
        return gson.fromJson(json, type)
    }
}

data class ProductionCompanyEntity(
    @SerialName("logo_path") val logoPath: String?,
    @SerialName("name") val name: String?,
)

@Serializable
@Entity(tableName = "movie_detail")
@TypeConverters(Converters::class, ProductionCompanyConverter::class)
data class MovieDetailEntity(
    @SerialName("adult")
    @ColumnInfo(name = "adult")
    val adult: Boolean? = false,

    @SerialName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,

    @SerialName("budget")
    @ColumnInfo(name = "budget")
    val budget: Int?,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "genres")
    val genres: List<String>? = emptyList(),

    @SerialName("homepage")
    @ColumnInfo(name = "homepage")
    val homepage: String? = null,

    @SerialName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @SerialName("imdb_id")
    @ColumnInfo(name = "imdb_id")
    val imdbId: String? = null,

    @SerialName("original_language")
    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = null,

    @SerialName("original_title")
    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,

    @SerialName("overview")
    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @SerialName("popularity")
    @ColumnInfo(name = "popularity")
    val popularity: Double? = null,

    @SerialName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,

    @SerialName("production_companies")
    @ColumnInfo(name = "production_companies")
    @TypeConverters(ProductionCompanyConverter::class)
    val productionCompanies: List<ProductionCompanyEntity>?,

    @SerialName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    @SerialName("revenue")
    @ColumnInfo(name = "revenue")
    val revenue: Int? = 0,

    @SerialName("runtime")
    @ColumnInfo(name = "runtime")
    val runtime: Int? = 0,

    @SerialName("status")
    @ColumnInfo(name = "status")
    val status: String? = null,

    @SerialName("tagline")
    @ColumnInfo(name = "tagline")
    val tagline: String? = null,

    @SerialName("title")
    @ColumnInfo(name = "title")
    val title: String? = null,

    @SerialName("video")
    @ColumnInfo(name = "video")
    val video: Boolean? = false,

    @SerialName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @SerialName("vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = 0,
) {
    fun toMovieDetail(): MovieDetail {
        return MovieDetail(
            id = id,
            title = title,
            posterPath = posterPath,
            backdropPath = backdropPath,
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            overview = overview,
            adult = adult,
            budget = budget,
            genres = genres?.map {
                Genre(
                    id = 0,
                    name = it
                )
            },
            homepage = homepage,
            imdbId = imdbId,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            popularity = popularity,
            productionCompanies = productionCompanies?.map {
                ProductionCompany(
                    name = it.name,
                    logoPath = it.logoPath,
                    id = 0,
                    originCountry = "",
                )
            },
            revenue = revenue,
            runtime = runtime,
            status = status,
            tagline = tagline,
            video = video,
            voteCount = voteCount,
        )
    }

    companion object {
        fun fromMovie(movie: MovieDetail): MovieDetailEntity {
            return MovieDetailEntity(
                id = movie.id,
                title = movie.title,
                posterPath = movie.posterPath,
                backdropPath = movie.backdropPath,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage,
                overview = movie.overview,
                adult = movie.adult,
                budget = movie.budget,
                genres = movie.genres?.map {
                    it.name
                },
                homepage = movie.homepage,
                imdbId = movie.imdbId,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                popularity = movie.popularity,
                productionCompanies = movie.productionCompanies?.map {
                    ProductionCompanyEntity(
                        name = it?.name,
                        logoPath = it.logoPath
                    )
                },
                revenue = movie.revenue,
                runtime = movie.runtime,
                status = movie.status,
                tagline = movie.tagline,
                video = movie.video,
                voteCount = movie.voteCount,
            )
        }
    }
}
