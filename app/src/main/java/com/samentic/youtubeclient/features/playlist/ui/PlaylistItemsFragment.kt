package com.samentic.youtubeclient.features.playlist.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.di.findAppComponent
import com.samentic.youtubeclient.core.ui.pagination.PaginationScrollListener
import com.samentic.youtubeclient.databinding.FragmentPlaylistItemsBinding
import com.samentic.youtubeclient.features.player.PlayerActivity
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import javax.inject.Inject

class PlaylistItemsFragment : Fragment(R.layout.fragment_playlist_items) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binding by viewBinding(FragmentPlaylistItemsBinding::bind)
    private val viewModel by viewModels<PlaylistItemsViewModel> { viewModelFactory }
    private lateinit var adapter: PlaylistItemsAdapter
    private val params by navArgs<PlaylistItemsFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // region initValue
        viewModel.setPlaylistId(params.playlistId)
        // endregion initValue

        // region initView
        binding.srlPlaylistItems.setOnRefreshListener {
            viewModel.refresh()
        }
        // endregion initView

        // region initRecyclerView
        adapter = PlaylistItemsAdapter { item ->
            startActivity(
                Intent(requireContext(), PlayerActivity::class.java).also {
                    it.putExtra(PlayerActivity.EXTRA_VIDEO_ID, item.id)
                }
            )
        }

        binding.rvPlaylistItems.let { rvPlaylistItems ->
            rvPlaylistItems.setHasFixedSize(true)
            rvPlaylistItems.adapter = adapter
            rvPlaylistItems.addOnScrollListener(
                object : PaginationScrollListener() {
                    override fun isLoading(): Boolean {
                        return viewModel.isLoadingItems() ||
                                // TODO: can we somehow move this to scrollListener?
                                adapter.currentList.lastOrNull() is PlayListItemsMoreItemView
                    }

                    override fun loadMore() {
                        viewModel.fetchNextPage()
                    }
                }
            )
        }
        // endregion initRecyclerView

        // region initObservation
        viewModel.loading.observe(viewLifecycleOwner) { isRefreshing ->
            binding.srlPlaylistItems.isRefreshing = isRefreshing
        }

        viewModel.moreLoading.observe(viewLifecycleOwner) { moreLoading ->
            binding.srlPlaylistItems.isEnabled = !moreLoading
            if (moreLoading) {
                if (adapter.currentList.isNotEmpty() &&
                    adapter.currentList.last() !is PlayListItemsMoreItemView
                ) {
                    adapter.submitList(
                        buildList {
                            addAll(adapter.currentList)
                            add(PlayListItemsMoreItemView())
                        }
                    )
                }
            }
        }

        viewModel.playlistItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
        // endregion initObservation
    }
}