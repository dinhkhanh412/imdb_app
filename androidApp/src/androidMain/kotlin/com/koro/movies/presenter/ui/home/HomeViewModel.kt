package com.koro.movies.presenter.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koro.movies.data.common.DataResult
import com.koro.movies.domain.repository.MovieRepository
import com.koro.movies.model.Movie
import com.koro.movies.presenter.model.MovieViewItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        getTrendingMovies()
    }

    fun loadLocalTrendingMovie() {
        viewModelScope.launch {
            movieRepository
                .getTrendingMovies(
                    loadLocalOnly = true
                )
                .collect { dataResult ->
                    when (dataResult) {
                        is DataResult.FromLocal -> {
                            _homeUiState.value = HomeUiState.ShowTrendingMovies(
                                dataResult.data.map { movie ->
                                    movie.toUiModel()
                                }
                            )
                        }

                        else -> Unit
                    }
                }
        }
    }

    fun getTrendingMovies() {
        viewModelScope.launch {
            movieRepository
                .getTrendingMovies()
                .collect { dataResult ->
                    when (dataResult) {
                        is DataResult.FromLocal -> {
                            _homeUiState.value = HomeUiState.ShowTrendingMovies(
                                dataResult.data.map { movie ->
                                    movie.toUiModel()
                                })
                        }

                        is DataResult.FromRemote -> {
                            _homeUiState.value = HomeUiState.ShowTrendingMovies(
                                dataResult.data.map { movie ->
                                    movie.toUiModel()
                                })
                        }

                        is DataResult.Error -> {
                            Log.e("HomeViewModel", "Error: ${dataResult.exception}")
                        }
                    }
                }
        }

    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            movieRepository
                .searchMovies(query)
                .collect { dataResult ->
                    when (dataResult) {
                        is DataResult.FromRemote -> {
                            _homeUiState.value = HomeUiState.ShowSearchMovies(
                                dataResult.data.map { movie ->
                                    movie.toUiModel()
                                })
                        }

                        is DataResult.Error -> {
                            Log.e("HomeViewModel", "Error: ${dataResult.exception}")
                        }

                        else -> Unit
                    }
                }
        }
    }


    private fun Movie.toUiModel(): MovieViewItem {
        return MovieViewItem(
            id = id.toString(),
            posterPath = posterPath.orEmpty(),
            name = title,
            releaseDate = releaseDate.orEmpty(),
            voteAverage = voteAverage,
            overview = overview.orEmpty()
        )
    }
}


sealed class HomeUiState() {
    object Loading : HomeUiState()
    data class ShowTrendingMovies(val movies: List<MovieViewItem>) : HomeUiState()
    data class ShowSearchMovies(val movies: List<MovieViewItem>) : HomeUiState()
    object Error : HomeUiState()
}
