package com.samentic.youtubeclient.features.playlist.ui

import com.google.api.client.util.DateTime
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.features.playlist.data.PlaylistDto

data class PlaylistView(
    val id: String,
    val publishedAt: DateTime,
    val channelId: String,
    val title: String,
    val channelTitle: String,
    val description: String?,
    val thumbnail: ThumbnailEntity?, // TODO: add error placeholder if it is null!
    val itemCount: Long,
    val playerUrl: String
)

fun PlaylistDto.toPlaylistView() = PlaylistView(
    id = playlist.id,
    publishedAt = DateTime.parseRfc3339(playlist.publishedAt),
    channelId = playlist.channelId,
    title = playlist.title,
    channelTitle = playlist.channelTitle,
    description = playlist.description,
    thumbnail = thumbnail,
    itemCount = playlist.itemCount,
    playerUrl = playlist.playerUrl
)