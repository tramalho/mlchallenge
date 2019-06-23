package br.com.tramalho.mlchallenge.data.repository

import br.com.tramalho.mlchallenge.data.entity.ItemDetailResult
import br.com.tramalho.mlchallenge.data.entity.ItemResult
import br.com.tramalho.mlchallenge.data.infra.network.ItemService
import br.com.tramalho.mlchallenge.data.infra.network.Result
import br.com.tramalho.mlchallenge.data.infra.network.call
import br.com.tramalho.mlchallenge.data.infra.network.parseResult

class ItemRepository(private val service: ItemService) {

    suspend fun findByName(name: String, offset: Int, limit: Int): Result<ItemResult> = call {
        service.searchItemByName(name, limit, offset)
    }

    suspend fun findDetail(id: String): Result<ItemDetailResult> {

        val result1 = parseResult(service.searchItemById(id).await())
        val result2 = parseResult(service.searchItemDescriptionById(id).await())

        return when {
            result1 is Result.Failure -> result1
            result2 is Result.Failure -> result2
            else -> Result.Success(ItemDetailResult(getData(result1), getData(result2).description))
        }
    }

    private fun <T : Any> getData(result1: Result<T>) = (result1 as Result.Success).data
}