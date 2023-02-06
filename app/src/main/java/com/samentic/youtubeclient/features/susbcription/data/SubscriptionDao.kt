package com.samentic.youtubeclient.features.susbcription.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM SubscriptionEntity WHERE softDeleteTime is NULL LIMIT :limit")
    fun getSubscriptions(limit: Long): LiveData<List<SubscriptionDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscriptions: List<SubscriptionEntity>)

    @Query("UPDATE SubscriptionEntity SET softDeleteTime = :time")
    suspend fun softDeleteAll(time: Long)
}