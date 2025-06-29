package com.koro.movies.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koro.movies.databinding.ItemMovieDetailInfoBinding
import com.koro.movies.presenter.model.ItemMovieInfoUiModel

class MovieInfoAdapter : ListAdapter<ItemMovieInfoUiModel, MovieInfoAdapter.MovieInfoViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemMovieInfoUiModel>() {
            override fun areItemsTheSame(oldItem: ItemMovieInfoUiModel, newItem: ItemMovieInfoUiModel) =
                oldItem.label == newItem.label

            override fun areContentsTheSame(oldItem: ItemMovieInfoUiModel, newItem: ItemMovieInfoUiModel) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieInfoViewHolder {
        val binding = ItemMovieDetailInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieInfoViewHolder(
        private val binding: ItemMovieDetailInfoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemMovieInfoUiModel) {
            binding.tvLabel.text = item.label
            binding.tvValue.text = item.value
        }
    }
}
