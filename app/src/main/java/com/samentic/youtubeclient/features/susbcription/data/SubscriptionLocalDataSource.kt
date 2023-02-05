package com.samentic.youtubeclient.features.susbcription.data

import javax.inject.Inject

class SubscriptionLocalDataSource @Inject constructor(
    private val dao: SubscriptionDao
) {

    suspend fun insertSubscriptions(subscriptions: List<SubscriptionEntity>) {
        dao.insertSubscription(subscriptions)
    }
}