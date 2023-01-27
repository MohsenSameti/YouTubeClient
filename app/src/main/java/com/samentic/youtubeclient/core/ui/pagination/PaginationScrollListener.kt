package com.samentic.youtubeclient.core.ui.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(
    private val visibleThreshold: Int = 5
) : RecyclerView.OnScrollListener() {

    private var hasUserScrolled = false

    abstract fun isLoading(): Boolean

    abstract fun loadMore()

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            hasUserScrolled = true
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            hasUserScrolled = false
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // we need it to scroll to bottom!!
        if (dy <= 0) return
        // need to wait for loading to finish
        if (isLoading()) return

        if(!hasUserScrolled) return

        val layoutManager = (recyclerView.layoutManager as? LinearLayoutManager) ?: return
        val totalItemCount = layoutManager.itemCount

        if (totalItemCount <= 0) {
            // this is not meaningful if there is no items!
            return
        }
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        if (lastVisibleItemPosition == -1) return

        if (lastVisibleItemPosition + visibleThreshold >= totalItemCount) {
            loadMore()
        }
    }
}