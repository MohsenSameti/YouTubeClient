package com.samentic.youtubeclient.features.channel.data

import com.samentic.youtubeclient.core.data.db.YoutubeDb
import dagger.Module
import dagger.Provides

@Module
object ChannelModule {

    @Provides
    fun provideChannelDao(db: YoutubeDb): ChannelDao {
        return db.channelDao()
    }
}