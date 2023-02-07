package com.samentic.youtubeclient.features.playlist.ui

import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.ui.inflater
import com.samentic.youtubeclient.core.ui.loadImage
import com.samentic.youtubeclient.databinding.ItemPlaylistItemBinding
import com.samentic.youtubeclient.databinding.ItemPlaylistItemsMoreLoadBinding

class PlaylistDetailAdapter(
    private val onItemClick: (PlaylistItemView) -> Unit
) : ListAdapter<PlaylistDetailAdapterItem, PlaylistDetailAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<PlaylistDetailAdapterItem>() {
        override fun areItemsTheSame(
            oldItem: PlaylistDetailAdapterItem,
            newItem: PlaylistDetailAdapterItem
        ): Boolean {
            val oldId = when (oldItem) {
                is PlaylistItemView -> oldItem.id
                is PlaylistItemsMoreItemView -> oldItem.id
                else -> ""
            }
            val newId = when (newItem) {
                is PlaylistItemView -> newItem.id
                is PlaylistItemsMoreItemView -> newItem.id
                else -> ""
            }
            return oldId == newId
        }

        override fun areContentsTheSame(
            oldItem: PlaylistDetailAdapterItem,
            newItem: PlaylistDetailAdapterItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PlaylistItemView -> R.layout.item_playlist_item
            is PlaylistItemsMoreItemView -> R.layout.item_playlist_items_more_load
            else -> throw IllegalStateException("Unknown type at position=$position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.item_playlist_item -> {
                PlaylistItemViewHolder(
                    ItemPlaylistItemBinding.inflate(parent.inflater, parent, false)
                )
            }
            R.layout.item_playlist_items_more_load -> {
                MoreLoadingViewHolder(
                    ItemPlaylistItemsMoreLoadBinding.inflate(parent.inflater, parent, false)
                )
            }
            else -> throw IllegalStateException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PlaylistItemView -> (holder as PlaylistItemViewHolder).bind(item)
        }
    }

    inner class MoreLoadingViewHolder(
        binding: ItemPlaylistItemsMoreLoadBinding
    ) : ViewHolder(binding)

    inner class PlaylistItemViewHolder(
        private val binding: ItemPlaylistItemBinding
    ) : ViewHolder(binding) {

        init {
            binding.root.setOnClickListener {
                (getItem(bindingAdapterPosition) as? PlaylistItemView)?.let {
                    onItemClick(it)
                }
            }
        }

        fun bind(item: PlaylistItemView) {
            loadImage(binding.ivThumbnail, item.thumbnails?.mediumUrl)
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

    abstract class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
}