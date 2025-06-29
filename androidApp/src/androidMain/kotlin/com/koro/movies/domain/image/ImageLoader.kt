package com.koro.movies.domain.image

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, into: ImageView, loadMode: ImageLoadMode = ImageLoadMode.original)
}

enum class ImageLoadMode{
    w200,
    original;

    fun path(): String = "$name/"
}
