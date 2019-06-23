package br.com.tramalho.mlchallenge.data.infra.network

import br.com.tramalho.mlchallenge.data.entity.ItemDescription
import br.com.tramalho.mlchallenge.data.entity.ItemDetail
import br.com.tramalho.mlchallenge.data.entity.ItemResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemService {

    @GET("search")
    fun searchItemByName(
        @Query("q") name: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<Response<ItemResult>>

    @GET("items/{id}")
    fun searchItemById(
        @Path("id") id: String
    ): Deferred<Response<ItemDetail>>

    @GET("items/{id}/description")
    fun searchItemDescriptionById(
        @Path("id") id: String
    ): Deferred<Response<ItemDescription>>

}