package com.samentic.youtubeclient.features.playlist.ui

import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samentic.youtubeclient.core.ui.inflater
import com.samentic.youtubeclient.core.ui.loadImage
import com.samentic.youtubeclient.databinding.ItemPlaylistItemBinding

class PlaylistItemsAdapter(
    private val onItemClick: (PlaylistItemView) -> Unit
) : ListAdapter<PlaylistItemView, PlaylistItemsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<PlaylistItemView>() {
        override fun areItemsTheSame(
            oldItem: PlaylistItemView,
            newItem: PlaylistItemView
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PlaylistItemView,
            newItem: PlaylistItemView
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPlaylistItemBinding.inflate(parent.inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemPlaylistItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: PlaylistItemView) {
            loadImage(binding.ivThumbnail, item.thumbnails.medium.url)
            binding.tvTitle.let { tvTitle ->
                tvTitle.text = item.title
                TooltipCompat.setTooltipText(tvTitle, item.title)
                tvTitle.setOnClickListener {
                    binding.root.performClick()
                }
            }
            binding.tvChannelTitle.text = item.videoOwnerChannelTitle
        }
    }
}