package com.samentic.youtubeclient.features.playlist.data

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.Playlist
import com.google.api.services.youtube.model.ThumbnailDetails
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType

data class PlaylistDto(
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
    publishedAt = snippet.publishedAt.toStringRfc3339(),
    channelId = snippet.channelId,
    title = snippet.title,
    channelTitle = snippet.channelTitle,
    description = snippet.description,
    itemCount = contentDetails.itemCount,
    playerUrl = player.embedHtml
)

fun Playlist.toThumbnailEntity(): ThumbnailEntity {
    return ThumbnailEntity(
        ownerId = id,
        ownerType = ThumbnailType.Playlist,
        defaultUrl = snippet?.thumbnails?.default?.url,
        highUrl = snippet?.thumbnails?.high?.url,
        mediumUrl = snippet?.thumbnails?.medium?.url,
        standardUrl = snippet?.thumbnails?.standard?.url,
        maxResolutionUrl = snippet?.thumbnails?.maxres?.url
    )
}