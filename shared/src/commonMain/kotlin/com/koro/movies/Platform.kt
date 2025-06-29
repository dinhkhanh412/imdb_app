package com.koro.movies

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform