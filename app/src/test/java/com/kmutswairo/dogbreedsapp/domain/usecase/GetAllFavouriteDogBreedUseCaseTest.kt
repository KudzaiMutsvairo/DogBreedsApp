package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.FavouriteDogBreed
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository.FavouriteDogBreedsRepository
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.usecase.GetAllFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAllFavouriteDogBreedUseCaseTest {
    private lateinit var useCase: GetAllFavouriteDogBreedUseCase
    private var repository: FavouriteDogBreedsRepository = mockk<FavouriteDogBreedsRepository>()

    @Before
    fun setUp() {
        useCase = GetAllFavouriteDogBreedUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN getAllFavouriteDogBreeds is called, WHEN there are favourite dog breeds in the cache, THEN return Success`() =
        runTest {
            // Arrange
            val favDogBreeds = listOf(
                FavouriteDogBreed(
                    id = 1,
                    name = "Pug",
                    subBreeds = listOf(),
                ),
            )
            coEvery { repository.getAllFavouriteDogBreeds() } returns flow {
                emit(Resource.Loading())
                emit(Resource.Success(favDogBreeds))
            }

            // Act
            val resultFlow = useCase()
            val result = resultFlow.toList()

            // Assert
            assertTrue(result.first() is Resource.Loading)
            assertTrue(result.last() is Resource.Success)
            assertEquals(favDogBreeds, (result.last() as Resource.Success).data)
        }

    @Test
    fun `GIVEN getAllFavouriteDogBreeds returns error THEN return error`() = runTest {
        // Arrange
        val errorMessage = "Error"
        coEvery { repository.getAllFavouriteDogBreeds() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(errorMessage))
        }

        // Act
        val resultFlow = useCase()
        val result = resultFlow.toList()

        // Assert
        assertTrue(result.first() is Resource.Loading)
        assertTrue(result.last() is Resource.Error)
        assertEquals(errorMessage, (result.last() as Resource.Error).message)
    }
}
