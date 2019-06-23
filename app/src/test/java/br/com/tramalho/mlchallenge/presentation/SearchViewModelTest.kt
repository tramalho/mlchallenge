package br.com.tramalho.mlchallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.tramalho.mlchallenge.data.entity.Installments
import br.com.tramalho.mlchallenge.data.entity.ItemResult
import br.com.tramalho.mlchallenge.data.entity.ItemSearch
import br.com.tramalho.mlchallenge.data.entity.Paging
import br.com.tramalho.mlchallenge.data.infra.network.Result
import br.com.tramalho.mlchallenge.data.repository.ItemRepository
import br.com.tramalho.mlchallenge.utils.CoroutinesTestRule
import br.com.tramalho.mlchallenge.utils.observeForTesting
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    // Changes the main dispatcher for testing purposes
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val itemResult: ItemResult = createItemResult()

    private val errorMessageException = "errorMessageException"

    @MockK
    private lateinit var itemRepository: ItemRepository

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SearchViewModel(itemRepository)
    }

    @Test
    fun shouldReturnSuccess() = coroutinesTestRule.testDispatcher.runBlockingTest {

        coEvery { itemRepository.findByName(any(), any(), any()) } returns Result.Success(itemResult)

        viewModel.find()

        viewModel.dataStatus.observeForTesting {
            assertEquals(ViewResult.Success(itemResult), viewModel.dataStatus.value)
        }
    }

    @Test
    fun shouldReturnError() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val errorResult = Error(errorMessageException)

        coEvery { itemRepository.findByName(any(), any(), any()) } returns Result.Failure(errorResult)

        viewModel.find()

        viewModel.dataStatus.observeForTesting {
            assertEquals(ViewResult.Failure(errorResult), viewModel.dataStatus.value)
            assertEquals(errorMessageException, (viewModel.dataStatus.value as ViewResult.Failure).error.message)
        }
    }

    private fun createItemResult(): ItemResult {
        val item = ItemSearch("", "", "", Installments(0,0.0), 0.0)
        return ItemResult("", Paging(0, 0, 0), listOf(item))
    }
}