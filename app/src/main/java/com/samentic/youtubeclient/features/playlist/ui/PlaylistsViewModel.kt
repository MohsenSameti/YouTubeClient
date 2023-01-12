package com.samentic.youtubeclient.features.playlist.ui

import androidx.lifecycle.ViewModel
import com.samentic.youtubeclient.features.playlist.data.PlaylistsRepository
import javax.inject.Inject

class PlaylistsViewModel @Inject constructor(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel(){

    suspend fun playlists() = playlistsRepository.fetchPlayLists()
}