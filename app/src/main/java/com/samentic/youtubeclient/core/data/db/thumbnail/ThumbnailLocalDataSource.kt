package com.samentic.youtubeclient.core.data.db.thumbnail

import javax.inject.Inject

class ThumbnailLocalDataSource @Inject constructor(
    private val dao: ThumbnailDao
) {

    suspend fun insertThumbnails(thumbnails: List<ThumbnailEntity>) {
        dao.insertThumbnails(thumbnails)
    }
}