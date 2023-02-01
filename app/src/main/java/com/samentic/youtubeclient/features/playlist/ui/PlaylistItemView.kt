package com.samentic.youtubeclient.features.playlist.ui

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.ThumbnailDetails
import com.samentic.youtubeclient.features.playlist.data.PlaylistItemEntity

data class PlaylistItemView(
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
) : PlaylistDetailAdapterItem

fun PlaylistItemEntity.toPlaylistItemView() = PlaylistItemView(
    id = id,
    description = description,
    position = position,
    playlistId = playlistId,
    title = title,
    thumbnails = thumbnails,
    publishedAt = publishedAt,
    channelTitle = channelTitle,
    channelId = channelId,
    videoOwnerChannelTitle = videoOwnerChannelTitle,
    videoOwnerChannelId = videoOwnerChannelId
)