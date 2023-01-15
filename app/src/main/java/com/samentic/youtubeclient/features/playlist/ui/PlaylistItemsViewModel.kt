package com.samentic.youtubeclient.features.playlist.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samentic.youtubeclient.features.playlist.data.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistItemsViewModel @Inject constructor(
    private val playlistsRepository: PlaylistsRepository,
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    // TODO: use savedState or assisted
    lateinit var playlistId: String
        private set

    val playlistItems = MutableLiveData<List<PlaylistItemView>>()

    fun setPlaylistId(id: String) {
        playlistId = id
        fetchPlayListItems()
    }

    fun fetchPlayListItems() {
        if(!this::playlistId.isInitialized) return

        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val items = playlistsRepository.fetchPlayListItems(playlistId)
            withContext(Dispatchers.Main) {
                playlistItems.value = items.map { it.toPlaylistItemView() }
                loading.value = false
            }
        }
    }
}