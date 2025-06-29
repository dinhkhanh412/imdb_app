package com.koro.movies

import android.app.Application
import com.koro.movies.di.databaseModule
import com.koro.movies.di.imageLoader
import com.koro.movies.di.networkModule
import com.koro.movies.di.repositoryModule
import com.koro.movies.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    imageLoader,
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}
