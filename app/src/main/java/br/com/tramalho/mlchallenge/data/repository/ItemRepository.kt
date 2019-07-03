package br.com.tramalho.mlchallenge.data.repository

import br.com.tramalho.mlchallenge.data.entity.ItemDetailResult
import br.com.tramalho.mlchallenge.data.entity.ItemResult
import br.com.tramalho.mlchallenge.data.infra.network.ItemService
import br.com.tramalho.mlchallenge.data.infra.network.Result
import br.com.tramalho.mlchallenge.data.infra.network.call

class ItemRepository(private val service: ItemService) {

    suspend fun findByName(name: String, offset: Int, limit: Int): Result<ItemResult> = call {
        service.searchItemByName(name, limit, offset)
    }

    suspend fun findDetail(id: String): Result<ItemDetailResult> {

        val result1 = call { service.searchItemById(id) }
        val result2 = call { service.searchItemDescriptionById(id) }

        return when {
            result1 is Result.Failure -> result1
            result2 is Result.Failure -> result2
            else -> Result.Success(ItemDetailResult(getData(result1), getData(result2).description))
        }
    }

    private fun <T : Any> getData(result1: Result<T>) = (result1 as Result.Success).data
}