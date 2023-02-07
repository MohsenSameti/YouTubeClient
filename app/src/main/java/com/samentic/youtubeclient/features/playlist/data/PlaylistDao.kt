package com.samentic.youtubeclient.features.playlist.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface PlaylistDao {
    @Insert
    suspend fun insertPlaylists(playlists: List<PlaylistEntity>)
}