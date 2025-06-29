package com.koro.movies.api

import com.koro.movies.common.ApiResult
import com.koro.movies.model.Movie
import com.koro.movies.model.MovieDetail
import com.koro.movies.model.SearchResponse
import com.koro.movies.model.TrendingResponse
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.utils.io.CancellationException
import kotlinx.io.IOException

expect fun getTmdbApiKey(): String
expect fun logMessage(message: String)
expect fun createHttpClient(): HttpClient

class TmdbApi {
    private val client: HttpClient = createHttpClient()
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val apiKey: String = getTmdbApiKey()

    internal suspend inline fun <reified R> callApi(
        path: String,
    ): ApiResult<R> {
        return try {
            val response = client.get("$baseUrl$path")
            val data = response.body<R>()
            ApiResult.Success(data)
        } catch (e: IOException) {
            ApiResult.Error(e)
        } catch (e: Exception) {
            logMessage("Unhandled API exception: ${e.message}")
            ApiResult.Error(e)
        }
    }

    suspend fun getTrendingMovies(): ApiResult<TrendingResponse> {
        return callApi<TrendingResponse>("trending/movie/day")
    }

    suspend fun searchMovies(query: String): ApiResult<SearchResponse> {
        return callApi<SearchResponse>("search/movie?query=$query")
    }

    suspend fun getMovieDetails(id: String): ApiResult<MovieDetail> {
        return callApi<MovieDetail>("movie/$id")
    }
}
