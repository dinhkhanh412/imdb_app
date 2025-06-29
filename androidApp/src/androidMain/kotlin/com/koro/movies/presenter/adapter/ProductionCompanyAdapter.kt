package com.koro.movies.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koro.movies.databinding.ItemProductionCompanyBinding
import com.koro.movies.domain.image.ImageLoadMode
import com.koro.movies.domain.image.ImageLoader
import com.koro.movies.presenter.model.ItemProductionCompanyUiModel

class ProductionCompanyAdapter(
    private val imageLoader: ImageLoader,
) : ListAdapter<ItemProductionCompanyUiModel, ProductionCompanyAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemProductionCompanyUiModel>() {
            override fun areItemsTheSame(old: ItemProductionCompanyUiModel, new: ItemProductionCompanyUiModel) =
                old.name == new.name

            override fun areContentsTheSame(old: ItemProductionCompanyUiModel, new: ItemProductionCompanyUiModel) =
                old == new
        }
    }

    inner class ViewHolder(private val binding: ItemProductionCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemProductionCompanyUiModel) {
            binding.tvName.text = item.name
            item.logoPath?.let {
                imageLoader.load(
                    url = item.logoPath,
                    into = binding.ivLogo,
                    loadMode = ImageLoadMode.w200
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductionCompanyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
