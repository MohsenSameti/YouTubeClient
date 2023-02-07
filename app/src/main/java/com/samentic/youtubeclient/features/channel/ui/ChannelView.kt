package com.samentic.youtubeclient.features.channel.ui

import com.google.api.client.util.DateTime
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.features.channel.data.ChannelDto
import com.samentic.youtubeclient.features.channel.data.ChannelEntity
import java.math.BigInteger

data class ChannelView(
    val id: String,
    val title: String,
    val description: String?,
    val publishedAt: DateTime,
    val thumbnail: ThumbnailEntity?, // TODO: add error placeholder if it is null!
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

fun ChannelDto.toChannelView() = ChannelView(
    id = channel.id,
    title = channel.title,
    description = channel.description,
    publishedAt = DateTime(channel.publishedAt),
    thumbnail = thumbnail,
    relatedPlaylists = channel.relatedPlaylists.toChannelViewPlaylists(),
    viewCount = channel.viewCount,
    subscriberCount = channel.subscriberCount,
    hiddenSubscriberCount = channel.hiddenSubscriberCount,
    videoCount = channel.videoCount
)

fun ChannelEntity.RelatedPlaylists.toChannelViewPlaylists(): ChannelView.RelatedPlaylists {
    return ChannelView.RelatedPlaylists(
        uploads = uploads,
        likes = likes,
        watchHistory = watchHistory
    )
}