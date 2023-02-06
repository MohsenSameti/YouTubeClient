package com.samentic.youtubeclient.features.susbcription.data

import androidx.room.Embedded
import androidx.room.Relation
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType

data class SubscriptionDto(
    @Embedded
    val subscription: SubscriptionEntity,
    @Relation(parentColumn = "id", entityColumn = "ownerId", entity = ThumbnailEntity::class)
    private val thumbnails: List<ThumbnailEntity>
) {
    val thumbnail: ThumbnailEntity?
        get() = thumbnails.firstOrNull { it.ownerType == ThumbnailType.Subscription }
}