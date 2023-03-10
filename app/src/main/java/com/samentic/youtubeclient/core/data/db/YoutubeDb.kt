package com.samentic.youtubeclient.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailDao
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.features.channel.data.ChannelDao
import com.samentic.youtubeclient.features.channel.data.ChannelEntity
import com.samentic.youtubeclient.features.playlist.data.PlaylistDao
import com.samentic.youtubeclient.features.playlist.data.PlaylistEntity
import com.samentic.youtubeclient.features.playlist.data.PlaylistItemEntity
import com.samentic.youtubeclient.features.susbcription.data.SubscriptionDao
import com.samentic.youtubeclient.features.susbcription.data.SubscriptionEntity

@Database(
    entities = [
        SubscriptionEntity::class,
        ThumbnailEntity::class,
        PlaylistEntity::class,
        PlaylistItemEntity::class,
        ChannelEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(BigIntegerConverter::class)
abstract class YoutubeDb : RoomDatabase() {

    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun thumbnailDao(): ThumbnailDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun channelDao(): ChannelDao
}