package com.samentic.youtubeclient.features.channel.ui

import androidx.lifecycle.*
import com.samentic.youtubeclient.features.channel.data.ChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChannelDetailViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
) : ViewModel() {

    private val channelId = MutableLiveData<String>()

    val loading = MutableLiveData<Boolean>()
    val detail = channelId.switchMap { id ->
        channelRepository.getChannel(id).map { it?.toChannelView() }
    }

    init {
        fetchChannelDetail()
    }

    fun fetchChannelDetail() {
        val channelId = this.channelId.value ?: return
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            channelRepository.getChannelById(channelId)
            withContext(Dispatchers.Main) {
                loading.value = false
            }
        }
    }

    fun setChannelId(channelId: String) {
        if (this.channelId.value != channelId) {
            this.channelId.value = channelId
            fetchChannelDetail()
        }
    }
}