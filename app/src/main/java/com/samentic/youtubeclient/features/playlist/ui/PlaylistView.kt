package com.samentic.youtubeclient.features.playlist.ui

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.ThumbnailDetails
import com.samentic.youtubeclient.features.playlist.data.PlaylistEntity

data class PlaylistView(
    val id: String,
    val publishedAt: DateTime,
    val channelId: String,
    val title: String,
    val channelTitle: String,
    val description: String?,
    val thumbnail: ThumbnailDetails,
    val itemCount: Long,
    val playerUrl: String
)

fun PlaylistEntity.toPlaylistView() = PlaylistView(
    id = id,
    publishedAt = publishedAt,
    channelId = channelId,
    title = title,
    channelTitle = channelTitle,
    description = description,
    thumbnail = thumbnail,
    itemCount = itemCount,
    playerUrl = playerUrl
)