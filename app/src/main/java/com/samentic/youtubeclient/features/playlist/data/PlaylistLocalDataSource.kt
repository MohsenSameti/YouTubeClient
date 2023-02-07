package com.samentic.youtubeclient.features.playlist.data

import androidx.room.withTransaction
import com.samentic.youtubeclient.core.data.db.YoutubeDb
import javax.inject.Inject

class PlaylistLocalDataSource @Inject constructor(
    private val db: YoutubeDb,
    private val dao: PlaylistDao
) {

    fun getPlaylists(limit: Long) = dao.getPlaylists(limit)

    suspend fun insertPlaylists(playlists: List<PlaylistEntity>, isFirstPage: Boolean) {
        db.withTransaction {
            if(isFirstPage) {
                dao.softDeleteAll(System.currentTimeMillis())
            }
            dao.insertPlaylists(playlists)
        }
    }
}