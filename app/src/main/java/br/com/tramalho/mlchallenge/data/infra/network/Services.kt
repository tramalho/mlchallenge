package br.com.tramalho.mlchallenge.data.infra.network

import br.com.tramalho.mlchallenge.data.entity.ItemResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemService {

    @GET("search")
    fun searchItemByName(
        @Query("q") name: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<Response<ItemResult>>
}