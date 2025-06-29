package com.koro.movies.domain.repository

import com.koro.movies.data.common.DataResult
import com.koro.movies.model.Movie
import com.koro.movies.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTrendingMovies(loadLocalOnly: Boolean = false): Flow<DataResult<List<Movie>>>
    suspend fun searchMovies(query: String): Flow<DataResult<List<Movie>>>
    suspend fun getMovieDetail(movieId: String): Flow<DataResult<MovieDetail>>
}
