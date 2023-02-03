package com.samentic.youtubeclient.features.susbcription.data

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.Subscription
import com.google.api.services.youtube.model.ThumbnailDetails
import com.samentic.youtubeclient.features.channel.data.ChannelEntity

data class SubscriptionEntity(
    val id: String,
    val title: String,
    val description: String,
    val resourceChannelId: String, // the channel of this subscription
    val publishedAt: DateTime,
    val thumbnails: ThumbnailDetails,
    val totalItemCount: Long,
    val newItemCount: Long,
    val uploadPlayList: String?
)

fun Subscription.toSubscriptionEntity(channel: ChannelEntity?) = SubscriptionEntity(
    id = id,
    title = snippet.title,
    description = snippet.description,
    resourceChannelId = snippet.resourceId.channelId,
    publishedAt = snippet.publishedAt,
    thumbnails = snippet.thumbnails,
    totalItemCount = contentDetails.totalItemCount,
    newItemCount = contentDetails.newItemCount,
    uploadPlayList = channel?.relatedPlaylists?.uploads
)