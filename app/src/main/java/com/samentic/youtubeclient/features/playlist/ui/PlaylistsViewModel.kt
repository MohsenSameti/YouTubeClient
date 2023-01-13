package com.samentic.youtubeclient.features.playlist.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samentic.youtubeclient.features.playlist.data.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistsViewModel @Inject constructor(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {

    val playlists = MutableLiveData<List<PlaylistView>>()

    val loading = MutableLiveData<Boolean>()

    init {
        fetchPlaylists()
    }

    fun fetchPlaylists() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val playlists = playlistsRepository.fetchPlayLists().map { it.toPlaylistView() }
            withContext(Dispatchers.Main.immediate) {
                this@PlaylistsViewModel.playlists.value = playlists
                loading.value = false
            }
        }
    }
}