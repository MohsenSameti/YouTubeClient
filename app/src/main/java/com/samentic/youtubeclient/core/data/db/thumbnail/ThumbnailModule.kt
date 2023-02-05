package com.samentic.youtubeclient.core.data.db.thumbnail

import com.samentic.youtubeclient.core.data.db.YoutubeDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ThumbnailModule {

    @Singleton
    @Provides
    fun provideThumbnailDao(db: YoutubeDb): ThumbnailDao {
        return db.thumbnailDao()
    }
}