package com.samentic.youtubeclient.features.playlist.data

import com.samentic.youtubeclient.core.data.db.YoutubeDb
import dagger.Module
import dagger.Provides

@Module
object PlaylistModule {

    @Provides
    fun providePlaylistDao(db: YoutubeDb): PlaylistDao {
        return db.playlistDao()
    }
}