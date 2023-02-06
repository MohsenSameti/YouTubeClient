package com.samentic.youtubeclient.features.susbcription.data

import androidx.room.withTransaction
import com.samentic.youtubeclient.core.data.db.YoutubeDb
import javax.inject.Inject

class SubscriptionLocalDataSource @Inject constructor(
    private val db: YoutubeDb,
    private val dao: SubscriptionDao
) {

    fun getSubscriptions(limit: Long) = dao.getSubscriptions(limit)

    suspend fun insertSubscriptions(
        subscriptions: List<SubscriptionEntity>,
        isFirstPage: Boolean
    ) {
        db.withTransaction {
            if (isFirstPage) {
                dao.softDeleteAll(System.currentTimeMillis())
            }
            dao.insertSubscription(subscriptions)
        }
    }
}