package com.samentic.youtubeclient.features.susbcription.ui

import com.google.api.client.util.DateTime
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.features.susbcription.data.SubscriptionDto

data class SubscriptionView(
    val id: String,
    val title: String,
    val description: String,
    val resourceChannelId: String, // the channel of this subscription
    val publishedAt: DateTime,
    val thumbnails: ThumbnailEntity?, // TODO: add error placeholder if it is null!
    val totalItemCount: Long,
    val newItemCount: Long,
    val uploadPlayList: String?
) : SubscriptionAdapterView

fun SubscriptionDto.toSubscriptionView() = SubscriptionView(
    id = subscription.id,
    title = subscription.title,
    description = subscription.description,
    resourceChannelId = subscription.resourceChannelId,
    publishedAt = DateTime.parseRfc3339(subscription.publishedAt),
    thumbnails = thumbnail,
    totalItemCount = subscription.totalItemCount,
    newItemCount = subscription.newItemCount,
    uploadPlayList = subscription.uploadPlayList
)