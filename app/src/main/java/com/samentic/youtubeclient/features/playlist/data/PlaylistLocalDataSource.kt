package com.samentic.youtubeclient.features.playlist.data

import javax.inject.Inject

class PlaylistLocalDataSource @Inject constructor(
    private val dao: PlaylistDao
) {

    suspend fun insertPlaylists(playlists: List<PlaylistEntity>) {
        dao.insertPlaylists(playlists)
    }
}