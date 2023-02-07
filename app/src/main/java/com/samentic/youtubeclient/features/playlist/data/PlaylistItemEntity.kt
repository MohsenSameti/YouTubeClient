package com.samentic.youtubeclient.features.playlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.api.services.youtube.model.PlaylistItem
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType

@Entity
data class PlaylistItemEntity(
    @PrimaryKey
    val id: String,
    val description: String?,
    val position: Long,
    val playlistId: String,
    val title: String,
    val publishedAt: String,
    val channelTitle: String,
    val channelId: String,
    val videoOwnerChannelTitle: String,
    val videoOwnerChannelId: String
) {
    var softDeleteTime: Long? = null
}

fun PlaylistItem.toPlaylistItemEntity() = PlaylistItemEntity(
    id = contentDetails.videoId,
    description = snippet.description,
    position = snippet.position,
    playlistId = snippet.playlistId,
    title = snippet.title,
    publishedAt = snippet.publishedAt.toStringRfc3339(),
    channelTitle = snippet.channelTitle,
    channelId = snippet.channelId,
    videoOwnerChannelTitle = snippet.videoOwnerChannelTitle,
    videoOwnerChannelId = snippet.videoOwnerChannelId
)

fun PlaylistItem.toThumbnailEntity(): ThumbnailEntity {
    return ThumbnailEntity(
        ownerId = contentDetails.videoId,
        ownerType = ThumbnailType.PlaylistItem,
        defaultUrl = snippet?.thumbnails?.default?.url,
        highUrl = snippet?.thumbnails?.high?.url,
        mediumUrl = snippet?.thumbnails?.medium?.url,
        standardUrl = snippet?.thumbnails?.standard?.url,
        maxResolutionUrl = snippet?.thumbnails?.maxres?.url
    )
}