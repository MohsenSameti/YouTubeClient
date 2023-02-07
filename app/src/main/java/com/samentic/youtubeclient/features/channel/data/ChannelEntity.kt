package com.samentic.youtubeclient.features.channel.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.api.services.youtube.model.Channel
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailEntity
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailType
import java.math.BigInteger

@Entity
data class ChannelEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val publishedAt: String,
    @Embedded val relatedPlaylists: RelatedPlaylists,
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
    publishedAt = snippet.publishedAt.toStringRfc3339(),
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

fun Channel.toThumbnailEntity(): ThumbnailEntity {
    return ThumbnailEntity(
        ownerId = id,
        ownerType = ThumbnailType.Channel,
        defaultUrl = snippet?.thumbnails?.default?.url,
        highUrl = snippet?.thumbnails?.high?.url,
        mediumUrl = snippet?.thumbnails?.medium?.url,
        standardUrl = snippet?.thumbnails?.standard?.url,
        maxResolutionUrl = snippet?.thumbnails?.maxres?.url
    )
}