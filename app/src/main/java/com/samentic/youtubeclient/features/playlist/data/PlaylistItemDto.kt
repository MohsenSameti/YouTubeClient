package com.samentic.youtubeclient.features.playlist.data

import androidx.room.Embedded
import androidx.room.Relation
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType

data class PlaylistItemDto(
    @Embedded
    val item: PlaylistItemEntity,
    @Relation(parentColumn = "id", entityColumn = "ownerId", entity = ThumbnailEntity::class)
    val thumbnails: List<ThumbnailEntity>
) {
    val thumbnail: ThumbnailEntity?
        get() = thumbnails.firstOrNull { it.ownerType == ThumbnailType.PlaylistItem }
}