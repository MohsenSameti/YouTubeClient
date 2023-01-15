package com.samentic.youtubeclient.features.playlist.data

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.PlaylistItem
import com.google.api.services.youtube.model.ThumbnailDetails

data class PlaylistItemEntity(
    val id: String,
    val description: String?,
    val position: Long,
    val playlistId: String,
    val title: String,
    val thumbnails: ThumbnailDetails,
    val publishedAt: DateTime,
    val channelTitle: String,
    val channelId: String,
    val videoOwnerChannelTitle: String,
    val videoOwnerChannelId: String
)

fun PlaylistItem.toPlaylistItemEntity() = PlaylistItemEntity(
    id = contentDetails.videoId,
    description = snippet.description,
    position = snippet.position,
    playlistId = snippet.playlistId,
    title = snippet.title,
    thumbnails = snippet.thumbnails,
    publishedAt = snippet.publishedAt,
    channelTitle = snippet.channelTitle,
    channelId = snippet.channelId,
    videoOwnerChannelTitle = snippet.videoOwnerChannelTitle,
    videoOwnerChannelId = snippet.videoOwnerChannelId
)