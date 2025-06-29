package com.koro.movies.di

import com.koro.movies.domain.image.ImageLoader
import com.koro.movies.presenter.utils.GlideImageLoader
import org.koin.dsl.module

val imageLoader = module {
    single<ImageLoader> { GlideImageLoader(context = get()) }
}

