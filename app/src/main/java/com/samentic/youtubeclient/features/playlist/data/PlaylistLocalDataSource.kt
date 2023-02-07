package com.samentic.youtubeclient.features.playlist.data

import androidx.room.withTransaction
import com.samentic.youtubeclient.core.data.db.YoutubeDb
import javax.inject.Inject

class PlaylistLocalDataSource @Inject constructor(
    private val db: YoutubeDb,
    private val dao: PlaylistDao
) {

    fun getPlaylists(limit: Long) = dao.getPlaylists(limit)

    fun getPlaylistItems(playlistId: String, limit: Long) = dao.getPlaylistItems(playlistId, limit)

    suspend fun insertPlaylists(playlists: List<PlaylistEntity>, isFirstPage: Boolean) {
        db.withTransaction {
            if (isFirstPage) {
                dao.softDeleteAllPlaylists(System.currentTimeMillis())
            }
            dao.insertPlaylists(playlists)
        }
    }

    suspend fun insertPlaylistItems(
        playlistId: String,
        items: List<PlaylistItemEntity>,
        isFirstPage: Boolean
    ) {
        db.withTransaction {
            if (isFirstPage) {
                dao.softDeleteAllPlaylistItems(playlistId, System.currentTimeMillis())
            }
            dao.insertPlaylistItems(items)
        }
    }
}