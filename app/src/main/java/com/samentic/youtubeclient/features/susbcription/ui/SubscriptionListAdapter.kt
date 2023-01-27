package com.samentic.youtubeclient.features.susbcription.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samentic.youtubeclient.core.ui.inflater
import com.samentic.youtubeclient.core.ui.loadImage
import com.samentic.youtubeclient.databinding.ItemSubscriptionBinding

class SubscriptionListAdapter(
    private val onSubscriptionClick: (SubscriptionView) -> Unit
) : ListAdapter<SubscriptionView, SubscriptionListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SubscriptionView>() {
        override fun areItemsTheSame(
            oldItem: SubscriptionView,
            newItem: SubscriptionView
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SubscriptionView,
            newItem: SubscriptionView
        ): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.thumbnails.hashCode() == newItem.thumbnails.hashCode()
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSubscriptionBinding.inflate(parent.inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemSubscriptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onSubscriptionClick(getItem(absoluteAdapterPosition))
            }
        }

        fun bind(sub: SubscriptionView) {
            loadImage(binding.ivThumbnail, sub.thumbnails.default.url, isCircular = true)
            binding.tvTitle.text = sub.title
        }
    }
}