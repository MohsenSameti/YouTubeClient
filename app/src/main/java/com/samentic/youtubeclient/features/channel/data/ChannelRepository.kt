package com.samentic.youtubeclient.features.channel.data

import com.google.api.services.youtube.YouTube
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import javax.inject.Inject

class ChannelRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val youtube: YouTube
) {

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

            response.items.map { it.toChannelEntity() }
        }
    }
}