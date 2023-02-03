package com.samentic.youtubeclient.features.channel.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samentic.youtubeclient.features.channel.data.ChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChannelDetailViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
) : ViewModel() {

    private lateinit var channelId: String

    val loading = MutableLiveData<Boolean>()
    val detail = MutableLiveData<ChannelView>()

    init {
        fetchChannelDetail()
    }

    fun fetchChannelDetail() {
        if (!this::channelId.isInitialized) return

        loading.value = false
        viewModelScope.launch(Dispatchers.IO) {
            val detail = channelRepository.getChannelById(channelId).toChannelView()
            withContext(Dispatchers.Main) {
                this@ChannelDetailViewModel.detail.value = detail
                loading.value = false
            }
        }
    }

    fun setChannelId(channelId: String) {
        if (!this::channelId.isInitialized || this.channelId != channelId) {
            this.channelId = channelId
            fetchChannelDetail()
        }
    }
}