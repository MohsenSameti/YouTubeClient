package com.samentic.youtubeclient.features.playlist.data

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.Playlist
import com.google.api.services.youtube.model.ThumbnailDetails

data class PlaylistEntity(
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

fun Playlist.toPlaylistEntity() = PlaylistEntity(
    id = id,
    publishedAt = snippet.publishedAt,
    channelId = snippet.channelId,
    title = snippet.title,
    channelTitle = snippet.channelTitle,
    description = snippet.description,
    thumbnail = snippet.thumbnails,
    itemCount = contentDetails.itemCount,
    playerUrl = player.embedHtml
)