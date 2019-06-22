package br.com.tramalho.mlchallenge.presentation

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tramalho.mlchallenge.data.entity.ItemResult
import br.com.tramalho.mlchallenge.data.infra.Constants.Companion.LIMIT
import br.com.tramalho.mlchallenge.data.infra.network.Result
import br.com.tramalho.mlchallenge.data.repository.ItemRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ItemRepository) : ViewModel() {

    val loading = MutableLiveData<Int>().apply { value = GONE }
    val searchValue = MutableLiveData<String>().apply { value = "" }
    val dataStatus = SingleLiveEvent<ViewResult<ItemResult>>()
    val buttonEnabled = MutableLiveData<Boolean>().apply { value = true }

    fun find() = viewModelScope.launch {

        seachInProgress()

        val result = repository.findByName(searchValue.value.toString(), 0, LIMIT)

        when (result) {
            is Result.Success -> searchSuccess(result.data)
            is Result.Failure -> searchError(result.error)
        }
    }

    private fun searchSuccess(itemResult: ItemResult) {
        dataStatus.value = ViewResult.Success(itemResult)
        //loading.value = GONE
    }

    private fun searchError(failure: Error) {
        loading.value = GONE
        buttonEnabled.value = true
        dataStatus.value = ViewResult.Failure(failure)
    }

    private fun seachInProgress(){
        loading.value = VISIBLE
        buttonEnabled.value = false
        dataStatus.value = ViewResult.InProgress("")
    }
}
