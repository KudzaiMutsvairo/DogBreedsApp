package com.kmutswairo.dogbreedsapp.presentation.breedslist

import com.kmutswairo.dogbreedsapp.CoroutineTestRule
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.mapper.toFavouriteDogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.DeleteFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.GetAllBreedsUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.InsertFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.RefreshDogBreedsUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist.DogBreedsState
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist.DogBreedsViewModel
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist.events.DogBreedsListEvent
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
class DogBreedsViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val getAllBreedsUseCase: GetAllBreedsUseCase =
        mockk<GetAllBreedsUseCase>()
    private val refreshDogBreedsUseCase: RefreshDogBreedsUseCase =
        mockk<RefreshDogBreedsUseCase>()
    private val insertFavouriteDogBreedUseCase: InsertFavouriteDogBreedUseCase =
        mockk<InsertFavouriteDogBreedUseCase>()
    private val deleteFavouriteDogBreedUseCase: DeleteFavouriteDogBreedUseCase =
        mockk<DeleteFavouriteDogBreedUseCase>()

    private lateinit var viewModel: DogBreedsViewModel

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `refreshDogBreeds should be run when DogBreedsEvent RefreshDogBreeds is called`() =
        runTest {
            // Arrange
            val breeds = listOf(
                DogBreed(
                    id = 1,
                    name = "Breed",
                    subBreeds = listOf("SubBreed"),
                ),
            )
            coEvery { getAllBreedsUseCase() } returns flow {
                emit(breeds)
            }
            coEvery { refreshDogBreedsUseCase() } returns flow {
                Resource.Success(true)
            }
            // Act
            viewModel = DogBreedsViewModel(
                getAllBreedsUseCase,
                refreshDogBreedsUseCase,
                insertFavouriteDogBreedUseCase,
                deleteFavouriteDogBreedUseCase,
            )
            viewModel.onEvent(DogBreedsListEvent.OnRefresh)

            // Assert
            coVerify { refreshDogBreedsUseCase() }
        }

    @Test
    fun `toggleFavouriteDogBreed should update uiState when breed is already favourite and removal is successful`() =
        runTest {
            // Arrange
            val breed = DogBreed(
                id = 1,
                name = "Breed",
                subBreeds = listOf("SubBreed"),
                isFavourite = true,
            )

            val expectedBreed = breed.copy(isFavourite = false)
            val breeds = listOf(expectedBreed)
            coEvery { deleteFavouriteDogBreedUseCase(breed.id!!) } returns Resource.Success(true)
            coEvery { getAllBreedsUseCase() } returns flow {
                emit(breeds)
            }

            viewModel = DogBreedsViewModel(
                getAllBreedsUseCase,
                refreshDogBreedsUseCase,
                insertFavouriteDogBreedUseCase,
                deleteFavouriteDogBreedUseCase,
            )

            // Act
            viewModel.onEvent(DogBreedsListEvent.OnClickFavourite(breed))

            // Assert
            val expected = DogBreedsState(
                breeds = breeds,
                message = "Removed from favourites",
                isLoading = false,
            )
            assertEquals(expected, viewModel.uiState.value)
        }

    @Test
    fun `toggleFavouriteDogBreed should update uiState when breed is not favourite and insertion is successful`() =
        runTest {
            // Arrange
            val breed = DogBreed(
                id = 1,
                name = "Breed",
                subBreeds = listOf("SubBreed"),
                isFavourite = false,
            )

            val breeds = listOf(breed)
            coEvery { insertFavouriteDogBreedUseCase(breed.toFavouriteDogBreed()) } returns Resource.Success(
                -1L,
            )
            coEvery { getAllBreedsUseCase() } returns flow {
                emit(breeds)
            }

            viewModel = DogBreedsViewModel(
                getAllBreedsUseCase,
                refreshDogBreedsUseCase,
                insertFavouriteDogBreedUseCase,
                deleteFavouriteDogBreedUseCase,
            )

            // Act
            viewModel.onEvent(DogBreedsListEvent.OnClickFavourite(breed))

            // Assert
            val expected = DogBreedsState(
                breeds = breeds,
                message = "Added to favourites",
                isLoading = false,
            )
            assertEquals(expected, viewModel.uiState.value)
        }

    @Test
    fun `toggleFavouriteDogBreed should update uiState with error when breed is already favourite and removal is unsuccessful`() =
        runTest {
            // Arrange
            val breed = DogBreed(
                id = 1,
                name = "Breed",
                subBreeds = listOf("SubBreed"),
                isFavourite = true,
            )
            val breeds = listOf(breed)

            coEvery { deleteFavouriteDogBreedUseCase(breed.id!!) } returns Resource.Error("Error")
            coEvery { getAllBreedsUseCase() } returns flow {
                emit(breeds)
            }

            viewModel = DogBreedsViewModel(
                getAllBreedsUseCase,
                refreshDogBreedsUseCase,
                insertFavouriteDogBreedUseCase,
                deleteFavouriteDogBreedUseCase,
            )

            // Act
            viewModel.onEvent(DogBreedsListEvent.OnClickFavourite(breed))

            // Assert
            val expected = DogBreedsState(
                breeds = breeds,
                message = "An unexpected error occurred",
                isLoading = false,
            )
            assertEquals(expected, viewModel.uiState.value)
        }
}
