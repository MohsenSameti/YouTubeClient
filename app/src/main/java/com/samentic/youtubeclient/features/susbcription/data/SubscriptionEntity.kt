package com.samentic.youtubeclient.features.susbcription.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.api.services.youtube.model.Subscription
import com.google.api.services.youtube.model.ThumbnailDetails
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType
import com.samentic.youtubeclient.features.channel.data.ChannelEntity

@Entity
data class SubscriptionEntity @JvmOverloads constructor(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val resourceChannelId: String, // the channel of this subscription
    val publishedAt: String,
    @Ignore
    val thumbnails: ThumbnailDetails? = null,
    val totalItemCount: Long,
    val newItemCount: Long,
    val uploadPlayList: String?
)

fun Subscription.toSubscriptionEntity(channel: ChannelEntity?) = SubscriptionEntity(
    id = id,
    title = snippet.title,
    description = snippet.description,
    resourceChannelId = snippet.resourceId.channelId,
    publishedAt = snippet.publishedAt.toStringRfc3339(),
    thumbnails = snippet.thumbnails,
    totalItemCount = contentDetails.totalItemCount,
    newItemCount = contentDetails.newItemCount,
    uploadPlayList = channel?.relatedPlaylists?.uploads
)

fun Subscription.toThumbnailEntity(): ThumbnailEntity {
    return ThumbnailEntity(
        ownerId = id,
        ownerType = ThumbnailType.Subscription,
        defaultUrl = snippet?.thumbnails?.default?.url,
        highUrl = snippet?.thumbnails?.high?.url,
        mediumUrl = snippet?.thumbnails?.medium?.url,
        standardUrl = snippet?.thumbnails?.standard?.url,
        maxResolutionUrl = snippet?.thumbnails?.maxres?.url
    )
}