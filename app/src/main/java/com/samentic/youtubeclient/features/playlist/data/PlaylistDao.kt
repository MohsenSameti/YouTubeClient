package com.samentic.youtubeclient.features.playlist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM PlaylistEntity WHERE softDeleteTime IS NULL LIMIT :limit")
    fun getPlaylists(limit: Long): LiveData<List<PlaylistDto>>

    @Query(
        """
        SELECT * FROM PlaylistItemEntity 
        WHERE playlistId = :playlistId AND softDeleteTime IS NULL
        LIMIT :limit
    """
    )
    fun getPlaylistItems(playlistId: String, limit: Long): LiveData<List<PlaylistItemDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: List<PlaylistEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistItems(items: List<PlaylistItemEntity>)

    @Query("UPDATE PlaylistEntity SET softDeleteTime = :time")
    suspend fun softDeleteAllPlaylists(time: Long)

    @Query("UPDATE PlaylistItemEntity SET softDeleteTime = :time WHERE playlistId = :playlistId")
    suspend fun softDeleteAllPlaylistItems(playlistId: String, time: Long)
}