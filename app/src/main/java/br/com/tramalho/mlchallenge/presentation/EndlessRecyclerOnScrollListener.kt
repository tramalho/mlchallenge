package br.com.tramalho.mlchallenge.presentation

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Listener observando o scroll da recycler view, ira invocar a function caso identifique
 * que o total de itens ainda nao visiveis seja menor que o valor apurado via calculo     
 */
class EndlessRecyclerOnScrollListener(val onLoadMore: () -> Unit) : RecyclerView.OnScrollListener() {

    private var mPreviousTotal = 1
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            return
        }

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager?.getItemCount() ?: 0
        val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }

        val visibleThreshold = 5
        if (!mLoading && totalItemCount - visibleItemCount <= lastVisibleItem + visibleThreshold) {

            onLoadMore()

            mLoading = true
        }
    }
}