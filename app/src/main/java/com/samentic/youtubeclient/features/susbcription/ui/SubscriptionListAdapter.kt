package com.samentic.youtubeclient.features.susbcription.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.ui.inflater
import com.samentic.youtubeclient.core.ui.loadImage
import com.samentic.youtubeclient.databinding.ItemSubscriptionBinding
import com.samentic.youtubeclient.databinding.ItemSubscriptionMoreLoadBinding

class SubscriptionListAdapter(
    private val onSubscriptionClick: (SubscriptionView) -> Unit
) : ListAdapter<SubscriptionAdapterView, SubscriptionListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SubscriptionAdapterView>() {
        override fun areItemsTheSame(
            oldItem: SubscriptionAdapterView,
            newItem: SubscriptionAdapterView
        ): Boolean {
            if (oldItem is SubscriptionView && newItem is SubscriptionView) {
                return oldItem.id == newItem.id
            }
            if (oldItem is SubscriptionMoreItemView && newItem is SubscriptionMoreItemView) {
                return oldItem.id == newItem.id
            }

            return false
        }

        override fun areContentsTheSame(
            oldItem: SubscriptionAdapterView,
            newItem: SubscriptionAdapterView
        ): Boolean {
            if (oldItem is SubscriptionView && newItem is SubscriptionView) {
                return oldItem.title == newItem.title &&
                        oldItem.thumbnails.hashCode() == newItem.thumbnails.hashCode()
            }
            if (oldItem is SubscriptionMoreItemView && newItem is SubscriptionMoreItemView) {
                return oldItem.hashCode() == newItem.hashCode()
            }

            return true
        }
    }
) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SubscriptionView -> R.layout.item_subscription
            is SubscriptionMoreItemView -> R.layout.item_subscription_more_load
            else -> throw IllegalArgumentException("Invalid view type at position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.item_subscription -> {
                return SubscriptionViewHolder(
                    ItemSubscriptionBinding.inflate(parent.inflater, parent, false)
                )
            }
            R.layout.item_subscription_more_load -> {
                return MoreLoadingViewHolder(
                    ItemSubscriptionMoreLoadBinding.inflate(parent.inflater, parent, false)
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is SubscriptionView -> (holder as SubscriptionViewHolder).bind(item)
        }
    }

    inner class MoreLoadingViewHolder(
        binding: ItemSubscriptionMoreLoadBinding
    ) : ViewHolder(binding)

    inner class SubscriptionViewHolder(
        private val binding: ItemSubscriptionBinding
    ) : ViewHolder(binding) {
        init {
            binding.root.setOnClickListener {
                (getItem(absoluteAdapterPosition) as? SubscriptionView)?.let(onSubscriptionClick)
            }
        }

        fun bind(sub: SubscriptionView) {
            loadImage(binding.ivThumbnail, sub.thumbnails?.defaultUrl, isCircular = true)
            binding.tvTitle.text = sub.title
        }
    }

    abstract class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
}