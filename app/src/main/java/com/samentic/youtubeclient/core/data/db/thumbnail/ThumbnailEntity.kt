package com.samentic.youtubeclient.core.data.db.thumbnail

import androidx.room.Entity

@Entity(primaryKeys = ["ownerId", "ownerType"])
data class ThumbnailEntity(
    val ownerId: String,
    val ownerType: ThumbnailType,
    val defaultUrl: String?,
    val highUrl: String?,
    val mediumUrl: String?,
    val standardUrl: String?,
    val maxResolutionUrl: String?
)