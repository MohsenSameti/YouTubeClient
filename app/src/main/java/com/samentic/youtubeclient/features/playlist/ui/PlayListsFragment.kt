package com.samentic.youtubeclient.features.playlist.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.di.findAppComponent
import com.samentic.youtubeclient.databinding.FragmentPlaylistsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayListsFragment : Fragment(R.layout.fragment_playlists) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binding by viewBinding(FragmentPlaylistsBinding::bind)
    private val viewModel by viewModels<PlaylistsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.root.setOnClickListener {
            Toast.makeText(requireContext(), "Oops", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launchWhenStarted {
            val value = withContext(Dispatchers.IO) {
                val playlists = viewModel.playlists()
                playlists.items.joinToString("\n") { it.kind }
            }
            binding.tmp.text = value
        }
    }
}