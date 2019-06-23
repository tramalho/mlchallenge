package br.com.tramalho.mlchallenge.data.infra.network

import io.mockk.every
import io.mockk.spyk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class CoroutineExecutorTest {

    private enum class MockStates {
        SUCCESS, FAILURE, EXCEPTION
    }

    private val exceptionMessage = "exceptionMessage"

    @Test
    fun shouldReturnSuccessResult() = runBlocking {
        val result = call { mockResult(MockStates.SUCCESS) }
        assertTrue(result is Result.Success<Any>)
    }

    @Test
    fun shouldReturnFailureResultWithNullBody() = runBlocking {
        val result = call { mockResult(MockStates.SUCCESS, null) }
        assertTrue(result is Result.Failure)
    }

    @Test
    fun shouldReturnError() = runBlocking {
        val result = call { mockResult(MockStates.FAILURE) }
        assertTrue(result is Result.Failure)
    }

    @Test
    fun shouldReturnErrorByException() = runBlocking {
        val result = call { mockResult(MockStates.EXCEPTION) }
        assertEquals(exceptionMessage, (result as Result.Failure).error.message)
    }

    private fun mockResult(mockStates: MockStates, body: String? = ""): CompletableDeferred<Response<Any>> {

        val response: Response<Any> = spyk(Response.success<Any>(body))
        val state = mockStates == MockStates.SUCCESS
        when (mockStates) {
            MockStates.EXCEPTION -> every { response.isSuccessful } throws IllegalArgumentException(exceptionMessage)
            else -> every { response.isSuccessful } returns state
        }

        return CompletableDeferred(response)
    }
}