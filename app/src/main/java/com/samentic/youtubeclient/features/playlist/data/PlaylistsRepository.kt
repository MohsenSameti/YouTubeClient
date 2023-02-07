package com.samentic.youtubeclient.features.playlist.data

import com.google.api.services.youtube.YouTube
import com.samentic.youtubeclient.core.data.db.thumbnail.ThumbnailLocalDataSource
import com.samentic.youtubeclient.core.ui.pagination.PagedResult
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import javax.inject.Inject

class PlaylistsRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val youtube: YouTube,
    private val playlistLocalDataSource: PlaylistLocalDataSource,
    private val thumbnailLocalDataSource: ThumbnailLocalDataSource
) {

    // todo: rename to myPlayLists
    // todo: add paging
    suspend fun fetchPlaylists(): List<PlaylistDto> {
        return authRepository.ensureAccessToken {
            // TODO: handle possible exceptions
            val response = youtube.playlists()
                .list("id,snippet,player,contentDetails,localizations,status".split(","))
                .setMaxResults(50L)
                .setMine(true)
                .execute()

            val playlists = response.items.map { playlist -> playlist.toPlaylistEntity() }
            val thumbnails = response.items.map { it.toThumbnailEntity() }

            thumbnailLocalDataSource.insertThumbnails(thumbnails)
            playlistLocalDataSource.insertPlaylists(playlists)

            response.items.map {
                PlaylistDto(
                    id = it.id,
                    publishedAt = it.snippet.publishedAt,
                    channelId = it.snippet.channelId,
                    title = it.snippet.title,
                    channelTitle = it.snippet.channelTitle,
                    description = it.snippet.description,
                    thumbnail = it.snippet.thumbnails,
                    itemCount = it.contentDetails.itemCount,
                    playerUrl = it.player.embedHtml
                )
            }
        }
    }

    suspend fun fetchPlaylistItems(
        id: String,
        pageToken: String?
    ): PagedResult<List<PlaylistItemEntity>> {
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