package com.koro.movies.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koro.movies.databinding.ItemMovieBinding
import com.koro.movies.domain.image.ImageLoader
import com.koro.movies.presenter.model.MovieViewItem

class MovieAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: (MovieViewItem) -> Unit,
) : ListAdapter<MovieViewItem, MovieAdapter.MovieViewHolder>(
    object : DiffUtil.ItemCallback<MovieViewItem>() {
        override fun areItemsTheSame(oldItem: MovieViewItem, newItem: MovieViewItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieViewItem, newItem: MovieViewItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {
    inner class MovieViewHolder(
        private val binding: ItemMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieViewItem) = with(binding) {
            imageLoader.load(item.posterPath, ivPoster)
            tvName.text = item.name
            tvReleaseDate.text = item.releaseDate
            tvOverview.text = item.overview

            if (item.voteAverage != 0.0) {
                tvVoteAverage.text = item.voteAverage.toString()
            } else {
                tvVoteAverage.isVisible = false
                ivVoteAverage.isVisible = false
            }

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
