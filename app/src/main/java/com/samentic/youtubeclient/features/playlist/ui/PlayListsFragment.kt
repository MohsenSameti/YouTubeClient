package com.samentic.youtubeclient.features.playlist.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.databinding.FragmentPlaylistsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class PlayListsFragment : Fragment(R.layout.fragment_playlists) {
    private val binding by viewBinding(FragmentPlaylistsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.root.setOnClickListener {
            Toast.makeText(requireContext(), "Oops", Toast.LENGTH_SHORT).show()
        }
    }
}