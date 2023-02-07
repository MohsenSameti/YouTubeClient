package com.samentic.youtubeclient.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailDao
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
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
        PlaylistItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class YoutubeDb : RoomDatabase() {

    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun thumbnailDao(): ThumbnailDao
    abstract fun playlistDao(): PlaylistDao
}