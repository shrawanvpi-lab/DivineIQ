package com.divineiq.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.divineiq.app.model.HomeItem
import com.divineiq.app.databinding.ItemHomeCardBinding

/**
 * Displays the list of recommended [HomeItem]s on the Home screen.
 */
class HomeAdapter(
    private val onItemClick: (HomeItem) -> Unit
) : ListAdapter<HomeItem, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HomeViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HomeViewHolder(
        private val binding: ItemHomeCardBinding,
        private val onItemClick: (HomeItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeItem) {
            binding.textHomeCategory.text = item.category
            binding.textHomeTitle.text = item.title
            binding.textHomeSubtitle.text = item.subtitle
            binding.imageHomeItem.load(item.imageUrl) {
                crossfade(true)
                placeholder(com.divineiq.app.R.drawable.bg_rounded_image_placeholder)
                error(com.divineiq.app.R.drawable.bg_rounded_image_placeholder)
            }
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeItem>() {
            override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem) =
                oldItem == newItem
        }
    }
}
