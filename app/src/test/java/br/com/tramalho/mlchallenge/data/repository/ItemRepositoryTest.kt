package br.com.tramalho.mlchallenge.data.repository

import br.com.tramalho.mlchallenge.data.entity.ItemDescription
import br.com.tramalho.mlchallenge.data.entity.ItemDetail
import br.com.tramalho.mlchallenge.data.infra.network.ItemService
import br.com.tramalho.mlchallenge.data.infra.network.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ItemRepositoryTest {

    @MockK
    private lateinit var itemService: ItemService

    private lateinit var itemDetail: ItemDetail

    private lateinit var itemDescription: ItemDescription


    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        itemDetail = ItemDetail("", 0.0, listOf(), 0)
        itemDescription = ItemDescription("")
    }

    @Test
    fun findDetailSuccess() = runBlockingTest {

        val responseDetail = Response.success(itemDetail)
        val responseDescription = Response.success(itemDescription)

        coEvery { itemService.searchItemById("").await() } returns responseDetail
        coEvery { itemService.searchItemDescriptionById("").await() } returns responseDescription

        val result = ItemRepository(itemService).findDetail("")

        assertTrue(result is Result.Success)
    }

    @Test
    fun findDetailErrorItemDetail() = runBlockingTest {

        val responseDetail = createResponseBodyError<ItemDetail>()
        val responseDescription = createResponseBodyError<ItemDescription>()

        coEvery { itemService.searchItemById("").await() } returns responseDetail
        coEvery { itemService.searchItemDescriptionById("").await() } returns responseDescription

        val result = ItemRepository(itemService).findDetail("")

        assertTrue(result is Result.Failure)
    }

    @Test
    fun findDetailErrorDescription() = runBlockingTest {

        val responseDetail = Response.success(itemDetail)
        val responseDescription = createResponseBodyError<ItemDescription>()

        coEvery { itemService.searchItemById("").await() } returns responseDetail
        coEvery { itemService.searchItemDescriptionById("").await() } returns responseDescription

        val result = ItemRepository(itemService).findDetail("")

        assertTrue(result is Result.Failure)
    }

    private fun <T : Any> createResponseBodyError(): Response<T> {
       return Response.error<T>(404, ResponseBody.create(MediaType.parse("application/json"), ""))
    }
}