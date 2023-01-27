package com.samentic.youtubeclient.features.susbcription.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samentic.youtubeclient.features.susbcription.data.SubscriptionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubscriptionListViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val subscriptions = MutableLiveData<List<SubscriptionView>>()

    init {
        fetchSubscriptions()
    }

    fun fetchSubscriptions() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val paged = subscriptionRepository.getSubscriptions()
            Log.d("SubscriptionTAG", paged.data.joinToString("\n") { "${it.title} -> ${it.resourceChannelId}" })
            subscriptions.postValue(paged.data.map { it.toSubscriptionView() })
            loading.postValue(false)
        }
    }

    fun refresh() {
        fetchSubscriptions()
    }
}