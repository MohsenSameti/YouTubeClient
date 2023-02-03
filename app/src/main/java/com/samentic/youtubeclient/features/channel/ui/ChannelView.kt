package com.samentic.youtubeclient.features.channel.ui

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.ThumbnailDetails
import com.samentic.youtubeclient.features.channel.data.ChannelEntity
import java.math.BigInteger

data class ChannelView(
    val id: String,
    val title: String,
    val description: String?,
    val publishedAt: DateTime,
    val thumbnails: ThumbnailDetails,
    val relatedPlaylists: RelatedPlaylists,
    val viewCount: BigInteger,
    val subscriberCount: BigInteger,
    val hiddenSubscriberCount: Boolean,
    val videoCount: BigInteger
) {
    data class RelatedPlaylists(
        val uploads: String,
        val likes: String?,
        val watchHistory: String?
    )
}

fun ChannelEntity.toChannelView() = ChannelView(
    id = id,
    title = title,
    description = description,
    publishedAt = publishedAt,
    thumbnails = thumbnails,
    relatedPlaylists = relatedPlaylists.toChannelViewPlaylists(),
    viewCount = viewCount,
    subscriberCount = subscriberCount,
    hiddenSubscriberCount = hiddenSubscriberCount,
    videoCount = videoCount
)

fun ChannelEntity.RelatedPlaylists.toChannelViewPlaylists(): ChannelView.RelatedPlaylists {
    return ChannelView.RelatedPlaylists(
        uploads = uploads,
        likes = likes,
        watchHistory = watchHistory
    )
}