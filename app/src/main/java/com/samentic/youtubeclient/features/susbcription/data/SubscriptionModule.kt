package com.samentic.youtubeclient.features.susbcription.data

import com.samentic.youtubeclient.core.data.db.YoutubeDb
import dagger.Module
import dagger.Provides

@Module
object SubscriptionModule {

    @Provides
    fun provideSubscriptionDao(db: YoutubeDb): SubscriptionDao {
        return db.subscriptionDao()
    }
}