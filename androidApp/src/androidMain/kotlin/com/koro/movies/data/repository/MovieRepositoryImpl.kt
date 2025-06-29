package com.koro.movies.data.repository

import android.util.Log
import com.koro.movies.api.TmdbApi
import com.koro.movies.common.ApiResult
import com.koro.movies.data.common.DataResult
import com.koro.movies.data.local.dao.MovieDao
import com.koro.movies.data.local.entity.MovieDetailEntity
import com.koro.movies.data.local.entity.MovieEntity
import com.koro.movies.domain.repository.MovieRepository
import com.koro.movies.model.Movie
import com.koro.movies.model.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    private val api: TmdbApi,
    private val dao: MovieDao,
) : MovieRepository {
    override fun getTrendingMovies(loadLocalOnly: Boolean): Flow<DataResult<List<Movie>>> = flow {
        val localMovies = dao
            .getTrendingMovies()
            .map { it.toMovie() }
        if (localMovies.isNotEmpty()) {
            emit(DataResult.FromLocal(localMovies))
            if (loadLocalOnly) return@flow
        }

        when (val result = api.getTrendingMovies()) {
            is ApiResult.Success -> {
                val sortedEntities = result.data.results.mapIndexed { index, movie ->
                    MovieEntity
                        .fromMovie(movie)
                        .copy(sortOrder = index)
                }
                dao.deleteTrendingMovies()
                dao.insertMovies(sortedEntities)
                emit(DataResult.FromRemote(result.data.results))
            }

            is ApiResult.Error -> Unit
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchMovies(query: String): Flow<DataResult<List<Movie>>> = flow {
        when (val result = api.searchMovies(query)) {
            is ApiResult.Success -> {
                emit(DataResult.FromRemote(result.data.results))
            }

            is ApiResult.Error -> {
                emit(DataResult.Error(result.exception))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetail(movieId: String): Flow<DataResult<MovieDetail>> = flow {
        val localMovies = dao
            .getMovieDetail(movieId)
            ?.toMovieDetail()
        if (localMovies != null) {
            emit(DataResult.FromLocal(localMovies))
        }

        when (val result = api.getMovieDetails(movieId)) {
            is ApiResult.Success -> {
                dao.insertMovieDetail(MovieDetailEntity.fromMovie(result.data))
                emit(DataResult.FromRemote(result.data))
            }

            is ApiResult.Error -> {
                emit(DataResult.Error(result.exception))
            }
        }
    }.flowOn(Dispatchers.IO)
}
