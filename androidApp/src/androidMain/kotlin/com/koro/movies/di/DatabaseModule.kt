package com.koro.movies.di

import androidx.room.Room
import com.koro.movies.data.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "movie.db"
        ).build()
    }

    single { get<AppDatabase>().movieDao() }
}

