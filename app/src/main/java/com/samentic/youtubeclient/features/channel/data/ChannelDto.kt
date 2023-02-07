package com.samentic.youtubeclient.features.channel.data

import androidx.room.Embedded
import androidx.room.Relation
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType

data class ChannelDto(
    @Embedded
    val channel: ChannelEntity,
    @Relation(parentColumn = "id", entityColumn = "ownerId", entity = ThumbnailEntity::class)
    val thumbnails: List<ThumbnailEntity>
) {
    val thumbnail: ThumbnailEntity?
        get() = thumbnails.firstOrNull { it.ownerType == ThumbnailType.Channel }
}
