package com.samentic.youtubeclient.features.playlist.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.ui.inflater
import com.samentic.youtubeclient.core.ui.loadImage
import com.samentic.youtubeclient.databinding.ItemPlaylistBinding

class PlaylistsAdapter(
    private val onPlaylistClick: (PlaylistView) -> Unit
) : ListAdapter<PlaylistView, PlaylistsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<PlaylistView>() {
        override fun areItemsTheSame(oldItem: PlaylistView, newItem: PlaylistView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlaylistView, newItem: PlaylistView): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPlaylistBinding.inflate(parent.inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemPlaylistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onPlaylistClick.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: PlaylistView) {
            loadImage(binding.ivThumbnail, item.thumbnail?.mediumUrl)
            binding.tvItemCount.text = binding.root.context.getString(
                R.string.label_videos_count,
                item.itemCount
            )
            binding.tvTitle.text = item.title
        }
    }
}