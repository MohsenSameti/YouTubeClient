package com.samentic.youtubeclient.features.susbcription.data

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.samentic.youtubeclient.core.ui.pagination.PagedResult
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import com.samentic.youtubeclient.features.playlist.data.AuthorizationHeaderAccessMethod
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val authRepository: AuthRepository
) {

    // TODO: inject in dagger
    // Duplicate 2: create youtube and credential
    private val credential: Credential by lazy {
        Credential.Builder(AuthorizationHeaderAccessMethod())
            .build()
    }

    private val youtube: YouTube by lazy {
        YouTube.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("Samentic Test")
            .build()
    }

    suspend fun getSubscriptions(pageToken: String?): PagedResult<List<SubscriptionEntity>> {
        return authRepository.ensureAccessToken { accessToken ->
            if (credential.accessToken != accessToken)
                credential.accessToken = accessToken

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