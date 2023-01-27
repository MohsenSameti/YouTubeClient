package com.samentic.youtubeclient.features.susbcription.data

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.Subscription
import com.google.api.services.youtube.model.ThumbnailDetails

data class SubscriptionEntity(
    val id: String,
    val title: String,
    val description: String,
    val resourceChannelId: String, // the channel of this subscription
    val publishedAt: DateTime,
    val thumbnails: ThumbnailDetails,
    val totalItemCount: Long,
    val newItemCount: Long
)

fun Subscription.toSubscriptionEntity() = SubscriptionEntity(
    id = id,
    title = snippet.title,
    description = snippet.description,
    resourceChannelId = snippet.resourceId.channelId,
    publishedAt = snippet.publishedAt,
    thumbnails = snippet.thumbnails,
    totalItemCount = contentDetails.totalItemCount,
    newItemCount = contentDetails.newItemCount
)