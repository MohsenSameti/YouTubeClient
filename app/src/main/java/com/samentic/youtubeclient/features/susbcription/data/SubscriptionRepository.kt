package com.samentic.youtubeclient.features.susbcription.data

import com.google.api.services.youtube.YouTube
import com.samentic.youtubeclient.core.ui.pagination.PagedResult
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val authRepository: AuthRepository,
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

            PagedResult<List<SubscriptionEntity>>(
                data = response.items.map { it.toSubscriptionEntity() },
                nextPageToken = response.nextPageToken,
                resultsPerPage = response.pageInfo.resultsPerPage,
                totalResults = response.pageInfo.totalResults
            )
        }
    }


}