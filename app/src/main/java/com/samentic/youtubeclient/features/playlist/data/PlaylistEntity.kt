package com.samentic.youtubeclient.features.playlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaylistEntity(
    @PrimaryKey
    val id: String,
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val channelTitle: String,
    val description: String?,
    val itemCount: Long,
    val playerUrl: String
) {
    var softDeleteTime: Long? = null
}
