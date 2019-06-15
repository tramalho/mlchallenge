package br.com.tramalho.mlchallenge.data.infra.network

import kotlinx.coroutines.Deferred
import retrofit2.Response

suspend fun <T : Any> call(block: () -> Deferred<Response<T>>): Result<T> {

    try {

        val response = block().await()

        return when {
            response.isSuccessful -> Result.Success(response.body() as T)
            else -> Result.Failure(Error(response.errorBody().toString()))
        }
    } catch (e: Exception) {
        return Result.Failure(Error(e.message))
    }
}

