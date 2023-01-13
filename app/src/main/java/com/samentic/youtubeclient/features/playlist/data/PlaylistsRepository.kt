package com.samentic.youtubeclient.features.playlist.data

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistListResponse
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import javax.inject.Inject

class PlaylistsRepository @Inject constructor(
    private val authRepository: AuthRepository
) {

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

    // todo: rename to myPlayLists
    // todo: add paging
    suspend fun fetchPlayLists(): List<PlaylistEntity> {
        return authRepository.ensureAccessToken { accessToken ->
            if (credential.accessToken != accessToken)
                credential.accessToken = accessToken
            // TODO: handle possible exceptions
            youtube.playlists()
                .list("id,snippet,player,contentDetails,localizations,status".split(","))
                .setMaxResults(50L)
                .setMine(true)
                .execute()
                .let {
                    it.items.map { playlist -> playlist.toPlayListEntity() }
                }
        }
    }
}