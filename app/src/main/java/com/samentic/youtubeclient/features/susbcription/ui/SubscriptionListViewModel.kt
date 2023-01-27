package com.samentic.youtubeclient.features.susbcription.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samentic.youtubeclient.features.susbcription.data.SubscriptionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubscriptionListViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    fun fetchSubscriptions() {
        viewModelScope.launch(Dispatchers.IO) {
            subscriptionRepository.getSubscriptions()
        }

    }
}