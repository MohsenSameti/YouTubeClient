package com.samentic.youtubeclient.core.data.db.thumbnail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ThumbnailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThumbnails(thumbnails: List<ThumbnailEntity>)
}