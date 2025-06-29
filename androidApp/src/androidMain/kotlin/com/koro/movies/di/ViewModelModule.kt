package com.koro.movies.di

import com.koro.movies.presenter.ui.detail.MovieDetailViewModel
import com.koro.movies.presenter.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}
