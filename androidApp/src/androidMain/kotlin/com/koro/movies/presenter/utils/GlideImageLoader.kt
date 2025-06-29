package com.koro.movies.presenter.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.koro.movies.R
import com.koro.movies.domain.image.ImageLoadMode
import com.koro.movies.domain.image.ImageLoader

class GlideImageLoader(
    private val context: Context,
) : ImageLoader {
    private val baseUrl = "https://image.tmdb.org/t/p/"

    override fun load(path: String, into: ImageView, loadMode: ImageLoadMode) {
        Glide.with(context)
            .load("$baseUrl${loadMode.path()}$path")
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(into)
    }
}
