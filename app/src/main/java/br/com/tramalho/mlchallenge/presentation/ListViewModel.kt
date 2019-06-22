package br.com.tramalho.mlchallenge.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tramalho.mlchallenge.data.entity.ItemResult
import br.com.tramalho.mlchallenge.data.entity.ItemSearch
import br.com.tramalho.mlchallenge.data.infra.Constants
import br.com.tramalho.mlchallenge.data.infra.network.Result
import br.com.tramalho.mlchallenge.data.repository.ItemRepository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: ItemRepository) : ViewModel() {

    val dataReceived = MutableLiveData<ArrayList<ItemSearch>>()

    private lateinit var itemResult: ItemResult

    fun addValue(itemResult: ItemResult) {

        val mergeData = ArrayList<ItemSearch>()

        dataReceived.value?.let {
            mergeData.addAll(dataReceived.value as ArrayList)
        }

        mergeData.addAll(itemResult.results)

        dataReceived.value = mergeData

        this.itemResult = itemResult
        this.itemResult.results = arrayListOf()
    }

    fun find() = viewModelScope.launch {

        if (itemResult.paging.offset < itemResult.paging.total) {

            val offset = itemResult.paging.offset + Constants.LIMIT

            val result = repository.findByName(itemResult.query, offset, Constants.LIMIT)

            if (result is Result.Success && result.data.results.isNotEmpty()) {
                addValue(result.data)
            }
        }
    }
}