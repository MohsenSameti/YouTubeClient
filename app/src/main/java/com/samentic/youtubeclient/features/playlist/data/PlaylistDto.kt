package com.samentic.youtubeclient.features.playlist.data

import androidx.room.Embedded
import androidx.room.Relation
import com.google.api.services.youtube.model.Playlist
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType

data class PlaylistDto(
    @Embedded
    val playlist: PlaylistEntity,
    @Relation(parentColumn = "id", entityColumn = "ownerId", entity = ThumbnailEntity::class)
    val thumbnails: List<ThumbnailEntity>
) {
    val thumbnail: ThumbnailEntity?
        get() = thumbnails.firstOrNull { it.ownerType == ThumbnailType.Playlist }
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