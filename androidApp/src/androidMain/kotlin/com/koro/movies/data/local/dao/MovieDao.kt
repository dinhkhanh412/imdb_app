package com.koro.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.koro.movies.data.local.entity.MovieDetailEntity
import com.koro.movies.data.local.entity.MovieEntity


@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE isTrending = 1 ORDER BY sortOrder ASC")
    fun getTrendingMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun deleteTrendingMovies()

    @Query("SELECT * FROM movie_detail WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: String): MovieDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)
}
