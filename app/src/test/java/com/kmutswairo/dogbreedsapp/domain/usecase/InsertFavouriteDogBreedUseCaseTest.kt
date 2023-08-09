package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.FavouriteDogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.repository.FavouriteDogBreedsRepository
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.InsertFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InsertFavouriteDogBreedUseCaseTest {
    private lateinit var useCase: InsertFavouriteDogBreedUseCase
    private var repository: FavouriteDogBreedsRepository = mockk<FavouriteDogBreedsRepository>()

    @Before
    fun setUp() {
        useCase = InsertFavouriteDogBreedUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN insertFavouriteDogBreed is called, WHEN there are favourite dog breeds in the cache, THEN return Success`() =
        runTest {
            // Arrange
            val dogBreed = FavouriteDogBreed(
                id = 12,
                name = "Poodle",
                subBreeds = emptyList(),
            )
            coEvery { repository.insertFavouriteDogBreed(dogBreed) } returns Resource.Success(1L)

            // Act
            val result = useCase(dogBreed)

            // Assert
            assertTrue(result is Resource.Success)
        }

    @Test
    fun `GIVEN insertFavouriteDogBreed returns an error THEN return Error`() = runTest {
        // Arrange
        val dogBreed = FavouriteDogBreed(
            id = 12,
            name = "Poodle",
            subBreeds = emptyList(),
        )
        val errorMessage = "Error inserting favourite dog breed"
        coEvery { repository.insertFavouriteDogBreed(dogBreed) } returns Resource.Error(
            message = errorMessage,
        )

        // Act
        val result = useCase(dogBreed)

        // Assert
        assertTrue(result is Resource.Error)
    }
}
