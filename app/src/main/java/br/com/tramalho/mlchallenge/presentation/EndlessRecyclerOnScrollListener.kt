package br.com.tramalho.mlchallenge.presentation

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessRecyclerOnScrollListener(val onLoadMore: () -> Unit) : RecyclerView.OnScrollListener() {

    private var mPreviousTotal = 1
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            return
        }

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager?.getItemCount()
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (mLoading) {
            if (totalItemCount!! > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }

        val visibleThreshold = 5
        if (!mLoading && totalItemCount!! - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            onLoadMore()

            mLoading = true
        }
    }
}