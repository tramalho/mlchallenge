package br.com.tramalho.mlchallenge.data.infra

sealed class ViewResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : ViewResult<T>()
    data class Failure(val error: Error) : ViewResult<Nothing>()
}