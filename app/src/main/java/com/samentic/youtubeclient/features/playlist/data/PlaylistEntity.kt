package com.samentic.youtubeclient.features.playlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.api.services.youtube.model.Playlist
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType

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