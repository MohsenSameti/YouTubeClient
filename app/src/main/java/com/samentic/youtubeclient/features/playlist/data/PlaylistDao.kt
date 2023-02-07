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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: List<PlaylistEntity>)

    @Query("UPDATE PlaylistEntity SET softDeleteTime = :time")
    suspend fun softDeleteAll(time: Long)
}