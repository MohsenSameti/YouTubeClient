package com.samentic.youtubeclient.features.channel.data

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.Channel
import com.google.api.services.youtube.model.ThumbnailDetails
import java.math.BigInteger

data class ChannelEntity(
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

fun Channel.toChannelEntity() = ChannelEntity(
    id = id,
    title = snippet.title,
    description = snippet.description,
    publishedAt = snippet.publishedAt,
    thumbnails = snippet.thumbnails,
    relatedPlaylists = ChannelEntity.RelatedPlaylists(
        uploads = contentDetails.relatedPlaylists.uploads,
        likes = contentDetails.relatedPlaylists.likes,
        watchHistory = contentDetails.relatedPlaylists.watchHistory
    ),
    viewCount = statistics.viewCount,
    subscriberCount = statistics.subscriberCount,
    hiddenSubscriberCount = statistics.hiddenSubscriberCount,
    videoCount = statistics.videoCount

)