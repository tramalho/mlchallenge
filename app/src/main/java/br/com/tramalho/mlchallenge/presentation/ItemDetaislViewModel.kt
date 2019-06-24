package br.com.tramalho.mlchallenge.presentation

import android.app.Application
import android.view.View
import android.view.View.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.tramalho.mlchallenge.R
import br.com.tramalho.mlchallenge.data.entity.ItemDetailResult
import br.com.tramalho.mlchallenge.data.entity.ItemSearch
import br.com.tramalho.mlchallenge.data.entity.Picture
import br.com.tramalho.mlchallenge.data.infra.network.Result
import br.com.tramalho.mlchallenge.data.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemDetaislViewModel(private val repository: ItemRepository, private val app: Application) :
    AndroidViewModel(app) {

    val totalSold = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val tittle = MutableLiveData<String>()
    val pictures = SingleLiveEvent<List<Picture>>()
    val description = MutableLiveData<String>()
    val successVisibility = MutableLiveData<Int>().apply { value = GONE }
    val loading = MutableLiveData<Int>().apply { value = GONE }
    val errorVisibility = MutableLiveData<Int>().apply { value = GONE }

    private lateinit var item: ItemSearch

    fun findDetails(itemSearch: ItemSearch) = viewModelScope.launch {
        item = itemSearch

        configStatus(VisibilityStatus.LOADING)

        val result = repository.findDetail(itemSearch.id)

        when (result) {
            is Result.Success -> success(result.data)
            is Result.Failure -> configStatus(VisibilityStatus.ERROR)
        }
    }

    fun tryAgain() {
        findDetails(item)
    }

    private fun configStatus(status: VisibilityStatus) {

        val visibility = when(status) {
            VisibilityStatus.LOADING -> ViewVisibility(GONE, VISIBLE, GONE)
            VisibilityStatus.SUCCESS -> ViewVisibility(VISIBLE, GONE, GONE)
            VisibilityStatus.ERROR -> ViewVisibility(GONE, GONE, VISIBLE)
        }

        successVisibility.value = visibility.successView
        loading.value = visibility.loading
        errorVisibility.value = visibility.errorViews
    }

    private fun success(data: ItemDetailResult) {
        configStatus(VisibilityStatus.SUCCESS)
        val item = data.itemDetail
        pictures.value = item.pictures
        totalSold.value = app.getString(R.string.sold, item.soldQuantity.toString())
        tittle.value = item.title
        price.value = formatCurrency(item.price)
        description.value = data.description
    }

    private class ViewVisibility(val successView: Int, val loading: Int, val errorViews: Int)
    private enum class VisibilityStatus {LOADING, SUCCESS, ERROR}
}