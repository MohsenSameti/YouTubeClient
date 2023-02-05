package com.samentic.youtubeclient.features.susbcription.data

import com.google.api.services.youtube.YouTube
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailLocalDataSource
import com.samentic.youtubeclient.core.ui.pagination.PagedResult
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import com.samentic.youtubeclient.features.channel.data.ChannelRepository
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val subscriptionLocalDataSource: SubscriptionLocalDataSource,
    private val thumbnailLocalDataSource: ThumbnailLocalDataSource,
    private val authRepository: AuthRepository,
    private val channelRepository: ChannelRepository,
    private val youtube: YouTube
) {

    suspend fun getSubscriptions(pageToken: String?): PagedResult<List<SubscriptionEntity>> {
        return authRepository.ensureAccessToken {
            val response = youtube.subscriptions()
                .list("contentDetails,id,snippet,subscriberSnippet".split(","))
                .setPageToken(pageToken)
                .setMine(true)
                .setMaxResults(30L)
                .execute()

            val subscriptionIds = response.items.map { it.snippet.resourceId.channelId }

            val channels = channelRepository.getChannelsById(subscriptionIds).associateBy { it.id }

            val subscriptions = response.items.map {
                val channel = channels[it.snippet.resourceId.channelId]
                it.toSubscriptionEntity(channel)
            }

            subscriptionLocalDataSource.insertSubscriptions(subscriptions)
            thumbnailLocalDataSource.insertThumbnails(response.items.map { it.toThumbnailEntity() })

            PagedResult<List<SubscriptionEntity>>(
                data = subscriptions,
                nextPageToken = response.nextPageToken,
                resultsPerPage = response.pageInfo.resultsPerPage,
                totalResults = response.pageInfo.totalResults
            )
        }
    }


}