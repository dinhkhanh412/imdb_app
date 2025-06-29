package com.koro.movies.api

import android.util.Log
import com.koro.movies.shared.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException

actual fun getTmdbApiKey(): String {
    return BuildConfig.TMDB_API_KEY
}

actual fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        engine {
            addInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${getTmdbApiKey()}")
                    .addHeader("accept", "application/json")
                    .build()
                chain.proceed(request)
            }
        }
        install(ContentNegotiation) {
            json(
                contentType = io.ktor.http.ContentType.Application.Json,
                json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                }
            )
        }
    }
}

actual fun logMessage(message: String) {
    Log.d("TmdbApiAndroid", message)
}
