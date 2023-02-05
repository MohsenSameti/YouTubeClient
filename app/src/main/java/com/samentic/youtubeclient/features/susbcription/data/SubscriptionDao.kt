package com.samentic.youtubeclient.features.susbcription.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface SubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscriptions: List<SubscriptionEntity>)
}