package com.samentic.youtubeclient.features.playlist.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.di.findAppComponent
import com.samentic.youtubeclient.core.ui.safeNavigate
import com.samentic.youtubeclient.databinding.FragmentPlaylistsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import javax.inject.Inject

// TODO: Add WatchLayer playlist (id=WL)
class PlaylistsFragment : Fragment(R.layout.fragment_playlists) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binding by viewBinding(FragmentPlaylistsBinding::bind)
    private val viewModel by viewModels<PlaylistsViewModel> { viewModelFactory }
    private lateinit var adapter: PlaylistsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // region initView
        binding.srlPlaylists.setOnRefreshListener {
            viewModel.fetchPlaylists()
        }
        // endregion initView

        // region initRecyclerView
        adapter = PlaylistsAdapter {
            safeNavigate(
                PlaylistsFragmentDirections.actionPlaylistsFragmentToPlaylistItemsFragment(
                    it.id
                )
            )
        }
        binding.rvPlaylists.let { rvPlaylist ->
            rvPlaylist.adapter = adapter
            rvPlaylist.setHasFixedSize(true)
        }
        // endregion initRecyclerView

        // region initObservation
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.srlPlaylists.isRefreshing = isLoading
        }

        viewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            adapter.submitList(playlists)
        }
        // endregion initObservation
    }
}