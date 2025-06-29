package com.koro.movies.di

import com.koro.movies.api.TmdbApi
import org.koin.dsl.module

val networkModule = module {
    single { TmdbApi() }
}
