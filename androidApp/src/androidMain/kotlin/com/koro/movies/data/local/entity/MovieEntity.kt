package com.koro.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.koro.movies.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
    val isTrending: Boolean,
    val cacheTimestamp: Long,
    val sortOrder: Int = 0
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            posterPath = posterPath,
            backdropPath = backdropPath,
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            overview = overview
        )
    }

    companion object {
        fun fromMovie(movie: Movie): MovieEntity {
            return MovieEntity(
                id = movie.id,
                title = movie.title,
                posterPath = movie.posterPath,
                backdropPath = movie.backdropPath,
                releaseDate = movie.releaseDate ?: "",
                voteAverage = movie.voteAverage,
                overview = movie.overview ?: "",
                isTrending = true,
                cacheTimestamp = System.currentTimeMillis(),
            )
        }
    }
}
