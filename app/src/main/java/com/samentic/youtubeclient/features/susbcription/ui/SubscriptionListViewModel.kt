package com.samentic.youtubeclient.features.susbcription.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samentic.youtubeclient.core.ui.pagination.PagedResult
import com.samentic.youtubeclient.features.susbcription.data.SubscriptionEntity
import com.samentic.youtubeclient.features.susbcription.data.SubscriptionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.min

class SubscriptionListViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()

    val subscriptions = MutableLiveData<List<SubscriptionView>>()

    private var totalItems: Int = -1
    // TODO: send this to api: do the same in playlist as well
    private var itemsPerPage: Int = 30//50
    private var nextPageToken: String? = null
    var currentPage = 1
        private set

    private var job: Job? = null

    init {
        fetchSubscriptions(null)
    }

    private fun fetchSubscriptions(pageToken: String?) {
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
            nextPageToken = paged.nextPageToken
            itemsPerPage = paged.resultsPerPage
            totalItems = paged.totalResults
            withContext(Dispatchers.Main) {
                subscriptions.value = buildList {
                    addAll(subscriptions.value.orEmpty())
                    addAll(paged.data.map { it.toSubscriptionView() })
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
            var result: PagedResult<List<SubscriptionEntity>>
            val items = mutableListOf<SubscriptionView>()
            do {
                result = getPage(token)
                token = result.nextPageToken
                items.addAll(result.data.map { it.toSubscriptionView() })
            } while (++page <= currentPage || token == null)
            currentPage = min(1, page - 1)
            totalItems = result.totalResults
            nextPageToken = token
            itemsPerPage = result.resultsPerPage

            withContext(Dispatchers.Main) {
                loading.value = false
                moreLoading.value = false
                subscriptions.value = items
            }
        }
    }

    private suspend fun getPage(pageToken: String?): PagedResult<List<SubscriptionEntity>> {
        return subscriptionRepository.getSubscriptions(pageToken)
    }

    fun isLoadingItems(): Boolean {
        return loading.value == true || moreLoading.value == true
    }

    fun fetchNextPage() {
        if (isLoadingItems()) return

        if (totalItems != -1) { // there is nextPage
            if (currentPage * itemsPerPage < totalItems && nextPageToken != null) {
                currentPage++
                fetchSubscriptions(nextPageToken)
            }
        }
    }
}