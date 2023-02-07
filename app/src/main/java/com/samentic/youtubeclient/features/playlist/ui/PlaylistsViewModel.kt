package com.samentic.youtubeclient.features.playlist.ui

import androidx.lifecycle.*
import com.samentic.youtubeclient.features.playlist.data.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistsViewModel @Inject constructor(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {

    private var itemsPerPage = 50
    private var currentPage = 1

    private val currentPageLiveData = MutableLiveData<Int>(currentPage)
    val playlists = currentPageLiveData.switchMap {
        playlistsRepository.getPlaylists(currentPage, itemsPerPage)
            .distinctUntilChanged()
            .map { playlists -> playlists.map { playlist -> playlist.toPlaylistView() } }
    }

    val loading = MutableLiveData<Boolean>()

    init {
        fetchPlaylists()
    }

    fun fetchPlaylists() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.fetchPlaylists()
            withContext(Dispatchers.Main) {
                currentPageLiveData.value = currentPage
                loading.value = false
            }
        }
    }
}