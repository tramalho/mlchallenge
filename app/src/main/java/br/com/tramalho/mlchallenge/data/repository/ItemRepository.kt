package br.com.tramalho.mlchallenge.data.repository

import br.com.tramalho.mlchallenge.data.entity.ItemResult
import br.com.tramalho.mlchallenge.data.infra.network.ItemService
import br.com.tramalho.mlchallenge.data.infra.network.Result
import br.com.tramalho.mlchallenge.data.infra.network.call

class ItemRepository(private val service: ItemService) {

    suspend fun findByName(name: String, offset: Int, limit: Int): Result<ItemResult> = call {
        service.searchItemByName(name, limit, offset)
    }
}