package com.samentic.youtubeclient.features.channel.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.di.findAppComponent
import com.samentic.youtubeclient.core.ui.loadImage
import com.samentic.youtubeclient.core.ui.safeNavigate
import com.samentic.youtubeclient.databinding.FragmentChannelDetailBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChannelDetailFragment : Fragment(R.layout.fragment_channel_detail) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binding by viewBinding(FragmentChannelDetailBinding::bind)
    private val params by navArgs<ChannelDetailFragmentArgs>()
    private val viewModel by viewModels<ChannelDetailViewModel> { viewModelFactory }

    private var uploadsPlaylistId: String? = null

    private val decimalFormat: DecimalFormat by lazy {
        val df = DecimalFormat.getInstance(Locale.US) as DecimalFormat
        df.groupingSize = 3
        df.isGroupingUsed = true
        df.maximumFractionDigits = 0
        df.isDecimalSeparatorAlwaysShown = false
        df
    }
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // region initValue
        uploadsPlaylistId = params.uploadsPlaylist
        viewModel.setChannelId(params.channelId)
        binding.btnVideos.setOnClickListener {
            val id = this.uploadsPlaylistId ?: return@setOnClickListener
            safeNavigate(
                ChannelDetailFragmentDirections.actionChannelDetailFragmentToPlaylistItemsFragment(
                    id
                )
            )
        }
        // endregion initValue

        // region initView
        binding.tvDescription.setOnClickListener {
            if (binding.tvDescription.text.isNotBlank()) {
                if (binding.tvDescription.maxLines == 2) {
                    binding.tvDescription.maxLines = Int.MAX_VALUE
                } else {
                    binding.tvDescription.maxLines = 2
                }
            }
        }

        binding.srlChannelDetail.setOnRefreshListener {
            viewModel.fetchChannelDetail()
        }
        // endregion initView

        // region initObservation
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.srlChannelDetail.isRefreshing = isLoading
        }
        viewModel.detail.observe(viewLifecycleOwner) {
            val channel = it ?: return@observe
            bindChannel(channel)
        }
        // endregion initObservation
    }

    private fun bindChannel(channel: ChannelView) {
        loadImage(binding.ivThumbnail, channel.thumbnail?.highUrl)
        binding.tvTitle.text = channel.title
        binding.tvDescription.let { tvDescription ->
            tvDescription.isVisible = !channel.description.isNullOrBlank()
            tvDescription.text = channel.description.orEmpty()
        }
        uploadsPlaylistId = channel.relatedPlaylists.uploads
        binding.tvSubscriberCount.let {
            if (channel.hiddenSubscriberCount) {
                binding.tvSubscriberCount.text = "-"
            } else {
                binding.tvSubscriberCount.text = decimalFormat.format(channel.subscriberCount)
            }
        }
        binding.tvViewCount.text = decimalFormat.format(channel.viewCount)
        binding.tvVideoCount.text = decimalFormat.format(channel.videoCount)
        binding.tvSince.text = dateFormatter.format(channel.publishedAt.value)
    }
}