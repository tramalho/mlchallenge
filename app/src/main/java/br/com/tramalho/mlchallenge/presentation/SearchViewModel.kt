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

    /**
     * Realiza busca dos primeiros dados que serao exibidos
     */
    fun find() = viewModelScope.launch {

        seachInProgress()

        val result = repository.findByName(searchValue.value.toString(), 5, 5)

        when (result) {
            is Result.Success -> searchSuccess(result.data)
            is Result.Failure -> searchError(result.error)
        }
    }

    /**
     * Envia os dados para view de modo que a nevegacao
     * possa acontecer
     */
    private fun searchSuccess(itemResult: ItemResult) {
        dataStatus.value = ViewResult.Success(itemResult)
        loading.value = GONE
        buttonEnabled.value = true
    }

    /**
     * Muda os estados dos campos na tela e notifica
     * a view para exibicao da snackbar
     */
    private fun searchError(failure: Error) {
        loading.value = GONE
        buttonEnabled.value = true
        dataStatus.value = ViewResult.Failure(failure)
    }

    /**
     * Notifica a view sobre o estado de loading para fechamento
     * do teclado e desabilita o botao de busca
     */
    private fun seachInProgress(){
        loading.value = VISIBLE
        buttonEnabled.value = false
        dataStatus.value = ViewResult.InProgress("")
    }
}
