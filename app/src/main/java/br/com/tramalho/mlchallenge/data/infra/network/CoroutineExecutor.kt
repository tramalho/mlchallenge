package br.com.tramalho.mlchallenge.data.infra.network

import kotlinx.coroutines.Deferred
import retrofit2.Response

suspend fun <T : Any> call(block: () -> Deferred<Response<T>>): Result<T> = parseResult(block().await())

fun <T : Any> parseResult(response: Response<T>): Result<T> {

    var result: Result<T>

    try {

        result = when {
            response.isSuccessful -> Result.Success(response.body() as T)
            else -> Result.Failure(Error(response.errorBody().toString()))
        }
    } catch (e: Exception) {
        result = Result.Failure(Error(e.message))
    }

    return result
}




