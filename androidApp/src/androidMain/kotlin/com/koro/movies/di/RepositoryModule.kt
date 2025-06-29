package com.koro.movies.di

import com.koro.movies.data.repository.MovieRepositoryImpl
import com.koro.movies.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}
