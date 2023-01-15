package com.samentic.youtubeclient.features.playlist.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.di.findAppComponent
import com.samentic.youtubeclient.databinding.FragmentPlaylistItemsBinding
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
            viewModel.fetchPlayListItems()
        }
        // endregion initView

        // region initRecyclerView
        adapter = PlaylistItemsAdapter {
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }

        binding.rvPlaylistItems.let { rvPlaylistItems ->
            rvPlaylistItems.setHasFixedSize(true)
            rvPlaylistItems.adapter = adapter
        }
        // endregion initRecyclerView

        // region initObservation
        viewModel.loading.observe(viewLifecycleOwner) { isRefreshing ->
            binding.srlPlaylistItems.isRefreshing = isRefreshing
        }

        viewModel.playlistItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
        // endregion initObservation
    }
}