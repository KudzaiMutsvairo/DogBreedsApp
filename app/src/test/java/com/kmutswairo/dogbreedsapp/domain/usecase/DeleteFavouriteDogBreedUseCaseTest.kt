package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.repository.FavouriteDogBreedsRepository
import com.kmutswairo.dogbreedsapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteFavouriteDogBreedUseCaseTest {

    private lateinit var useCase: DeleteFavouriteDogBreedUseCase
    private var repository: FavouriteDogBreedsRepository = mockk<FavouriteDogBreedsRepository>()

    @Before
    fun setUp() {
        useCase = DeleteFavouriteDogBreedUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN deleteFavouriteDogBreed is called, WHEN there are favourite dog breeds in the cache, THEN return Success`() =
        runTest {
            // Arrange
            coEvery { repository.deleteFavouriteDogBreed(1) } returns Resource.Success(true)

            // Act
            val result = useCase(1)

            // Assert
            assertTrue(result is Resource.Success)
        }

    @Test
    fun `GIVEN deleteFavouriteDogBreed throws an ecxeption THEN return Error`() = runTest {
        // Arrange
        val errorMessage = "Error deleting favourite dog breed"
        coEvery { repository.deleteFavouriteDogBreed(1) } returns Resource.Error(
            message = errorMessage,
        )

        // Act
        val result = useCase(1)

        // Assert
        assertTrue(result is Resource.Error)
    }
}
