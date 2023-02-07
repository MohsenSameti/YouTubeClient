package com.samentic.youtubeclient.features.channel.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChannelDao {

    @Query("SELECT * FROM ChannelEntity WHERE id = :id")
    fun getChannel(id: String): LiveData<ChannelDto?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<ChannelEntity>)
}