package com.koro.movies.presenter.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koro.movies.data.common.DataResult
import com.koro.movies.domain.repository.MovieRepository
import com.koro.movies.model.MovieDetail
import com.koro.movies.presenter.model.ItemMovieInfoUiModel
import com.koro.movies.presenter.model.ItemProductionCompanyUiModel
import com.koro.movies.presenter.model.MovieDetailUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetailUiModel?>(null)
    val movieDetail: StateFlow<MovieDetailUiModel?> = _movieDetail.asStateFlow()

    fun getMovieDetail(id: String) {
        viewModelScope.launch {
            movieRepository
                .getMovieDetail(id)
                .collect { dataResult ->
                    when (dataResult) {
                        is DataResult.FromLocal -> {
                            _movieDetail.value = dataResult.data.toUiModel()
                        }

                        is DataResult.FromRemote -> {
                            _movieDetail.value = dataResult.data.toUiModel()
                        }

                        is DataResult.Error -> {
                            Log.e("DetailViewModel", "Error: ${dataResult.exception}")
                        }
                    }
                }
        }
    }

    fun MovieDetail.toUiModel(): MovieDetailUiModel {
        val detailInfo = listOf<ItemMovieInfoUiModel>(
            ItemMovieInfoUiModel("Release Date", releaseDate.orEmpty()),
            ItemMovieInfoUiModel("Original Language", originalLanguage.orEmpty()),
            ItemMovieInfoUiModel("Runtime", runtime.toString()),
            ItemMovieInfoUiModel("Status", status.orEmpty()),
            ItemMovieInfoUiModel("Vote Average", voteAverage.toString()),
            ItemMovieInfoUiModel("Vote Count", voteCount.toString()),
            ItemMovieInfoUiModel("Budget", "$${budget.toString()}"),
            ItemMovieInfoUiModel("Revenue", "$${revenue.toString()}"),
        )
        return MovieDetailUiModel(
            title = title,
            backdropPath = backdropPath,
            posterPath = posterPath,
            genres = genres?.map { it.name },
            homepage = homepage,
            id = id,
            imdbId = imdbId,
            overview = overview,
            popularity = popularity,
            movieDetailInfos = detailInfo,
            releaseDate = releaseDate,
            runtime = runtime,
            productionCompanies = productionCompanies?.map {
                ItemProductionCompanyUiModel(it.name.toString(), it.logoPath)
            },
        )
    }
}
