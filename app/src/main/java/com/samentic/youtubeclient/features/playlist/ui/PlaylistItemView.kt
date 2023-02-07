package com.samentic.youtubeclient.features.playlist.ui

import com.google.api.client.util.DateTime
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.features.playlist.data.PlaylistItemDto

data class PlaylistItemView(
    val id: String,
    val description: String?,
    val position: Long,
    val playlistId: String,
    val title: String,
    val thumbnails: ThumbnailEntity?, // TODO: add error placeholder if it is null!
    val publishedAt: DateTime,
    val channelTitle: String,
    val channelId: String,
    val videoOwnerChannelTitle: String,
    val videoOwnerChannelId: String
) : PlaylistDetailAdapterItem

fun PlaylistItemDto.toPlaylistItemView() = PlaylistItemView(
    id = item.id,
    description = item.description,
    position = item.position,
    playlistId = item.playlistId,
    title = item.title,
    thumbnails = thumbnail,
    publishedAt = DateTime.parseRfc3339(item.publishedAt),
    channelTitle = item.channelTitle,
    channelId = item.channelId,
    videoOwnerChannelTitle = item.videoOwnerChannelTitle,
    videoOwnerChannelId = item.videoOwnerChannelId
)