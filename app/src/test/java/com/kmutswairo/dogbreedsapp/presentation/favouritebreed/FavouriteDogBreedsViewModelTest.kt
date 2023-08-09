package com.kmutswairo.dogbreedsapp.presentation.favouritebreed

import com.kmutswairo.dogbreedsapp.CoroutineTestRule
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.FavouriteDogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.DeleteFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.GetAllFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.favouritebreed.FavouriteDogBreedsState
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.favouritebreed.FavouriteDogBreedsViewModel
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.favouritebreed.events.FavouriteBreedsEvent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class FavouriteDogBreedsViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val getAllFavouriteDogBreedUseCase: GetAllFavouriteDogBreedUseCase =
        mockk<GetAllFavouriteDogBreedUseCase>()
    private val deleteFavouriteDogBreedUseCase: DeleteFavouriteDogBreedUseCase =
        mockk<DeleteFavouriteDogBreedUseCase>()

    private lateinit var viewModel: FavouriteDogBreedsViewModel

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `deleteFavouriteBreed should be run when FavouriteBreedsEvent RemoveFavouriteBreed is called`() =
        runTest {
            // Arrange
            val breed = FavouriteDogBreed(
                id = 1,
                name = "Breed",
                subBreeds = listOf("SubBreed"),
            )
            coEvery { deleteFavouriteDogBreedUseCase(breed.id) } returns Resource.Success(true)
            coEvery { getAllFavouriteDogBreedUseCase() } returns flow {
                emit(Resource.Success(emptyList()))
            }
            // Act
            viewModel = FavouriteDogBreedsViewModel(
                getAllFavouriteDogBreedUseCase,
                deleteFavouriteDogBreedUseCase,
            )
            viewModel.onEvent(FavouriteBreedsEvent.RemoveFavouriteBreed(breed))

            // Assert
            coVerify { deleteFavouriteDogBreedUseCase(breed.id) }
            coVerify { getAllFavouriteDogBreedUseCase() }
        }

    @Test
    fun `uiState should be updated when FavouriteDogBreedsViewModel is initialized`() =
        runTest {
            // Arrange
            val breeds = listOf(
                FavouriteDogBreed(
                    id = 1,
                    name = "Breed",
                    subBreeds = listOf("SubBreed"),
                ),
            )

            val expected = FavouriteDogBreedsState(
                breeds = breeds,
                message = null,
                isLoading = false,
            )
            coEvery { getAllFavouriteDogBreedUseCase() } returns flow {
                emit(Resource.Success(breeds))
            }
            // Act
            viewModel = FavouriteDogBreedsViewModel(
                getAllFavouriteDogBreedUseCase,
                deleteFavouriteDogBreedUseCase,
            )

            // Assert
            assertEquals(expected, viewModel.uiState.value)
        }

    @Test
    fun `uiState should be updated when FavouriteBreedsEvent RemoveFavouriteBreed is called`() =
        runTest {
            // Arrange
            val breed = FavouriteDogBreed(
                id = 1,
                name = "Breed",
                subBreeds = listOf("SubBreed"),
            )
            val expected = FavouriteDogBreedsState(
                breeds = listOf(breed),
                message = "Successfully removed ${breed.name} from favourites",
                isLoading = false,
            )
            coEvery { deleteFavouriteDogBreedUseCase(breed.id) } returns Resource.Success(true)
            coEvery { getAllFavouriteDogBreedUseCase() } returns flow {
                emit(Resource.Success(listOf(breed)))
            }
            // Act
            viewModel = FavouriteDogBreedsViewModel(
                getAllFavouriteDogBreedUseCase,
                deleteFavouriteDogBreedUseCase,
            )
            viewModel.onEvent(FavouriteBreedsEvent.RemoveFavouriteBreed(breed))

            // Assert
            assertEquals(expected, viewModel.uiState.value)
        }

    @Test
    fun `uiState should be updated when RemoveFavouriteBreed is called and deleteFavouriteDogBreedUseCase returns Resource Error`() =
        runTest {
            // Arrange
            val breed = FavouriteDogBreed(
                id = 1,
                name = "Breed",
                subBreeds = listOf("SubBreed"),
            )
            val expected = FavouriteDogBreedsState(
                breeds = emptyList(),
                message = "Error",
                isLoading = false,
            )
            coEvery { getAllFavouriteDogBreedUseCase() } returns flow {
                emit(Resource.Success(emptyList()))
            }
            coEvery { deleteFavouriteDogBreedUseCase(breed.id) } returns Resource.Error("Error")
            // Act
            viewModel = FavouriteDogBreedsViewModel(
                getAllFavouriteDogBreedUseCase,
                deleteFavouriteDogBreedUseCase,
            )
            viewModel.onEvent(FavouriteBreedsEvent.RemoveFavouriteBreed(breed))

            // Assert
            assertEquals(expected, viewModel.uiState.value)
        }
}
