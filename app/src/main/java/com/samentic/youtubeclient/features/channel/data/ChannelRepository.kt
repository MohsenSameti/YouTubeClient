package com.samentic.youtubeclient.features.channel.data

import com.google.api.services.youtube.YouTube
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailLocalDataSource
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import javax.inject.Inject

class ChannelRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val youtube: YouTube,
    private val channelLocalDataSource: ChannelLocalDataSource,
    private val thumbnailLocalDataSource: ThumbnailLocalDataSource
) {

    fun getChannel(id: String) = channelLocalDataSource.getChannel(id)

    suspend fun getChannelById(channelId: String): ChannelEntity {
        return getChannelsById(listOf(channelId))[0]
    }

    suspend fun getChannelsById(ids: List<String>): List<ChannelEntity> {
        return authRepository.ensureAccessToken {
            val response = youtube.channels()
                .list("id,snippet,statistics,contentDetails".split(","))
                .setMaxResults(50L)
                .setId(ids)
                .execute()

            val channels = response.items.map { it.toChannelEntity() }
            channelLocalDataSource.insertChannels(channels)
            thumbnailLocalDataSource.insertThumbnails(
                response.items.map { it.toThumbnailEntity() }
            )
            channels
        }
    }
}