package com.samentic.youtubeclient.features.playlist.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samentic.youtubeclient.core.ui.pagination.PagedResult
import com.samentic.youtubeclient.features.playlist.data.PlaylistItemEntity
import com.samentic.youtubeclient.features.playlist.data.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.min

class PlaylistItemsViewModel @Inject constructor(
    private val playlistsRepository: PlaylistsRepository,
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()

    // TODO: use savedState or assisted
    lateinit var playlistId: String
        private set

    val playlistItems = MutableLiveData<List<PlaylistItemView>>()

    // page data
    private var totalItems: Int = -1
    private var itemsPerPage: Int = 15//50
    private var nextPageToken: String? = null
    private var currentPage = 1

    private var job: Job? = null

    fun setPlaylistId(id: String) {
        if (this::playlistId.isInitialized && playlistId == id) return
        playlistId = id
        fetchPlaylistItems()
    }

    private fun fetchPlaylistItems(pageToken: String? = null) {
        if (!this::playlistId.isInitialized) return

        if (pageToken != null) {
            moreLoading.value = true
            loading.value = false
        } else {
            moreLoading.value = false
            loading.value = true
        }
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            val paged = getPage(pageToken)
            totalItems = paged.totalResults
            nextPageToken = paged.nextPageToken
            itemsPerPage = paged.resultsPerPage
            Log.d(
                "YouTubeTAG",
                "page:$currentPage,total:$totalItems,items:$itemsPerPage,token:$pageToken"
            )
            withContext(Dispatchers.Main) {
                playlistItems.value = buildList {
                    addAll(playlistItems.value.orEmpty())
                    addAll(paged.data.map { it.toPlaylistItemView() })
                }
                moreLoading.value = false
                loading.value = false
            }
        }
    }

    // Duplicate 2: refresh all pages. (whole paging process)
    fun refresh() {
        job?.cancel()
        loading.value = true
        moreLoading.value = false
        job = viewModelScope.launch(Dispatchers.IO) {
            var page = 1
            var token: String? = null
            var result: PagedResult<List<PlaylistItemEntity>>
            val items = mutableListOf<PlaylistItemView>()
            do {
                result = getPage(token)
                token = result.nextPageToken
                items.addAll(result.data.map { it.toPlaylistItemView() })
                Log.d("YouTubeTAG", "refresh: page:$page,token:$token")
            } while (++page <= currentPage || token == null)
            currentPage = min(1, page - 1)
            totalItems = result.totalResults
            nextPageToken = token
            itemsPerPage = result.resultsPerPage
            withContext(Dispatchers.Main) {
                loading.value = false
                moreLoading.value = false
                playlistItems.value = items
            }
        }
    }

    private suspend fun getPage(pageToken: String?): PagedResult<List<PlaylistItemEntity>> {
        return playlistsRepository.fetchPlaylistItems(playlistId, pageToken)
    }

    fun isLoadingItems(): Boolean {
        return loading.value == true || moreLoading.value == true
    }

    fun fetchNextPage() {
        if (isLoadingItems()) return

        if (totalItems != -1) { // there is nextPage
            if (currentPage * itemsPerPage < totalItems && nextPageToken != null) {
                currentPage++
                fetchPlaylistItems(nextPageToken)
            }
        }
    }
}