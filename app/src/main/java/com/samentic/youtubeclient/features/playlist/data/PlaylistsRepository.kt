package com.samentic.youtubeclient.features.playlist.data

import com.google.api.services.youtube.YouTube
import com.samentic.youtubeclient.core.ui.pagination.PagedResult
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import javax.inject.Inject

class PlaylistsRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val youtube: YouTube
) {

    // todo: rename to myPlayLists
    // todo: add paging
    suspend fun fetchPlaylists(): List<PlaylistEntity> {
        return authRepository.ensureAccessToken {
            // TODO: handle possible exceptions
            youtube.playlists()
                .list("id,snippet,player,contentDetails,localizations,status".split(","))
                .setMaxResults(50L)
                .setMine(true)
                .execute()
                .let {
                    it.items.map { playlist -> playlist.toPlaylistEntity() }
                }
        }
    }

    suspend fun fetchPlaylistItems(id: String, pageToken: String?): PagedResult<List<PlaylistItemEntity>> {
        return authRepository.ensureAccessToken {
            val response = youtube.playlistItems()
                .list("contentDetails,id,snippet,status".split(","))
//                .setMaxResults(50L)
                .setMaxResults(15)
                .setPlaylistId(id)
                .setPageToken(pageToken)
                .execute()

            PagedResult(
                data = response.items.map { it.toPlaylistItemEntity() },
                nextPageToken = response.nextPageToken,
                resultsPerPage = response.pageInfo.resultsPerPage,
                totalResults = response.pageInfo.totalResults
            )
        }
    }
}