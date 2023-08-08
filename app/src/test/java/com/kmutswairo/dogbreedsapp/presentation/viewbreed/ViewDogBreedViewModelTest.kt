package com.kmutswairo.dogbreedsapp.presentation.viewbreed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kmutswairo.dogbreedsapp.domain.usecase.GetBreedImagesUseCase
import com.kmutswairo.dogbreedsapp.presentation.viewbreed.events.ViewDogBreedEvent
import com.kmutswairo.dogbreedsapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewDogBreedViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getBreedImagesUseCase: GetBreedImagesUseCase = mockk<GetBreedImagesUseCase>()
    private lateinit var viewModel: ViewDogBreedViewModel

    @Before
    fun setup() {
        viewModel = ViewDogBreedViewModel(getBreedImagesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    // Test event handling
    @Test
    fun `getImagesShould be run when viewModel onEvent(ViewDogBreedEvent_Load) is called`() =
        runTest {
            // Arrange
            val breedName = "name"
            coEvery { getBreedImagesUseCase(breedName) } returns flow {
                emit(Resource.Success(listOf("url1", "url2")))
            }

            // Act
            viewModel.onEvent(ViewDogBreedEvent.Load(breedName = breedName))

            // Assert
            coVerify { getBreedImagesUseCase(breedName) }
        }

    @Test
    fun `getBreedImages should update uiState with success`() = runTest {
        // Arrange
        val breedName = "name"
        val images = listOf("url1", "url2")
        coEvery { getBreedImagesUseCase(breedName) } returns flow {
            emit(Resource.Success(images))
        }

        // Act
        viewModel.onEvent(ViewDogBreedEvent.Load(breedName = breedName))

        // Assert
        val expected = ViewDogBreedState(
            dogBreeds = images,
            message = null,
            isLoading = false,
        )
        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `getBreedImages should update uiState with error`() = runTest {
        // Arrange
        val breedName = "name"
        coEvery { getBreedImagesUseCase(breedName) } returns flow {
            emit(Resource.Error("Error getting breed images"))
        }

        // Act
        viewModel.onEvent(ViewDogBreedEvent.Load(breedName = breedName))

        // Assert
        val expected = ViewDogBreedState(
            dogBreeds = emptyList(),
            message = "Error getting breed images",
            isLoading = false,
        )
        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `getBreedImages should update uiState with loading`() = runTest {
        // Arrange
        val breedName = "name"
        coEvery { getBreedImagesUseCase(breedName) } returns flow {
            emit(Resource.Loading())
        }

        // Act
        viewModel.onEvent(ViewDogBreedEvent.Load(breedName = breedName))

        // Assert
        val expected = ViewDogBreedState(
            dogBreeds = emptyList(),
            message = null,
            isLoading = true,
        )
        assertEquals(expected, viewModel.uiState.value)
    }
}
