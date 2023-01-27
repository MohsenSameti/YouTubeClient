package com.samentic.youtubeclient.core.ui.pagination

data class PagedResult<T>(
    val data: T,
    val nextPageToken: String?,
    val resultsPerPage: Int,
    val totalResults: Int
)
