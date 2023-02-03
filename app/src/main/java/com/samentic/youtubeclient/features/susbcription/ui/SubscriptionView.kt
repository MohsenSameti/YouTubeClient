package com.samentic.youtubeclient.features.susbcription.ui

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.ThumbnailDetails
import com.samentic.youtubeclient.features.susbcription.data.SubscriptionEntity

data class SubscriptionView(
    val id: String,
    val title: String,
    val description: String,
    val resourceChannelId: String, // the channel of this subscription
    val publishedAt: DateTime,
    val thumbnails: ThumbnailDetails,
    val totalItemCount: Long,
    val newItemCount: Long,
    val uploadPlayList: String?
) : SubscriptionAdapterView

fun SubscriptionEntity.toSubscriptionView() = SubscriptionView(
    id = id,
    title = title,
    description = description,
    resourceChannelId = resourceChannelId,
    publishedAt = publishedAt,
    thumbnails = thumbnails,
    totalItemCount = totalItemCount,
    newItemCount = newItemCount,
    uploadPlayList = uploadPlayList
)